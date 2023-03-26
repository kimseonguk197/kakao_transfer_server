package com.example.remittance.domain;

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
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="email", nullable = false, unique = true)
    private String email;

//    거래외국환등록여부
    @Column(name="trading_bank_info_yn", nullable = false)
    private String trading_bank_info_yn;

//    우대할인율
    private BigDecimal discount_percentage;

//    송금인주소
    @Column(nullable = false)
    private String address;


    //    user 송금 통계
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private UserStatics userStatics;


    //    송금목록
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Transfer> transfers;



    //    송금인계좌목록
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserBankInfo> userBankInfos;


    //    row 생성시간
    @CreatedDate
    private LocalDateTime createDate;

    public void setUserStatics(UserStatics userStatics){
        this.userStatics = userStatics;
        userStatics.setUser(this);
    }


    public static User createUser(String name, String email, String tradingBankInfoYn, String address, UserStatics userStatics){
        User user = new User();
        user.name = name;
        user.email = email;
        user.trading_bank_info_yn = tradingBankInfoYn;
//        최초 할인율 50 고정
        user.discount_percentage = BigDecimal.valueOf(50);
        user.address = address;
        user.setUserStatics(userStatics);
        return user;
    }


    public User updateTrading_bank_info_yn(User user, String trading_bank_info_yn){
        user.trading_bank_info_yn = trading_bank_info_yn;
        return user;
    }


}
