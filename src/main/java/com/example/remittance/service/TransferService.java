package com.example.remittance.service;

import com.example.remittance.domain.*;
import com.example.remittance.dto.*;
import com.example.remittance.repository.TransferRepository;
import com.example.remittance.repository.UserBankInfoRepository;
import com.example.remittance.repository.UserStaticsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class TransferService {
    private final UserService userService;
    private final TransferValidation transferValidation;
    private final UserStaticsRepository userStaticsRepository;
    private final UserBankInfoRepository userBankInfoRepository;
    private final TransferRepository transferRepository;
    private final ExchangeRateFeeService exchangeRateFeeService;

    public TransferService(UserService userService, TransferValidation transferValidation, UserStaticsRepository userStaticsRepository, UserBankInfoRepository userBankInfoRepository, TransferRepository transferRepository, ExchangeRateFeeService exchangeRateFeeService) {

        this.userService = userService;
        this.transferValidation = transferValidation;
        this.userStaticsRepository = userStaticsRepository;
        this.userBankInfoRepository = userBankInfoRepository;
        this.transferRepository = transferRepository;
        this.exchangeRateFeeService = exchangeRateFeeService;
    }

    public void save(TransferRequestDto tfd) throws Exception {
        ExchangeRateFeeRequestDto exReqDto = new ExchangeRateFeeRequestDto();
        exReqDto.setEmail(tfd.getEmail());
        exReqDto.setAmount_target(tfd.getAmount_target());
        exReqDto.setTarget_country(tfd.getTarget_country());
        exReqDto.setTarget_currency(tfd.getTarget_currency());
        ExchangeRateFeeResponseDto exResDto =  exchangeRateFeeService.findTotalRateFee(exReqDto);

        User user = userService.findByEmail(tfd.getEmail());
//        고객, (usd환전금액, 최신환율정보)등을 전달 후 validation 체크
        transferValidation.validate(user, exResDto, tfd.getSender_account_number());;

//        수신자주소
        List<RecipientAddress> recipientAddresses = RecipientAddress.createRecipientAddress(tfd);

//        수신자 은행정보
        List<RecipientBankInfo> recipientBankInfos = RecipientBankInfo.createRecipientBankInfo(tfd);

//        수신자 정보
        Recipient recipient = Recipient.createRecipient(tfd, recipientBankInfos, recipientAddresses);

//        송금
        Transfer transfer = Transfer.builder().amount(exResDto.getTotal_krw_amount()).target_amount(tfd.getAmount_target())
                .usd_amount(exResDto.getUsd_amount()).target_currency(tfd.getTarget_currency())
                .target_country(tfd.getTarget_country()).fee(exResDto.getFee()).discount_exchange_rate(exResDto.getExchange_rate())
                .exchangeRateInfo(exResDto.getExchangeRateInfo()).user(user).recipient(recipient).build();

//        잔액 수정
        UserBankInfo userBankInfo = userBankInfoRepository.findByAccount(tfd.getSender_account_number()).orElseThrow(Exception::new);;
//        송금 후 당행송금total 가산
        UserStatics ust = userStaticsRepository.findById(user.getId()).orElseThrow(Exception::new);

        try{
            transferRepository.save(transfer);
            userBankInfoRepository.save(UserBankInfo.updateUserBankInfo(userBankInfo, userBankInfo.getBalance().subtract(exResDto.getTotal_krw_amount())));
            userStaticsRepository.save(UserStatics.updateUserStatics(ust, ust.getTotal_remit_usd().add(exResDto.getUsd_amount()), ust.getOther_bank_total_remit_usd(), user));
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    //    송금인 email을 통해 최근3개월 해외송금내역조회
    public List<MyTransfersResponseDto> mytransfers(String userEmail) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeMonthAgo = now.minusMonths(3);
        List<Transfer> transfers = transferRepository.findAllByUserAndCreateDateBetween(userService.findByEmail(userEmail), threeMonthAgo, now);
        List<MyTransfersResponseDto> myTransfers = new ArrayList<>();
        for(Transfer transfer : transfers){
            MyTransfersResponseDto myTransfer = new MyTransfersResponseDto();
            myTransfer.setAmount_target(transfer.getTarget_amount());
            myTransfer.setTarget_country(transfer.getTarget_country());
            myTransfer.setTarget_currency(transfer.getTarget_currency());
            myTransfer.setRecipient_name(transfer.getRecipient().getName());
            myTransfer.setRecipient_account(transfer.getRecipient().getAccount_number());
            myTransfer.setTransfer_date(transfer.getCreateDate());
            myTransfers.add(myTransfer);
        }
        return myTransfers;
    }


    //    송금인 email을 통해 최근 해외송금 수신인정보 조회
    public List<MyRecipientsResponseDto> myRecipients(String userEmail) throws Exception {
        List<Transfer> transfers = transferRepository.findAllByUserOrderByCreateDateDesc(userService.findByEmail(userEmail));
        List<MyRecipientsResponseDto> myRecipients = new ArrayList<>();
        for(Transfer transfer : transfers){
            MyRecipientsResponseDto myRecipient = new MyRecipientsResponseDto();
            Recipient recipient = transfer.getRecipient();
            myRecipient.setRecipient_name(recipient.getName());
            myRecipient.setRecipient_account(recipient.getAccount_number());
            Map<String, String> address_map = new HashMap<>();
            for(RecipientAddress ra : recipient.getRecipientAddresses()){
                address_map.put(ra.getAddress_type(), ra.getAddress());
            }
            Map<String, String> bankinfo_map = new HashMap<>();
            for(RecipientBankInfo rb : recipient.getRecipientBankInfos()){
                bankinfo_map.put(rb.getCode_type(), rb.getCode());
            }
            myRecipient.setAddress_map(address_map);
            myRecipient.setBank_info_map(bankinfo_map);
            myRecipient.setTransfer_date(transfer.getCreateDate());
            myRecipients.add(myRecipient);
        }
        return myRecipients;
    }

}
