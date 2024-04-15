package com.finologee.apifinologeebank.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true)
    private String accountNumber;
    @ManyToMany(mappedBy = "bankAccounts")
    @JsonIgnore
    private List<User> users;
    private String accountName;
    @OneToMany(mappedBy = "bankAccount")
    private List<Balance> balances;
    @Enumerated(EnumType.STRING)
    private BankAccountStatus bankAccountStatus;
}
