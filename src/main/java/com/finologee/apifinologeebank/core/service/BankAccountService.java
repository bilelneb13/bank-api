package com.finologee.apifinologeebank.core.service;

import com.finologee.apifinologeebank.core.model.BankAccount;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface BankAccountService {
    Optional<BankAccount> getBankAccountByAccountNumber(String accNumber);

    BankAccount createBankAccount(BankAccount bankAccount);

    BankAccount updateBankAccount(UUID id, BankAccount bankAccount);

    void deleteBankAccount(UUID id);

    Set<BankAccount> getBankAccountsByUsername(String authenticatedUsername);
}
