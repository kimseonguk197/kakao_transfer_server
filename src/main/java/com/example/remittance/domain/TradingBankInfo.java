package com.example.remittance.domain;

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
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class TradingBankInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

//    지정항목
    private String type;

//    파일경로
    private String file_path;

    //    row 생성시간
    @CreatedDate
    private LocalDateTime createDate;


    @Builder
    public TradingBankInfo(User user, String type, String file_path){
        this.user = user;
        this.type = type;
        this.file_path = file_path;
    }


}
