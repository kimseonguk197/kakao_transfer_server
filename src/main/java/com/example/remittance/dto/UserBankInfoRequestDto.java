package com.example.remittance.dto;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UserBankInfoRequestDto {
    @NotNull
    private String user_email;
    @NotNull
    private String account_number;
    @NotNull
    private BigDecimal balance;
}
