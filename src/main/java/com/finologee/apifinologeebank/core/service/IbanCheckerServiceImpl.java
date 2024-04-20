package com.finologee.apifinologeebank.core.service;

import com.finologee.apifinologeebank.core.dto.IBANDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class IbanCheckerServiceImpl implements IbanCheckerService {
    private final RestTemplate restTemplate;
    @Value("${iban_checker.api.key}")
    String apiKey;

    @Override
    public boolean callIbanChecker(String iban) {
        String apiUrl = "https://api.api-ninjas.com/v1/iban?iban=" + iban;
        HttpHeaders headers = new HttpHeaders();
        headers.set("accept", "application/json");
        headers.set("X-Api-Key", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<IBANDto> response = restTemplate.exchange(
                apiUrl,
                HttpMethod.GET,
                entity,
                IBANDto.class
        );

        IBANDto responseBody = response.getBody();
        return Objects.requireNonNull(responseBody).isValid();
    }
}
