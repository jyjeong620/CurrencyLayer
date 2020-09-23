package com.jyjeong.currencylayer.controller;

import com.jyjeong.currencylayer.dto.CurrencyLayerDto;
import com.jyjeong.currencylayer.service.CurrencyLayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;

@Controller
@Slf4j
public class CurrencyLayerController {

    @Autowired
    CurrencyLayerService currencylayerService;


    /**
     * 메인화면
     * @return
     */
    @GetMapping("/")
    public String main(){

        return "main";
    }

    /**
     * 국가 코드를 받아와 DB에 저장된 환율 반환
     * @param stdCountryCode 국가코드
     * @return 국가코드별 환율
     */
    @GetMapping("/getCurrencyRate")
    @ResponseBody
    public BigDecimal getCurrencyInfo(
            @RequestParam(value = "stdCountryCode") String stdCountryCode
    ){
        log.info("set stdCountryCode = " + stdCountryCode);
        if(currencylayerService.getCurrencyRate(stdCountryCode) != null){
            return currencylayerService.getCurrencyRate(stdCountryCode).setScale(2, BigDecimal.ROUND_DOWN);
        }
        else {
            return null;
        }
    }


    /**
     * 입력받은 국가코드를 환율로 변환 
     * 입력받은 송금액과 환율을 이용하여 수취금액 반환 
     * @param stdCountryCode 국가코드
     * @param remittance 송금액
     * @return 수취금액
     */
    @PostMapping("/getReceivedAmount")
    @ResponseBody
    public BigDecimal getReceivedAmount(
            @RequestParam(value = "stdCountryCode") String stdCountryCode,
            @RequestParam(value = "remittance") BigDecimal remittance
    ){
        log.info("set currencyRate : " + currencylayerService.getCurrencyRate(stdCountryCode));
        log.info("set remittance : " + remittance);

        return currencylayerService.getReceivedAmount(currencylayerService.getCurrencyRate(stdCountryCode),remittance);
    }
}
