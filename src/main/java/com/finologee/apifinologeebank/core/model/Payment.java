package com.finologee.apifinologeebank.core.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
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
