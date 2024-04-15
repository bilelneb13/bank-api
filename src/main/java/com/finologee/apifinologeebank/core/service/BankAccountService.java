package com.finologee.apifinologeebank.core.service;

import com.finologee.apifinologeebank.core.model.BankAccount;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankAccountService {
    Optional<BankAccount> getBankAccountById(UUID id);

    BankAccount createBankAccount(BankAccount bankAccount);

    BankAccount updateBankAccount(UUID id, BankAccount bankAccount);

    void deleteBankAccount(UUID id);

    List<BankAccount> getAllBankAccounts();

    List<BankAccount> getBankAccountsByUsername(String authenticatedUsername);
}
