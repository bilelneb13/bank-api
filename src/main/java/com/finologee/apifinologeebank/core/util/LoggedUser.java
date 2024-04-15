package com.finologee.apifinologeebank.core.util;

import com.finologee.apifinologeebank.core.config.UserManagerConfig;
import com.finologee.apifinologeebank.core.exception.NotFoundException;
import com.finologee.apifinologeebank.core.model.BankAccount;
import com.finologee.apifinologeebank.core.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
@RequiredArgsConstructor
public class LoggedUser {
    static UserManagerConfig userManagerConfig;
    public static String getAccountNumber() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof User user) {
                return user.getUsername();
            }
        }
        throw new NotFoundException("Account number not found in Security Context.");
    }
    public static List<BankAccount> getLoggedAccounts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();

            if (principal instanceof User user) {
                User userDetails = (User) userManagerConfig.loadUserByUsername(user.getUsername());
                return user.getBankAccounts();
            }
        }
        throw new NotFoundException("Account number not found in Security Context.");
    }
}