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
import java.util.Set;

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
        BankAccount account3 = BankAccount.builder()
                                          .accountNumber("ACC3")
                                          .accountName("Account3")
                                          .bankAccountStatus(BankAccountStatus.ENABLED)
                                          .build();
        BankAccount account4 = BankAccount.builder()
                                          .accountNumber("ACC4")
                                          .accountName("Account4")
                                          .bankAccountStatus(BankAccountStatus.ENABLED)
                                          .build();
        BankAccount account5 = BankAccount.builder()
                                          .accountNumber("ACC5")
                                          .accountName("Account5")
                                          .bankAccountStatus(BankAccountStatus.BLOCKED)
                                          .build();
        bankAccountRepository.saveAll(Arrays.asList(account1, account2, account3, account4, account5));
        // Initialize users
        User user1 = User.builder().username("user1").password(passwordEncoder.encode("1234")).address("1234 address 1")
                         .bankAccounts(Set.of(account1, account2)).build();
        User user2 = User.builder().username("user2").password(passwordEncoder.encode("1234")).address("1234 address 2")
                         .bankAccounts(Set.of(account1)).build();
        User user3 = User.builder().username("user3").password(passwordEncoder.encode("1234")).address("1234 address 3")
                         .bankAccounts(Set.of(account3, account4)).build();
        User user4 = User.builder().username("user4").password(passwordEncoder.encode("1234")).address("1234 address 4")
                         .bankAccounts(Set.of(account5, account4)).build();


        userRepository.saveAll(Arrays.asList(user1, user2, user3, user4));
        // Create balances
        Balance balance1 = Balance.builder()
                                  .amount(new BigDecimal("100000.00"))
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
        Balance balance21 = Balance.builder()
                                  .amount(new BigDecimal("10.00"))
                                  .currency(Currency.getInstance("EUR"))
                                  .balanceType(BalanceType.AVAILABLE)
                                  .bankAccount(account2)
                                  .build();
        Balance balance3 = Balance.builder()
                                  .amount(new BigDecimal("200.00"))
                                  .currency(Currency.getInstance("USD"))
                                  .balanceType(BalanceType.END_OF_DAY)
                                  .bankAccount(account3)
                                  .build();
        Balance balance4 = Balance.builder()
                                  .amount(new BigDecimal("2000.00"))
                                  .currency(Currency.getInstance("USD"))
                                  .balanceType(BalanceType.AVAILABLE)
                                  .bankAccount(account4)
                                  .build();

        balanceRepository.saveAll(Arrays.asList(balance1, balance2, balance21, balance3, balance4));

        Payment payment1 = Payment.builder()
                                  .amount(new BigDecimal("500.00"))
                                  .currency(Currency.getInstance("EUR"))
                                  .beneficiaryName("John Doe")
                                  .communication("Payment for services")
                                  .creationDate(LocalDateTime.now().minusHours(4))
                                  .paymentStatus(PaymentStatus.EXECUTED)
                                  .beneficiaryAccountNumber(account2.getAccountNumber())
                                  .giverAccount(account1)
                                  .build();

        Payment payment2 = Payment.builder()
                                  .amount(new BigDecimal("100.00"))
                                  .currency(Currency.getInstance("EUR"))
                                  .beneficiaryName("Jane Doe")
                                  .communication("Payment for goods")
                                  .creationDate(LocalDateTime.now().minusWeeks(1))
                                  .paymentStatus(PaymentStatus.EXECUTED)
                                  .beneficiaryAccountNumber(account2.getAccountNumber())
                                  .giverAccount(account1)
                                  .build();

        Payment payment3 = Payment.builder()
                                  .amount(new BigDecimal("120.00"))
                                  .currency(Currency.getInstance("EUR"))
                                  .beneficiaryName("Jane Doe")
                                  .communication("Payment for goods")
                                  .creationDate(LocalDateTime.now())
                                  .paymentStatus(PaymentStatus.EXECUTED)
                                  .beneficiaryAccountNumber(account2.getAccountNumber())
                                  .giverAccount(account1)
                                  .build();
        Payment payment4 = Payment.builder()
                                  .amount(new BigDecimal("13.00"))
                                  .currency(Currency.getInstance("EUR"))
                                  .beneficiaryName("Jane Doe")
                                  .communication("Payment for goods")
                                  .creationDate(LocalDateTime.now().minusDays(5))
                                  .paymentStatus(PaymentStatus.EXECUTED)
                                  .beneficiaryAccountNumber(account2.getAccountNumber())
                                  .giverAccount(account1)
                                  .build();
        Payment payment5 = Payment.builder()
                                  .amount(new BigDecimal("10.00"))
                                  .currency(Currency.getInstance("EUR"))
                                  .beneficiaryName("Jane Doe")
                                  .communication("Payment for goods")
                                  .creationDate(LocalDateTime.now())
                                  .paymentStatus(PaymentStatus.EXECUTED)
                                  .beneficiaryAccountNumber(account5.getAccountNumber())
                                  .giverAccount(account1)
                                  .build();
        Payment payment6 = Payment.builder()
                                  .amount(new BigDecimal("1003.00"))
                                  .currency(Currency.getInstance("EUR"))
                                  .beneficiaryName("Jane Doe")
                                  .communication("Payment for goods")
                                  .creationDate(LocalDateTime.now().minusMinutes(20))
                                  .paymentStatus(PaymentStatus.EXECUTED)
                                  .beneficiaryAccountNumber(account4.getAccountNumber())
                                  .giverAccount(account1)
                                  .build();
        Payment payment7 = Payment.builder()
                                  .amount(new BigDecimal("1200.00"))
                                  .currency(Currency.getInstance("EUR"))
                                  .beneficiaryName("Jane Doe")
                                  .communication("Payment for goods")
                                  .creationDate(LocalDateTime.now().minusMinutes(5))
                                  .paymentStatus(PaymentStatus.EXECUTED)
                                  .beneficiaryAccountNumber(account3.getAccountNumber())
                                  .giverAccount(account1)
                                  .build();
        paymentRepository.saveAll(Arrays.asList(payment1, payment2, payment3, payment4, payment5, payment6, payment7));
    }

}
