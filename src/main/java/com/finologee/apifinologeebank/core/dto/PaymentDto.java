package com.finologee.apifinologeebank.core.dto;

import com.finologee.apifinologeebank.core.model.BankAccount;
import com.finologee.apifinologeebank.core.model.PaymentStatus;
import jakarta.persistence.*;
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
import java.util.UUID;

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
    private String giverAccountNumber;
    @NotBlank
    private String beneficiaryAccountNumber;
    @NotBlank
    private String beneficiaryName;
    private String communication;
}