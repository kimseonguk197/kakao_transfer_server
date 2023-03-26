package com.example.remittance.dto;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class TransferRequestDto {
    @NotNull
    private String email;
    @NotNull
    private String sender_account_number;
//    송금금액(외화)
    @NotNull
    private BigDecimal amount_target;
    @NotNull
    private String target_currency;
    @NotNull
    private String target_country;
    @NotNull
    private String recipient_name;
    @NotNull
    private String recipient_account;
    @NotNull
    private Map<String, String> bank_info_map;
    @NotNull
    private Map<String, String> address_map;
}
