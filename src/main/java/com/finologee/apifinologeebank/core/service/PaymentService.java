package com.finologee.apifinologeebank.core.service;

import com.finologee.apifinologeebank.core.dto.PaymentDto;
import com.finologee.apifinologeebank.core.model.BankAccount;
import com.finologee.apifinologeebank.core.model.Payment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentService {
    Optional<Payment> getPaymentById(UUID id);

    PaymentDto createPayment(PaymentDto payment);

    Payment updatePayment(UUID id, Payment payment);

    void deletePayment(UUID id);

    List<PaymentDto> getAllUserPayments(int page, int size);
}
