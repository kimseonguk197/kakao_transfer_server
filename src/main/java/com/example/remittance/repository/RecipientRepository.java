package com.example.remittance.repository;

import com.example.remittance.domain.Recipient;
import com.example.remittance.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {
}
