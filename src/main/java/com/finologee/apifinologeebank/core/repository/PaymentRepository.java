package com.finologee.apifinologeebank.core.repository;

import com.finologee.apifinologeebank.core.model.BankAccount;
import com.finologee.apifinologeebank.core.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findAllByGiverAccount_AccountNumber(String accountNumber);
    Page<Payment> findAllByGiverAccountIn(Set<BankAccount> accounts, Pageable pageable);
    Page<Payment> findAllByBeneficiaryAccountNumberAndCreationDateIsBetween(String beneficiaryNumber, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
