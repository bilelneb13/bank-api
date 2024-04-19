package com.finologee.apifinologeebank.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finologee.apifinologeebank.core.config.AppConfig;
import com.finologee.apifinologeebank.core.config.SecurityConfig;
import com.finologee.apifinologeebank.core.config.UserManagerConfig;
import com.finologee.apifinologeebank.core.dto.PaymentDto;
import com.finologee.apifinologeebank.core.jwt.JwtTokenGenerator;
import com.finologee.apifinologeebank.core.repository.UserRepository;
import com.finologee.apifinologeebank.core.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class, SecurityConfig.class})
@WebAppConfiguration
@SpringBootTest
@EnableWebMvc
public class PaymentControllerTests {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private PaymentController paymentController;
    @Mock
    private SecurityConfig securityConfig;
    @MockBean
    private UserManagerConfig userManagerConfig;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @WithMockUser(username = "user1", password = "1234")
    @Test
    public void testGetAllUserPayments() throws Exception {
        List<PaymentDto> mockPayments = new ArrayList<>(PaymentDtoGenerator.generateRandomPayments(10, "ACC1"));
        // Mock service method
        when(paymentService.getAllUserPayments(0, 10)).thenReturn(mockPayments);


        // Perform GET request and verify status code
        mvc.perform(get("/api/v1/payments").param("page", "0").param("size", "10")
                                           .contentType(MediaType.APPLICATION_JSON))
           .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user1", password = "1234")
    public void testGetAllUserPaymentsByCriteria() throws Exception {
        // Mocked data
        List<PaymentDto> mockPayments = new ArrayList<>(PaymentDtoGenerator.generateRandomPayments(10, "ACC1"));

        // Mock service method
        when(paymentService.getAllUserPaymentsByBeneficiaryAndPeriod(anyString(), any(String.class), any(String.class), anyInt(), anyInt())).thenReturn(mockPayments);

        // Perform GET request and verify status code and response content
        mvc.perform(get("/api/v1/payments/beneficiary/{accountNumber}", "ACC1").param("start_date", "2024-01-01")
                                                                               .param("end_date", "2024-04-01")
                                                                               .param("page", "0").param("size", "5")
           )
           .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username = "user1", password = "1234")
    public void testCreatePayment() throws Exception {
        // Mocked data
        PaymentDto mockPayment = new PaymentDto();
        mockPayment.setAmount(BigDecimal.TEN);
        mockPayment.setCurrency(Currency.getInstance("USD"));
        mockPayment.setBeneficiaryAccountNumber("DEXX YY");
        mockPayment.setGiverAccountNumber("ACC1");
        // Set other fields of the mockPayment as needed

        // Mock the behavior of the paymentService.createPayment() method
        when(paymentService.createPayment(any(PaymentDto.class))).thenReturn(mockPayment);

        // Perform POST request and verify status code and response content
        mvc.perform(post("/api/v1/payments").contentType(MediaType.APPLICATION_JSON_VALUE)
                                            .accept(MediaType.APPLICATION_JSON_VALUE)
                                            .content(asJsonString(PaymentDto.builder().amount(BigDecimal.TEN)
                                                                            .beneficiaryAccountNumber("ACC2")
                                                                            .giverAccountNumber("ACC1")
                                                                            .currency(Currency.getInstance("EUR"))
                                                                            .beneficiaryName("John Smith").build())))
           .andDo(MockMvcResultHandlers.print())
           .andExpect(status().is2xxSuccessful());
        // Add more assertions to verify other fields of the response if needed
    }
    @Test
    @WithMockUser(username = "user1", password = "1234")
    public void testDeletePayment() throws Exception {

        // Set other fields of the mockPayment as needed

        // Mock the behavior of the paymentService.createPayment() method
        when(paymentService.deletePayment(any(UUID.class))).thenReturn(ResponseEntity.noContent().build());

        // Perform POST request and verify status code and response content
        // Perform DELETE request with payment ID and verify status code
        UUID paymentId = UUID.randomUUID(); // Replace with a valid payment ID
        mvc.perform(delete("/api/v1/payments/{id}", paymentId)
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

}