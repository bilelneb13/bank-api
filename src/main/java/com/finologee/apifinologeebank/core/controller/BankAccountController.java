package com.finologee.apifinologeebank.core.controller;

import com.finologee.apifinologeebank.core.model.BankAccount;
import com.finologee.apifinologeebank.core.service.BankAccountService;
import com.finologee.apifinologeebank.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping(path = "${base_url}/accounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @GetMapping
    public ResponseEntity<Set<BankAccount>> getAllBankAccounts() {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        Set<BankAccount> accounts = bankAccountService.getBankAccountsByUsername(authenticatedUsername);
        return ResponseEntity.ok(accounts);
    }
}
