package com.example.remittance.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
//송금인송금통계
public class UserStatics {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    당행1년송금총액
    @Column(nullable = false)
    private BigDecimal total_remit_usd;

//    타행1년송금총액
    @Column(nullable = false)
    private BigDecimal other_bank_total_remit_usd;


//    송금인id
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    //    row 생성시간
    @CreatedDate
    private LocalDateTime createDate;


    public void setUser(User user) {
        this.user = user;
    }

    public static UserStatics createUserStatics(BigDecimal total_remit_usd,BigDecimal other_bank_total_remit_usd){
        UserStatics userStatics = new UserStatics();
        userStatics.total_remit_usd = total_remit_usd;
        userStatics.other_bank_total_remit_usd = other_bank_total_remit_usd;
        return userStatics;
    }

    public static UserStatics updateUserStatics(UserStatics userStatics, BigDecimal total_remit_usd, BigDecimal other_bank_total_remit_usd,User user){
        userStatics.total_remit_usd = total_remit_usd;
        userStatics.other_bank_total_remit_usd = other_bank_total_remit_usd;
        return userStatics;
    }

}
