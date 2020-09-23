package com.jyjeong.currencylayer.service;

import com.jyjeong.currencylayer.dto.QuotesDto;

import java.math.BigDecimal;
import java.util.List;

public interface CurrencyLayerService {
    public List<QuotesDto> getQuotes();
    public BigDecimal getCurrencyRate(String stdCountryCode);
    public BigDecimal getReceivedAmount(BigDecimal currencyRate, BigDecimal remittance);
}
