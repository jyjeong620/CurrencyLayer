package com.jyjeong.currencylayer.service;


import com.jyjeong.currencylayer.dto.QuotesDto;
import com.jyjeong.currencylayer.repository.QuotesRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrencyLayerServiceImpl implements CurrencyLayerService {


    public final QuotesRepository quotesRepository;

    /**
     * 매계변수로 받아온 국가코드에 대한 환율 반환
     * @param stdCountryCode 국가코드
     * @return 환율(소수점 2자리까지)
     */
    @Override
    public BigDecimal getCurrencyRate(String stdCountryCode) {
        Optional<QuotesDto> getQuotes = quotesRepository.findById(stdCountryCode);
        if(getQuotes.isPresent()) {
            BigDecimal currencyRate = getQuotes.get().getCurrencyRate();
            log.debug("환율 : " + currencyRate.setScale(2, BigDecimal.ROUND_DOWN));
            return currencyRate;
        } else {
            log.error("환율 정보를 가져올 수 없습니다.");
            return BigDecimal.ZERO;
        }
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
        log.debug("수취금액 : " + receivedAmount);
        return receivedAmount;
    }
}
