package com.jyjeong.currencylayer.service;


import com.jyjeong.currencylayer.dto.CurrencyLayerDto;
import com.jyjeong.currencylayer.dto.DataDto;
import com.jyjeong.currencylayer.dto.QuotesDto;
import com.jyjeong.currencylayer.repository.QuotesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class CurrencyLayerServiceImpl implements CurrencyLayerService {

    @Autowired
    Environment environment;

    @Autowired
    QuotesRepository quotesRepository;


    /**
     * 저장된 국가코드, 환율 반환
     * @return List<QuotesDto>
     */
    @Override
    public List<QuotesDto> getQuotes(){
        List<QuotesDto> quotesDto = quotesRepository.findAll();
        return quotesDto;
    }

    /**
     * 매계변수로 받아온 국가코드에 대한 환율 반환
     * @param stdCountryCode 국가코드
     * @return 환율(소수점 2자리까지)
     */
    @Override
    public BigDecimal getCurrencyRate(String stdCountryCode) {
        BigDecimal currencyRate = quotesRepository.findById(stdCountryCode).get().getCurrencyRate();
        log.info("환율 : " + currencyRate.setScale(2, BigDecimal.ROUND_DOWN));
        return currencyRate;
    }

    /**
     * 매계변수로 받아온 환율과 송금액을 계산하여 수취금액 반환
     * @param currencyRate 환율정보
     * @param remittance 송금액
     * @return 환율정보화 송금액을 계산한 수취금액
     */
    @Override
    public BigDecimal getReceivedAmount(BigDecimal currencyRate, BigDecimal remittance) {
        BigDecimal receivedAmount = remittance.multiply(currencyRate).setScale(2, BigDecimal.ROUND_DOWN);
        log.info("수취금액 : " + receivedAmount);
        return receivedAmount;
    }
}
