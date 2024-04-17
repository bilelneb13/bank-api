package com.finologee.apifinologeebank.core.service;

import com.finologee.apifinologeebank.core.model.BankAccount;
import com.finologee.apifinologeebank.core.model.User;
import com.finologee.apifinologeebank.core.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final UserService userService;



    @Override
    public Optional<BankAccount> getBankAccountByAccountNumber(String accNumber) {
        return bankAccountRepository.findByAccountNumber(accNumber);
    }

    @Override
    public BankAccount createBankAccount(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public BankAccount updateBankAccount(UUID id, BankAccount bankAccount) {
        BankAccount existingBankAccount = bankAccountRepository.findById(id)
                                                               //todo change exception
                                                               .orElseThrow(() -> new RuntimeException("BankAccount not found"));
        BeanUtils.copyProperties(bankAccount, existingBankAccount);
        return bankAccountRepository.save(existingBankAccount);
    }

    @Override
    public void deleteBankAccount(UUID id) {
        bankAccountRepository.deleteById(id);
    }

/*    @Override
    public Set<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }*/

    @Override
    public Set<BankAccount> getBankAccountsByUsername(String authenticatedUsername) {
        // Find the user by username
        Optional<User> userOptional = userService.getUserByUsername(authenticatedUsername);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Get the bank accounts associated with the user
            return user.getBankAccounts();
        } else {
            // Handle case when user is not found (optional)
            throw new RuntimeException("User not found with username: " + authenticatedUsername);
        }
    }
}
