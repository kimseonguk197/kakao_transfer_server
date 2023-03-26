package com.example.remittance.repository;

import com.example.remittance.domain.ExchangeRateInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExchangeRateInfoRepository extends JpaRepository<ExchangeRateInfo, Long> {
    ExchangeRateInfo findFirstByCurrencyOrderByIdDesc(String currency);

}
