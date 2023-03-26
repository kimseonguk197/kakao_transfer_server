package com.example.remittance.dto;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class MyRecipientsResponseDto {
    private String recipient_name;
    private String recipient_account;
    private Map<String, String> bank_info_map;
    private Map<String, String> address_map;
    private LocalDateTime transfer_date;
}
