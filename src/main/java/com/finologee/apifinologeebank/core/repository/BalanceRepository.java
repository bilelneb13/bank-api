package com.finologee.apifinologeebank.core.repository;

import com.finologee.apifinologeebank.core.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface BalanceRepository extends JpaRepository<Balance, UUID> {
}