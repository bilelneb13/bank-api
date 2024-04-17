package com.finologee.apifinologeebank.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private BigDecimal amount;
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private BalanceType balanceType;
    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    @JsonIgnore
    private BankAccount bankAccount;
}
