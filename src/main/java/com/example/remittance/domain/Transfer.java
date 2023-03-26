package com.example.remittance.domain;

import com.example.remittance.etc.TransferStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Transfer {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //    송금액(원화)
    @Column(nullable = false)
    private BigDecimal amount;
//    송금액(외화)
    @Column(nullable = false)
    private BigDecimal target_amount;
//    송금액(USD)
    @Column(nullable = false)
    private BigDecimal usd_amount;
//    송금통화
    @Column(nullable = false)
    private String target_currency;
//    송금국가
    @Column(nullable = false)
    private String target_country;
//    송금 수수료
    private BigDecimal fee;
    private BigDecimal discount_exchange_rate;
    @Enumerated(EnumType.STRING)
    private TransferStatusEnum transferStatusEnum;

//    환율정보
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @JsonIgnore
    private ExchangeRateInfo exchangeRateInfo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "transfer")
    private Recipient recipient;

//    송금인id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name="user_id")
    @JsonIgnore
    private User user;

    //    row 생성시간
    @CreatedDate
    private LocalDateTime createDate;

    public void setRecipient(Recipient recipient){
        this.recipient = recipient;
        recipient.setTransfer(this);
    }

    @Builder
    public Transfer(BigDecimal amount, BigDecimal target_amount, BigDecimal usd_amount, String target_currency, String target_country, BigDecimal fee, BigDecimal discount_exchange_rate,ExchangeRateInfo exchangeRateInfo, User user, Recipient recipient){
        this.amount = amount;
        this.target_amount = target_amount;
        this.usd_amount = usd_amount;
        this.target_currency = target_currency;
        this.target_country = target_country;
        this.fee = fee;
        this.discount_exchange_rate = discount_exchange_rate;
        this.transferStatusEnum = TransferStatusEnum.remmiting;
        this.exchangeRateInfo = exchangeRateInfo;
        this.user = user;
        this.setRecipient(recipient);
    }

}
