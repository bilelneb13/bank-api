package com.finologee.apifinologeebank.core.util;

import com.finologee.apifinologeebank.core.model.*;
import com.finologee.apifinologeebank.core.repository.BalanceRepository;
import com.finologee.apifinologeebank.core.repository.BankAccountRepository;
import com.finologee.apifinologeebank.core.repository.PaymentRepository;
import com.finologee.apifinologeebank.core.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final BalanceRepository balanceRepository;
    private final PaymentRepository paymentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        // Initialize bank accounts
        BankAccount account1 = BankAccount.builder()
                .accountNumber("ACC1")
                .accountName("Account1")
                .bankAccountStatus(BankAccountStatus.ENABLED)
                .build();

        BankAccount account2 = BankAccount.builder()
                .accountNumber("ACC2")
                .accountName("Account2")
                .bankAccountStatus(BankAccountStatus.ENABLED)
                .build();
        bankAccountRepository.saveAll(Arrays.asList(account1, account2));
        // Initialize users
        User user1 = User.builder().username("user1").password(passwordEncoder.encode("1234")).address("1234 address 1").bankAccounts(Arrays.asList(account1,account2)).build();
        User user2 = User.builder().username("user2").password(passwordEncoder.encode("1234")).address("1234 address 2").bankAccounts(Collections.singletonList(account1)).build();
        User user3 = User.builder().username("user3").password(passwordEncoder.encode("1234")).address("1234 address 3").build();


        userRepository.saveAll(Arrays.asList(user1,user2,user3));
        // Create balances
        Balance balance1 = Balance.builder()
                .amount(new BigDecimal("1000.00"))
                .currency(Currency.getInstance("EUR"))
                .balanceType(BalanceType.AVAILABLE)
                .bankAccount(account1)
                .build();

        Balance balance2 = Balance.builder()
                .amount(new BigDecimal("2000.00"))
                .currency(Currency.getInstance("USD"))
                .balanceType(BalanceType.AVAILABLE)
                .bankAccount(account2)
                .build();

        balanceRepository.saveAll(Arrays.asList(balance1, balance2));

        Payment payment1 = Payment.builder()
                .amount(new BigDecimal("500.00"))
                .currency(Currency.getInstance("EUR"))
                .beneficiaryName("John Doe")
                .communication("Payment for services")
                .creationDate(LocalDateTime.now())
                .paymentStatus(PaymentStatus.EXECUTED)
                .beneficiaryAccountNumber(account2.getAccountNumber())
                .giverAccount(account1)
                .build();

        Payment payment2 = Payment.builder()
                .amount(new BigDecimal("100.00"))
                .currency(Currency.getInstance("EUR"))
                .beneficiaryName("Jane Doe")
                .communication("Payment for goods")
                .creationDate(LocalDateTime.now())
                .paymentStatus(PaymentStatus.EXECUTED)
                .beneficiaryAccountNumber(account2.getAccountNumber())
                .giverAccount(account1)
                .build();

        paymentRepository.saveAll(Arrays.asList(payment1, payment2));
    }

}
