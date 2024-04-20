package com.finologee.apifinologeebank.core.dto;

import com.finologee.apifinologeebank.core.annotation.IbanChecker;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentDto {
    @NotNull
    @Positive
    private BigDecimal amount;
    @NotNull
    private Currency currency;
    @NotNull
    @IbanChecker
    private String giverAccountNumber;
    @NotBlank
    @IbanChecker
    private String beneficiaryAccountNumber;
    @NotBlank
    private String beneficiaryName;
    private String communication;
    private LocalDateTime creationDate;
}