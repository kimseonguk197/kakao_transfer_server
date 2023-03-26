package com.example.remittance.domain;

import com.example.remittance.dto.TransferRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Getter
//recipient_id+codeType index를 통해 정합성 + 조회 속도 향상
@Table(name = "recipient_bank_info", indexes = @Index(name = "idx__id__code_type", columnList = "recipient_id, code_type"))
@EntityListeners(AuditingEntityListener.class)
public class RecipientBankInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    code_type
    @Column(name="code_type", nullable = false)
    private String code_type;

//    code
    @Column(name="code", nullable = false)
    private String code;

//    수신인정보id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(nullable = false, name = "recipient_id")
    private Recipient recipient;

    //    row 생성시간
    @CreatedDate
    private LocalDateTime createDate;


    public void setRecipient(Recipient recipient){
        this.recipient = recipient;
    }

    public static List<RecipientBankInfo> createRecipientBankInfo(TransferRequestDto transferRequestDto){
        List<RecipientBankInfo> recipientBankInfos = new ArrayList<>();
        Map<String, String> bank_info_map = transferRequestDto.getBank_info_map();
        for(String key : bank_info_map.keySet()){
            RecipientBankInfo recipientBankInfo = new RecipientBankInfo();
            recipientBankInfo.code_type = key;
            recipientBankInfo.code = bank_info_map.get(key);
            recipientBankInfos.add(recipientBankInfo);
        }
        return recipientBankInfos;
    }
}
