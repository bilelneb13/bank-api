package com.finologee.apifinologeebank.core.controller;


import com.finologee.apifinologeebank.core.dto.PaymentDto;
import com.finologee.apifinologeebank.core.model.Payment;
import com.finologee.apifinologeebank.core.service.PaymentService;
import com.finologee.apifinologeebank.core.util.LoggedUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "${base_url}/payments")
@RequiredArgsConstructor
@Slf4j
@Validated
public class PaymentController {
    private final PaymentService paymentService;
    private final LoggedUser loggedUser;

    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllUserPayments(@RequestParam int page,@RequestParam int size) {
        List<PaymentDto> payments = paymentService.getAllUserPayments(page, size);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable UUID id) {
        Optional<Payment> optionalPayment = paymentService.getPaymentById(id);
        return optionalPayment.map(ResponseEntity::ok)
                              .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestBody @Valid PaymentDto payment) throws RuntimeException {
        PaymentDto createdPayment = paymentService.createPayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable UUID id, @RequestBody Payment payment) {
        Payment updatedPayment = paymentService.updatePayment(id, payment);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable UUID id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
