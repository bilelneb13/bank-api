package com.finologee.apifinologeebank.core.service;

import com.finologee.apifinologeebank.core.model.BankAccount;
import com.finologee.apifinologeebank.core.model.BankAccountStatus;
import com.finologee.apifinologeebank.core.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class AccountServiceTests {
    @Mock
    private UserService userService; // Mock UserService

    @InjectMocks
    private BankAccountServiceImpl bankAccountService; // Inject BankAccountServiceImpl with mocked UserService

    @Test
    public void testGetBankAccountsByUsername() {
        // Given
        String authenticatedUsername = "user1";
        // Add some mock bank accounts
        BankAccount account1 = BankAccount.builder()
                                          .accountNumber("ACC1")
                                          .accountName("Account1")
                                          .bankAccountStatus(BankAccountStatus.ENABLED)
                                          .build();

        BankAccount account2 = BankAccount.builder()
                                          .accountNumber("ACC2")
                                          .accountName("Account2")
                                          .bankAccountStatus(BankAccountStatus.ENABLED)
                                          .build();
        BankAccount account3 = BankAccount.builder()
                                          .accountNumber("ACC3")
                                          .accountName("Account3")
                                          .bankAccountStatus(BankAccountStatus.ENABLED)
                                          .build();
        Set<BankAccount> expectedBankAccounts = Set.of(account2,account1);

        User user = User.builder().bankAccounts(expectedBankAccounts).username("user1").password("1234").address("address1").build();
        // Mock userService.getUserByUsername(authenticatedUsername) to return a user with bank accounts
        when(userService.getUserByUsername(authenticatedUsername))
                .thenReturn(Optional.of(user));

        // When
        Set<BankAccount> actualBankAccounts = bankAccountService.getBankAccountsByUsername(authenticatedUsername);

        // Then
        assertEquals(expectedBankAccounts.size(), actualBankAccounts.size());
        // Optionally, you can further verify the contents of the sets if necessary
        // assertEquals(expectedBankAccounts, actualBankAccounts);
    }
}
