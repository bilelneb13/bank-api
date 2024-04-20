package com.finologee.apifinologeebank.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finologee.apifinologeebank.core.config.AppConfig;
import com.finologee.apifinologeebank.core.config.CustomLogoutHandler;
import com.finologee.apifinologeebank.core.config.SecurityConfig;
import com.finologee.apifinologeebank.core.config.UserManagerConfig;
import com.finologee.apifinologeebank.core.jwt.JwtTokenGenerator;
import com.finologee.apifinologeebank.core.jwt.TokenUtils;
import com.finologee.apifinologeebank.core.model.Balance;
import com.finologee.apifinologeebank.core.model.BalanceType;
import com.finologee.apifinologeebank.core.model.BankAccount;
import com.finologee.apifinologeebank.core.model.BankAccountStatus;
import com.finologee.apifinologeebank.core.repository.UserRepository;
import com.finologee.apifinologeebank.core.service.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, SecurityConfig.class})
@WebAppConfiguration
@SpringBootTest
@EnableWebMvc
public class AccountControllerTests {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private BankAccountService bankAccountService;
    @MockBean
    private BankAccountController accountController;
    @Mock
    private SecurityConfig securityConfig;
    @MockBean
    private UserManagerConfig userManagerConfig;
    @MockBean
    private CustomLogoutHandler customLogoutHandler;
    @MockBean
    private TokenUtils tokenUtils;


    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }


    @Test
    @WithMockUser(username = "user1", password = "1234")
    public void testGetAllBankAccounts() throws Exception {
        // Mocked data
        Set<BankAccount> mockAccounts = Set.of(BankAccount.builder().accountNumber("ACC1").accountName("Account 1")
                                                          .bankAccountStatus(BankAccountStatus.ENABLED)
                                                          .balances(List.of(Balance.builder().amount(BigDecimal.TEN)
                                                                                   .balanceType(BalanceType.AVAILABLE)
                                                                                   .currency(Currency.getInstance("EUR"))
                                                                                   .build())).build());

        // Mock service method
        when(bankAccountService.getBankAccountsByUsername(anyString())).thenReturn(mockAccounts);

        // Perform GET request and verify status code and response content
        mvc.perform(get("/api/v1/accounts"))
           .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());

    }

}