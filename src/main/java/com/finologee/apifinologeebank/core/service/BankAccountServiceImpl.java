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
    public Set<BankAccount> getBankAccountsByUsername(String authenticatedUsername) {
        Optional<User> userOptional = userService.getUserByUsername(authenticatedUsername);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getBankAccounts();
        } else {
            throw new RuntimeException("User not found with username: " + authenticatedUsername);
        }
    }
}
