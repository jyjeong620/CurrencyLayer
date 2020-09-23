package com.jyjeong.currencylayer.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CurrencyLayerDto {
    String stdCountryName;  //수취국가명
    String stdCountryCode;  //수취국가코드
    String currencyRate;    //환율
    BigDecimal remittance;  //송금액
    String receivedAmount;  //수취금액

}
