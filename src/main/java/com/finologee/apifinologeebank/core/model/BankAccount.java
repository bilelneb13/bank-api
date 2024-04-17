package com.finologee.apifinologeebank.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String accountNumber;
    @ManyToMany(mappedBy = "bankAccounts")
    @JsonIgnore
    private Set<User> users;
    private String accountName;
    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY)
    private List<Balance> balances;
    @Enumerated(EnumType.STRING)
    private BankAccountStatus bankAccountStatus;
}
