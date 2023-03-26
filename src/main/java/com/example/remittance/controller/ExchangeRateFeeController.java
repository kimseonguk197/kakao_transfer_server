package com.example.remittance.controller;

import com.example.remittance.dto.ExchangeRateFeeRequestDto;
import com.example.remittance.dto.ExchangeRateFeeResponseDto;
import com.example.remittance.service.ExchangeRateFeeService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExchangeRateFeeController {

    private final ExchangeRateFeeService exchangeRateFeeService;

    public ExchangeRateFeeController(ExchangeRateFeeService exchangeRateFeeService) {
        this.exchangeRateFeeService = exchangeRateFeeService;
    }

//    송금전 환율, 수수료 등 정보조회
    @GetMapping("exchange/total")
    public ExchangeRateFeeResponseDto findTotalRateFee(@RequestBody ExchangeRateFeeRequestDto erf) throws Exception {
        ExchangeRateFeeResponseDto exResDto= exchangeRateFeeService.findTotalRateFee(erf);;
        return exResDto;
    }

}
