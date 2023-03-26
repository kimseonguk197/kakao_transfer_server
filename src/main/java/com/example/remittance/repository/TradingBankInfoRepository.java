package com.example.remittance.repository;

import com.example.remittance.domain.TradingBankInfo;
import com.example.remittance.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradingBankInfoRepository extends JpaRepository<TradingBankInfo, Long> {
    List<TradingBankInfo> findAllByUser(User us);
}
