package com.example.remittance.repository;

import com.example.remittance.domain.Transfer;
import com.example.remittance.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findAllByUserAndCreateDateBetween(User user, LocalDateTime start, LocalDateTime end);
    List<Transfer> findAllByUserOrderByCreateDateDesc(User user);
}
