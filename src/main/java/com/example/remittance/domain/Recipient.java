package com.example.remittance.domain;

import com.example.remittance.dto.TransferRequestDto;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "recipient", indexes = @Index(name = "idx__id__createDate", columnList = "id, createDate"))
@EntityListeners(AuditingEntityListener.class)
public class Recipient {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="name", nullable = false)
    private String name;

//    계좌번호
    @Column(name="account_number", nullable = false)
    private String account_number;

//    송금기본id
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Transfer transfer;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private List<RecipientAddress> recipientAddresses = new ArrayList<>();;

//    수신인은행정보id
    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    private List<RecipientBankInfo> recipientBankInfos = new ArrayList<>();;

    public void setRecipientAddress(RecipientAddress recipientAddress){
        this.recipientAddresses.add(recipientAddress);
        recipientAddress.setRecipient(this);
    }

    public void setRecipientBankInfos(RecipientBankInfo recipientBankInfo){
        this.recipientBankInfos.add(recipientBankInfo);
        recipientBankInfo.setRecipient(this);
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    public static Recipient createRecipient(TransferRequestDto transferRequestDto, List<RecipientBankInfo> recipientBankInfos, List<RecipientAddress> recipientAddresses){
        Recipient recipient = new Recipient();
        recipient.name = transferRequestDto.getRecipient_name();
        recipient.account_number = transferRequestDto.getRecipient_account();
        for(RecipientBankInfo recipientBankInfo : recipientBankInfos){
            recipient.setRecipientBankInfos(recipientBankInfo);
        }
        for(RecipientAddress recipientAddress : recipientAddresses){
            recipient.setRecipientAddress(recipientAddress);
        }
        return recipient;
    }

    //    row 생성시간
    @CreatedDate
    private LocalDateTime createDate;

}
