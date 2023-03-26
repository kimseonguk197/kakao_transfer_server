package com.example.remittance.repository;

import com.example.remittance.domain.UserStatics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStaticsRepository extends JpaRepository<UserStatics, Long> {
}
