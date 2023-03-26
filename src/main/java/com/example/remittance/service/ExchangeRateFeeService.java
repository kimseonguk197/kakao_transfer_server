package com.example.remittance.service;

import com.example.remittance.domain.*;
import com.example.remittance.dto.ExchangeRateFeeRequestDto;
import com.example.remittance.dto.ExchangeRateFeeResponseDto;
import com.example.remittance.repository.ExchangeRateInfoRepository;
import com.example.remittance.repository.TransferFeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@Transactional
public class ExchangeRateFeeService {
    private final UserService userService;
    private final TransferFeeRepository transferFeeRepository;
    private final ExchangeRateInfoRepository exchangeRateInfoRepository;

    public ExchangeRateFeeService(UserService userService, TransferFeeRepository transferFeeRepository, ExchangeRateInfoRepository exchangeRateInfoRepository) {
        this.userService = userService;
        this.transferFeeRepository = transferFeeRepository;
        this.exchangeRateInfoRepository = exchangeRateInfoRepository;
    }

//        우대환율 적용 후 환율 : 매매기준율 + (매도기준율-매매기준율)*우대율
    public ExchangeRateFeeResponseDto findTotalRateFee(ExchangeRateFeeRequestDto erf) throws Exception {
        ExchangeRateFeeResponseDto exResDto = new ExchangeRateFeeResponseDto();
        ExchangeRateInfo eri =  exchangeRateInfoRepository.findFirstByCurrencyOrderByIdDesc(erf.getTarget_currency());
        User user = userService.findByEmail(erf.getEmail());
//        (매도기준율-매매기준율)*우대율
        BigDecimal gap = eri.getSelling_exchange_rate().subtract(eri.getBasic_exchange_rate()).multiply(user.getDiscount_percentage().divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
//        우대환율 적용 후 매도환율 : 매매기준율 + gap
        BigDecimal exchange_rate = eri.getBasic_exchange_rate().add(gap);
//        우대환율 적용 krw 환산 금액
        BigDecimal krw_amount = erf.getAmount_target().multiply(exchange_rate);
//        매매기준율 기준 USD 환산 송금금액 : (송금금액(외화) * 매매기준율)/USD매매기준율
        BigDecimal usd_amount = erf.getAmount_target().multiply(exchangeRateInfoRepository.findFirstByCurrencyOrderByIdDesc(erf.getTarget_currency()).getBasic_exchange_rate()).divide(exchangeRateInfoRepository.findFirstByCurrencyOrderByIdDesc("USD").getBasic_exchange_rate(), 2, RoundingMode.HALF_UP);
//        수수료 : 테이블에서 조회
        BigDecimal fee = transferFeeRepository.findByAmount(erf.getAmount_target(), erf.getTarget_currency(), erf.getTarget_country()).getFee();
//        총예상 비용 : 수수료 + 매도기준 krw 환산금액
        BigDecimal total_krw_amount = krw_amount.add(fee);

        exResDto.setExchangeRateInfo(eri);
        exResDto.setExchange_rate(exchange_rate);
        exResDto.setKrw_amount(krw_amount);
        exResDto.setUsd_amount(usd_amount);
        exResDto.setFee(fee);
        exResDto.setTotal_krw_amount(total_krw_amount);

        return exResDto;
    }

}
