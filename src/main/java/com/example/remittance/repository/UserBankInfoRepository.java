package com.example.remittance.repository;

import com.example.remittance.domain.UserBankInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBankInfoRepository extends JpaRepository<UserBankInfo, Long> {
    Optional<UserBankInfo> findByAccount(String accountNumber);
}
