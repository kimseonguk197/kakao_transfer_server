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
//recipient_id+address_type index를 통해 정합성 + 조회 속도 향상
@Table(name = "recipient_address", indexes = @Index(name = "idx__recipient_id__address_type", columnList = "recipient_id, address_type"))
@EntityListeners(AuditingEntityListener.class)
public class RecipientAddress {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    code_type
    @Column(name="address_type", nullable = false)
    private String address_type;

//    code
    @Column(name="address", nullable = false)
    private String address;

//    수신인정보id
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private Recipient recipient;

    //    row 생성시간
    @CreatedDate
    private LocalDateTime createDate;


    public void setRecipient(Recipient recipient){
        this.recipient = recipient;
    }

    public static List<RecipientAddress> createRecipientAddress(TransferRequestDto transferRequestDto){
        List<RecipientAddress> recipientAddresses = new ArrayList<>();
        Map<String, String> address_map = transferRequestDto.getAddress_map();
        for(String key : address_map.keySet()){
            RecipientAddress recipientAddress = new RecipientAddress();
            recipientAddress.address_type = key;
            recipientAddress.address = address_map.get(key);
            recipientAddresses.add(recipientAddress);
        }
        return recipientAddresses;
    }
}
