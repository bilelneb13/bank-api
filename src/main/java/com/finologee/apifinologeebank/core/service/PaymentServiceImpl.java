package com.finologee.apifinologeebank.core.service;

import com.finologee.apifinologeebank.core.dto.PaymentDto;
import com.finologee.apifinologeebank.core.exception.BlockedPaymentException;
import com.finologee.apifinologeebank.core.exception.NotFoundException;
import com.finologee.apifinologeebank.core.exception.SameAccountException;
import com.finologee.apifinologeebank.core.exception.UnsufficientFundsException;
import com.finologee.apifinologeebank.core.mapper.PaymentMapper;
import com.finologee.apifinologeebank.core.model.*;
import com.finologee.apifinologeebank.core.repository.PaymentRepository;
import com.finologee.apifinologeebank.core.util.LoggedUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final LoggedUser loggedUser;
    private final BalanceService balanceService;
    private final BankAccountService bankAccountService;

    @Override
    public Optional<Payment> getPaymentById(UUID id) {
        return paymentRepository.findById(id);
    }

    @Override
    @Transactional(dontRollbackOn = {BlockedPaymentException.class})
    public PaymentDto createPayment(PaymentDto payment) {
        //validate preconditions
        if (loggedUser.getLoggedAccounts().stream()
                      .noneMatch(bankAccount -> bankAccount.getAccountNumber()
                                                           .equals(payment.getGiverAccountNumber())))
            throw new NotFoundException("Account Number does match not any client's accounts");
        if (payment.getGiverAccountNumber().equals(payment.getBeneficiaryAccountNumber()))
            throw new SameAccountException("Both legs of the transfer are the same");
        if (bestMatchingBalance(payment).isEmpty()) throw new UnsufficientFundsException("InsufficientFunds ...");
        if (Set.of("LUXX XX", "LUYY YY").contains(payment.getBeneficiaryAccountNumber())) {

            executePaymentWithStatus(payment, getGiverAccount(payment).get(), PaymentStatus.BLOCKED);

            throw new BlockedPaymentException("Blocked Payment ...");

        }
        Optional<Balance> optionalBalance = bestMatchingBalance(payment);
        Optional<Balance> targetBalance = getTargetBalance(payment);
        BigDecimal amount = payment.getAmount();
        if (optionalBalance.isPresent()) {
            Balance balance = optionalBalance.get();
            debitAccount(balance, amount);
            targetBalance.ifPresent(value -> creditAccount(value, amount));
            return paymentMapper.toDto(executePaymentWithStatus(payment, balance.getBankAccount(), PaymentStatus.EXECUTED));
        }

        return payment;
    }

    private Optional<Balance> getTargetBalance(PaymentDto payment) {
        Optional<BankAccount> targetAccount = bankAccountService.getBankAccountByAccountNumber(payment.getBeneficiaryAccountNumber());
        if (targetAccount.isPresent()) {
            BankAccount acc = targetAccount.get();
            Optional<Balance> matchingBalance = acc.getBalances().stream()
                                                   .filter(balance -> Objects.equals(balance.getBalanceType(), BalanceType.AVAILABLE))
                                                   .findFirst();
            return Optional.ofNullable(matchingBalance.orElseGet(() -> balanceService.createEmptyBalance(Balance
                    .builder().balanceType(BalanceType.AVAILABLE).amount(BigDecimal.ZERO)
                    .currency(Currency.getInstance("EUR")).bankAccount(targetAccount.get()).build())));
        }
        return Optional.empty();
    }

    private void debitAccount(Balance sourceBalance, BigDecimal amount) {
        sourceBalance.setAmount(sourceBalance.getAmount().subtract(amount));
        balanceService.updateBalance(sourceBalance);
    }

    private void creditAccount(Balance targetBalance, BigDecimal amount) {
        targetBalance.setAmount(targetBalance.getAmount().add(amount));
        balanceService.updateBalance(targetBalance);
    }

    private Payment executePaymentWithStatus(PaymentDto paymentDto, BankAccount sourceAccount, PaymentStatus
            paymentStatus) {
        Payment payment = paymentMapper.paymentDtoToPayment(paymentDto, sourceAccount, paymentStatus);
        payment.setPaymentStatus(paymentStatus);
        return paymentRepository.save(payment);
    }

    private Optional<Balance> bestMatchingBalance(PaymentDto payment) {
        Optional<BankAccount> giverAccount = getGiverAccount(payment);
        Optional<Balance> bestMatchingBalance = giverAccount.get().getBalances().stream()
                                                            .filter(bal -> bal.getAmount()
                                                                              .compareTo(payment.getAmount()) >= 0)
                                                            .filter(bal -> bal.getCurrency()
                                                                              .equals(payment.getCurrency()))
                                                            .findFirst();
        // If no matching currency balance was found, return the first exceeding balance
        if (bestMatchingBalance.isEmpty()) {
            // Find the first balance that exceeds the payment amount

            // Return the first exceeding balance if found, otherwise return null
            return giverAccount.get().getBalances().stream()
                               .filter(balance -> balance.getAmount().compareTo(payment.getAmount()) >= 0)
                               .findFirst();
        }

        // Return the best matching balance
        return bestMatchingBalance;

    }

    private Optional<BankAccount> getGiverAccount(PaymentDto payment) {
        return loggedUser.getLoggedAccounts().stream()
                         .filter(bankAccount -> bankAccount.getAccountNumber()
                                                           .equals(payment.getGiverAccountNumber()))
                         .findFirst();
    }

    @Override
    public Payment updatePayment(UUID id, Payment payment) {
        Payment existingPayment = paymentRepository.findById(id)
                                                   //todo change exception
                                                   .orElseThrow(() -> new RuntimeException("Payment not found"));
        BeanUtils.copyProperties(payment, existingPayment);
        return paymentRepository.save(existingPayment);
    }

    @Override
    public ResponseEntity<?> deletePayment(UUID id) {
        // Retrieve authenticated user's username
        Set<BankAccount> accounts = loggedUser.getLoggedAccounts();

        // Check if the payment belongs to the authenticated user
        if (!doesPaymentBelongToUser(id, accounts)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                 .body("Payment Not found or don't have the to execute this operation");
        }

        // Delete the payment
        paymentRepository.deleteById(id);
        return ResponseEntity.ok("Payment deleted successfully.");

    }

    private boolean doesPaymentBelongToUser(UUID id, Set<BankAccount> accounts) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        return optionalPayment.filter(payment -> accounts.contains(payment.getGiverAccount())).isPresent();
    }

    @Override
    public List<PaymentDto> getAllUserPayments(int page, int size) {
        // Create Pageable object for pagination and sorting
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "creationDate"));

        // Retrieve Page of payments for the user's bank accounts
        Page<Payment> allByGiverAccountIn = paymentRepository.findAllByGiverAccountIn(loggedUser.getLoggedAccounts(), pageable);

        // Convert Page of payments to List of PaymentDto

        return allByGiverAccountIn.getContent().stream()
                                  .map(paymentMapper::toDto)
                                  .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> getAllUserPaymentsByBeneficiaryAndPeriod(String accountNumber, String startDate,
                                                                     String endDate, int page,
                                                                     int size) {
        LocalDate startLocalDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endLocalDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
        // Create Pageable object for pagination and sorting
        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "creationDate"));

        // Retrieve Page of payments for the user's bank accounts
        Page<Payment> allBeneficiaryAccountBetween = paymentRepository.findAllByBeneficiaryAccountNumberAndCreationDateIsBetween(
                accountNumber, startLocalDate.atStartOfDay(), endLocalDate.atTime(LocalTime.MAX), pageable);

        // Convert Page of payments to List of PaymentDto

        return allBeneficiaryAccountBetween.getContent().stream()
                                  .map(paymentMapper::toDto)
                                  .collect(Collectors.toList());
    }
}
