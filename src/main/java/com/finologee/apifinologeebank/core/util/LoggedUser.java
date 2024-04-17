package com.finologee.apifinologeebank.core.util;

import com.finologee.apifinologeebank.core.exception.NotFoundException;
import com.finologee.apifinologeebank.core.model.BankAccount;
import com.finologee.apifinologeebank.core.model.User;
import com.finologee.apifinologeebank.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class LoggedUser {
    private final UserService userService;


    public Set<BankAccount> getLoggedAccounts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Optional<User> userDetails = userService.getUserByUsername(authentication.getName());
            if (userDetails.isPresent())
                return userDetails.get().getBankAccounts();
            throw new NotFoundException("User not found ...");
        }

        throw new NotFoundException("Account number not found in Security Context.");
    }
}