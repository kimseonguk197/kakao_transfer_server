package com.example.remittance.etc;

import com.example.remittance.domain.*;
import com.example.remittance.repository.ExchangeRateInfoRepository;
import com.example.remittance.repository.TransferFeeRepository;
import com.example.remittance.repository.UserBankInfoRepository;
import com.example.remittance.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeding  implements CommandLineRunner {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ExchangeRateInfoRepository exchangeRateInfoRepository;
    @Autowired
    TransferFeeRepository transferFeeRepository;
    @Autowired
    UserBankInfoRepository userBankInfoRepository;

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
    private void loadUserData() {
        if(userRepository.count()==0){
            User user = User.createUser("INDIVIDUAL SENDER", "test@naver.com", "N",
                   "seoul", UserStatics.createUserStatics(BigDecimal.valueOf(0), BigDecimal.valueOf(0)));
            UserBankInfo userBankInfo = UserBankInfo.createUserBankInfo("123456789", BigDecimal.valueOf(5000000), user);
            userBankInfoRepository.save(userBankInfo);
        }
        if(transferFeeRepository.count()==0){
            TransferFee transferFee1 = TransferFee.builder()
                    .fee(BigDecimal.valueOf(5000))
                    .target_currency("USD")
                    .target_country("US")
                    .start(BigDecimal.valueOf(0))
                    .end(BigDecimal.valueOf(5000))
                    .build();
            TransferFee transferFee2 = TransferFee.builder()
                    .fee(BigDecimal.valueOf(10000))
                    .target_currency("USD")
                    .target_country("US")
                    .start(BigDecimal.valueOf(5001))
                    .end(BigDecimal.valueOf(99999999))
                    .build();
            List<TransferFee> lst = new ArrayList<>(Arrays.asList(transferFee1,  transferFee2));
            transferFeeRepository.saveAll(lst);

        }
        if (exchangeRateInfoRepository.count() == 0) {
            ExchangeRateInfo exchangeRateInfo = ExchangeRateInfo.builder()
                    .roundId(1L)
                    .currency("USD")
                    .country("US")
                    .basic_exchange_rate(BigDecimal.valueOf(1100))
                    .buying_exchange_rate(BigDecimal.valueOf(1000))
                    .selling_exchange_rate(BigDecimal.valueOf(1200))
                    .build();
            exchangeRateInfoRepository.save(exchangeRateInfo);
        }
    }

}
