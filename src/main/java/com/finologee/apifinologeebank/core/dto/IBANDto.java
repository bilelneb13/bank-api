package com.finologee.apifinologeebank.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IBANDto {
    private String iban;
    private String bankName;
    private String accountNumber;
    private String bankCode;
    private String country;
    private boolean valid;
    private String bban;
}
