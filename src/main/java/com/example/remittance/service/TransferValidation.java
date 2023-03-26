package com.example.remittance.service;

import com.example.remittance.domain.User;
import com.example.remittance.domain.UserBankInfo;
import com.example.remittance.domain.UserStatics;
import com.example.remittance.dto.ExchangeRateFeeResponseDto;
import com.example.remittance.repository.UserBankInfoRepository;
import com.example.remittance.repository.UserStaticsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransferValidation {


    private final UserStaticsRepository userStaticsRepository;
    private final UserBankInfoRepository userBankInfoRepository;

    public TransferValidation(UserStaticsRepository userStaticsRepository, UserBankInfoRepository userBankInfoRepository) {
        this.userStaticsRepository = userStaticsRepository;
        this.userBankInfoRepository = userBankInfoRepository;
    }

    void validate(User user, ExchangeRateFeeResponseDto exResDto, String accNum) throws Exception {
//        0.계좌에 보낼 돈이 있는지 체크(원화기준)
        UserBankInfo userBankInfo = userBankInfoRepository.findByAccount(accNum).orElseThrow(Exception::new);
        if(userBankInfo.getBalance().compareTo(exResDto.getTotal_krw_amount()) < 0){
            throw new Exception();
        }
//        1.건당 5천불을 초과 && 지정외국환은행이 없다면 Error
        if(exResDto.getUsd_amount().compareTo(BigDecimal.valueOf(5000)) > 0 && user.getTrading_bank_info_yn().equals("N")){
            throw new Exception();
        }

//        2.total 5만불을 초과하면 Error
        UserStatics ust = userStaticsRepository.findById(user.getId()).orElseThrow(Exception::new);
        if(ust.getTotal_remit_usd().add(ust.getOther_bank_total_remit_usd()).add(exResDto.getUsd_amount()).compareTo(BigDecimal.valueOf(50000))>0){
            throw new Exception();
        }
    }
}
