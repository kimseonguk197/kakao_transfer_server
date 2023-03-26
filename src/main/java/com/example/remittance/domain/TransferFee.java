package com.example.remittance.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TransferFee {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //    송금통화
    @Column(nullable = false)
    private String target_currency;
    //    송금국가
    @Column(nullable = false)
    private String target_country;
    //    송금구간(start)
    @Column(nullable = false)
    private BigDecimal amount_start;
    //    송금구간(end)
    @Column(nullable = false)
    private BigDecimal amount_end;
    //    수수료
    @Column(nullable = false)
    private BigDecimal fee;

    @Builder
    public TransferFee(BigDecimal start, BigDecimal end, BigDecimal fee, String target_country, String target_currency){
        this.fee = fee;
        this.target_currency = target_currency;
        this.target_country = target_country;
        this.amount_start = start;
        this.amount_end = end;
    }

}
