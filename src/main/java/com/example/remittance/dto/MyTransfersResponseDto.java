package com.example.remittance.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MyTransfersResponseDto {
    private BigDecimal amount_target;
    private String target_currency;
    private String target_country;
    private String recipient_name;
    private String recipient_account;
    private LocalDateTime transfer_date;
}
