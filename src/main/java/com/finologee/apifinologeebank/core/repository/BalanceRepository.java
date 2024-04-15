package com.finologee.apifinologeebank.core.repository;

import com.finologee.apifinologeebank.core.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BalanceRepository extends JpaRepository<Balance, UUID> {
}