package com.jyjeong.currencylayer.common;

import com.jyjeong.currencylayer.dto.DataDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
@Slf4j
public class DatabaseConfig implements ApplicationRunner {

    @Autowired
    DataSource dataSource;

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.access.key}")
    private String apiAccessKey;

    @Value("${api.currencies}")
    private String apiCurrencies;

    @Value("${api.source}")
    private String apiSource;

    @Value("${api.format}")
    private String apiFormat;


    /**
     * 프로그램 시작시 API 호출하여 받아온 데이터를 DB에 저장
     * 내장 H2 DB 사용
     */
    @Override
    public void run(ApplicationArguments args) throws SQLException {
        try(Connection conn = dataSource.getConnection()){
            log.info("connection DB : " + conn.getMetaData().getURL());
            log.info("connection User : " + conn.getMetaData().getUserName());

            Statement stmt = conn.createStatement();

            String sql = "CREATE TABLE CURRENCYINFO(stdCountryCode CHAR(3) NOT NULL, currencyRate DECIMAL(20,9), PRIMARY KEY (stdCountryCode) )";
            stmt.executeUpdate(sql);


            ResponseEntity<DataDto> responseEntity = getAllCurrencyRate();
            for( String key : responseEntity.getBody().getQuotes().keySet()){
                stmt.execute("INSERT INTO CURRENCYINFO VALUES('"+key.replace("USD","")+"' ,"+responseEntity.getBody().getQuotes().get(key)+")");
            }

//            stmt.execute("INSERT INTO CURRENCYINFO VALUES('KRW' ,1162.894983)");
//            stmt.execute("INSERT INTO CURRENCYINFO VALUES('JPY' ,105.01695)");
//            stmt.execute("INSERT INTO CURRENCYINFO VALUES('PHP' ,48.480501)");

        } catch (Exception e){
            log.error("Repo DB Setting Error.");
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }

    /**
     * API를 이용해 환율정보를 가져와 반환
     * @return
     */
    public ResponseEntity getAllCurrencyRate(){
        RestTemplate restTemplate = new RestTemplate();
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("access_key", apiAccessKey)
                .queryParam("currencies",apiCurrencies)
                .queryParam("source",apiSource)
                .queryParam("format",apiFormat)
                .build(false);    //자동으로 encode해주는 것을 막기 위해 false
        log.info("CurrencyLayer API Get :: GET URL : " + uriComponents.toUriString());
        ResponseEntity<DataDto> dataDto = restTemplate.getForEntity(uriComponents.toUriString(), DataDto.class);

        if(!dataDto.getBody().isSuccess()){
            log.error("CurrencyLayer API GET :: Failed ");
            return new ResponseEntity<>("에러", HttpStatus.BAD_REQUEST);
        }
        else {
            log.debug("CurrencyLayer API GET :: Success ");
            return dataDto;
        }
    }
}