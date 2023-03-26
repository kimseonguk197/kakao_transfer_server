package com.example.remittance.dto;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
public class ExchangeRateFeeRequestDto {
    @NotNull
    private String email;
//    송금금액(외화)
    @NotNull
    private BigDecimal amount_target;
    @NotNull
    private String target_currency;
    @NotNull
    private String target_country;
}
