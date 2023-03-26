package com.example.remittance.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
//고시회차 + 통화 Unique Constraint 설정
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "round_id", "currency", "country"}))
public class ExchangeRateInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    고시회차
    @Column(nullable = false)
    private Long round_id;

//    통화
    @Column(nullable = false)
    private String currency;
    //    통화
    @Column(nullable = false)
    private String country;

//    매매기준율
    @Column(nullable = false)
    private BigDecimal basic_exchange_rate;

//    매도율
    @Column(nullable = false)
    private BigDecimal selling_exchange_rate;

//    매입율
    @Column(nullable = false)
    private BigDecimal buying_exchange_rate;

    //    row 생성시간
    @CreatedDate
    private LocalDateTime createDate= LocalDateTime.now();;

    @Builder
    public ExchangeRateInfo(Long roundId, String currency, String country,BigDecimal basic_exchange_rate, BigDecimal buying_exchange_rate,BigDecimal selling_exchange_rate){
        this.round_id = roundId;
        this.currency = currency;
        this.country = country;
        this.basic_exchange_rate = basic_exchange_rate;
        this.buying_exchange_rate = buying_exchange_rate;
        this.selling_exchange_rate = selling_exchange_rate;
    }

}
