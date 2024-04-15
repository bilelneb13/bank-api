package com.finologee.apifinologeebank.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private BigDecimal amount;
    private Currency currency;
    @ManyToOne
    @JoinColumn(name = "giver_account_id")
    private BankAccount giverAccount;
    private String beneficiaryAccountNumber;
    private String beneficiaryName;
    private String communication;
    private LocalDateTime creationDate;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
