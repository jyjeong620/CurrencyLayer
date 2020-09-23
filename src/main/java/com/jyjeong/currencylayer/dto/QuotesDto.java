package com.jyjeong.currencylayer.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Table(name = "CURRENCYINFO")
@Entity
public class QuotesDto {

    @Id
    @Column(name = "stdCountryCode")
    private String stdCountryCode;

    @Column(name = "currencyRate")
    private BigDecimal currencyRate;
}
