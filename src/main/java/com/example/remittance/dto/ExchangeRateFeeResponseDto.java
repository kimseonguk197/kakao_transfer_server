package com.example.remittance.dto;


import com.example.remittance.domain.ExchangeRateInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ExchangeRateFeeResponseDto {
    private ExchangeRateInfo exchangeRateInfo;
//    수수료
    private BigDecimal fee;
//    우대환율적용환율
    private BigDecimal exchange_rate;
//    우대환율적용환율 원화금액
    private BigDecimal krw_amount;
//    수수료 + 우대환율적용환율 원화금액
    private BigDecimal total_krw_amount;
//    매매기준율 기준 usd 금액
    private BigDecimal usd_amount;
}
