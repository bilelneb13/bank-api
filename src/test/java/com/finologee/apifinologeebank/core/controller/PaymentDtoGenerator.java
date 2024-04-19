package com.finologee.apifinologeebank.core.controller;

import com.finologee.apifinologeebank.core.dto.PaymentDto;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Random;

public class PaymentDtoGenerator {

    private static final Random random = new Random();

    public static List<PaymentDto> generateRandomPayments(int numberOfPayments, String giverAccountNumber) {
        List<PaymentDto> payments = new ArrayList<>();
        for (int i = 0; i < numberOfPayments; i++) {
            PaymentDto payment = new PaymentDto();
            payment.setAmount(BigDecimal.valueOf(random.nextDouble() * 1000)); // Random amount
            payment.setCurrency(Currency.getInstance("USD")); // Currency can be any random currency
            payment.setGiverAccountNumber(giverAccountNumber); // Set the same giver account number
            payment.setBeneficiaryAccountNumber(generateRandomIban()); // Random beneficiary account number
            payment.setBeneficiaryName(RandomStringUtils.randomAlphabetic(10)); // Random beneficiary name
            payment.setCommunication(RandomStringUtils.randomAlphabetic(20)); // Random communication
            payment.setCreationDate(LocalDateTime.now()
                                                 .minusDays(random.nextInt(30))); // Random creation date within last 30 days
            payments.add(payment);
        }
        return payments;
    }

    private static String generateRandomIban() {
        // This is just a placeholder for generating a random IBAN
        return "DE" + RandomStringUtils.randomNumeric(20); // Random IBAN format (example: DE followed by 20 random digits)
    }
}
