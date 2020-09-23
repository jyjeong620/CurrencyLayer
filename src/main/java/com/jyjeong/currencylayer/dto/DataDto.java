package com.jyjeong.currencylayer.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;


@Data
public class DataDto {
    private boolean success;
    private String terms;
    private String privacy;
    private int timestamp;
    private String source;
    @JsonProperty("quotes")
    private Map<String, BigDecimal> quotes;
//    private List<QuotesDto> quotes;
}
