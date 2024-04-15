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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    private final PaymentRepository paymentRepository;

    @Override
    public Optional<Payment> getPaymentById(UUID id) {
        return paymentRepository.findById(id);
    }

    @Override
    @Transactional
    public PaymentDto createPayment(PaymentDto payment) {
        //validate preconditions
        if (LoggedUser.getLoggedAccounts().stream().noneMatch(bankAccount ->
                bankAccount.getAccountNumber().equals(payment.getGiverAccountNumber())))
            throw new NotFoundException("Account Number does match not any client's accounts");
        if (payment.getGiverAccountNumber().equals(payment.getBeneficiaryAccountNumber()))
            throw new SameAccountException("Both legs of the transfer are the same");
        if (bestMatchingBalance(payment).isEmpty())
            throw new UnsufficientFundsException("InsufficientFunds ...");
        if (Set.of("LUXX XX", "LUYY YY").contains(payment.getBeneficiaryAccountNumber())){
            createBlacklistedPayment(payment);
            throw new BlockedPaymentException("Blocked Payment ...");
        }
        return null;
    }

    private void createBlacklistedPayment(PaymentDto paymentDto) {
        //Payment payment = paymentMapper.paymentDtoToPayment(paymentDto);
        Payment payment = Payment.builder().build();
        payment.setPaymentStatus(PaymentStatus.BLOCKED);
        paymentRepository.save(payment);
    }

    private Optional<Balance> bestMatchingBalance(PaymentDto payment) {
        Optional<BankAccount> giverAccount = LoggedUser.getLoggedAccounts().stream().filter(bankAccount -> bankAccount.getAccountNumber().equals(payment.getGiverAccountNumber())).findFirst();
        Optional<Balance> bestMatchingBalance = giverAccount.get().getBalances().stream().filter(bal -> bal.getAmount().compareTo(payment.getAmount()) >= 0)
                .filter(bal -> bal.getCurrency().equals(payment.getCurrency()))
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

    @Override
    public Payment updatePayment(UUID id, Payment payment) {
        Payment existingPayment = paymentRepository.findById(id)
                //todo change exception
                .orElseThrow(() -> new RuntimeException("Payment not found"));
        BeanUtils.copyProperties(payment, existingPayment);
        return paymentRepository.save(existingPayment);
    }

    @Override
    public void deletePayment(UUID id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
