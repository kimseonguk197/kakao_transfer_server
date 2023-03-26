package com.example.remittance.dto;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserRequestDto {
    @NotNull
    private String email;
    @NotNull
    private String name;
    @NotNull
    private String tradingBankInfoYn;
    private String address;
}
