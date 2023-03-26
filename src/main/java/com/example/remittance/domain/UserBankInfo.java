package com.example.remittance.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
//송금인
public class UserBankInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    송금인id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    //    계좌번호
    @Column(nullable = false, unique = true)
    private String account;

    //    계좌잔액
    @Column(nullable = false)
    private BigDecimal balance;

    //    row 생성시간
    @CreatedDate
    private LocalDateTime createDate;


    public void setUser(User user) {
        this.user = user;
    }

    public static UserBankInfo updateUserBankInfo(UserBankInfo userBankInfo, BigDecimal balance){
        userBankInfo.balance = balance;
        return userBankInfo;
    }

    public static UserBankInfo createUserBankInfo(String account_number, BigDecimal balance, User user){
        UserBankInfo userBankInfo = new UserBankInfo();
        userBankInfo.account = account_number;
        userBankInfo.balance = balance;
        userBankInfo.user = user;
        return userBankInfo;
    }


}
