package com.jyjeong.currencylayer;

import com.jyjeong.currencylayer.dto.DataDto;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@RunWith(SpringRunner.class)
class CurrencyLayerApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	MockMvc mockMvc;

	@Test
	public void getMain() throws Exception{
		mockMvc.perform(get("/main"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void getDate() throws Exception{
		mockMvc.perform(get("/getCurrencyRate?stdCountryCode=KRW"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void getReceivedAmount() throws Exception {
		mockMvc.perform(post("/getReceivedAmount")
				.param("stdCountryName","KRW")
				.param("remittance","1000"))
				.andDo(print())
				.andExpect(status().isOk());

	}

	@Test
	public void setCurrencyLayer(){
		RestTemplate restTemplate = new RestTemplate();
		UriComponents builder = UriComponentsBuilder.fromHttpUrl("http://apilayer.net/api/live")
				.queryParam("access_key", "e7eac679984835ccf260bbfcee80ee84")
				.queryParam("currencies","KRW, JPY, PHP")
				.queryParam("source","USD")
				.queryParam("format","1")
				.build(false);    //자동으로 encode해주는 것을 막기 위해 false

		System.out.println("URL@@@@@@@@@@@@" + builder.toUriString());
		ResponseEntity<DataDto> response = restTemplate.getForEntity(builder.toUriString(), DataDto.class);

		System.out.println("@@@Success : " + response.getBody().isSuccess());
		System.out.println("@@@Quotes.getCur : " + response.getBody().getQuotes().toString());

		for( String key : response.getBody().getQuotes().keySet()){
			System.out.println("INSERT INTO CURRENCYINFO VALUES('"+key.replace("USD","")+"' ,"+response.getBody().getQuotes().get(key));
		}

	}
}
