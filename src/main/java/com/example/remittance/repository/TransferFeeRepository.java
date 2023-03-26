package com.example.remittance.repository;

import com.example.remittance.domain.TransferFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface TransferFeeRepository extends JpaRepository<TransferFee, Long> {
    //    jpql방식
    @Query("select t from TransferFee t where t.amount_start <= :amount and t.amount_end >= :amount " +
            "and t.target_currency = :target_currency and t.target_country = :target_country")
    TransferFee findByAmount(BigDecimal amount, String target_currency, String target_country );
}
