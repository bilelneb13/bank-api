package com.finologee.apifinologeebank.core.controller;


import com.finologee.apifinologeebank.core.dto.PaymentDto;
import com.finologee.apifinologeebank.core.model.Payment;
import com.finologee.apifinologeebank.core.service.PaymentService;
import com.finologee.apifinologeebank.core.util.LoggedUser;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @Operation(summary = "Get all payments of the authenticated user", description = "Retrieves a list of all payments of the authenticated user using Pagination")
    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllUserPayments(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "5") int size) {
        List<PaymentDto> payments = paymentService.getAllUserPayments(page, size);
        return ResponseEntity.ok(payments);
    }
    @Operation(summary = "Get all payments related to beneficiary account", description = "Retrieves a list of all payments of the authenticated related to beneficiary account and creation date using Pagination")
    @GetMapping("/beneficiary/{accountNumber}")
    public ResponseEntity<List<PaymentDto>> getAllUserPaymentsByCriteria(@PathVariable String accountNumber,
                                                                         @RequestParam(name = "start_date")  String startDate,
                                                                         @RequestParam(name = "end_date") String endDate,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "5") int size) {
        List<PaymentDto> payments = paymentService.getAllUserPaymentsByBeneficiaryAndPeriod(accountNumber, startDate, endDate, page, size);
        return ResponseEntity.ok(payments);
    }

/*    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable UUID id) {
        Optional<Payment> optionalPayment = paymentService.getPaymentById(id);
        return optionalPayment.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }*/

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestBody @Valid PaymentDto payment) throws RuntimeException {
        PaymentDto createdPayment = paymentService.createPayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
    }

/*    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable UUID id, @RequestBody Payment payment) {
        Payment updatedPayment = paymentService.updatePayment(id, payment);
        return ResponseEntity.ok(updatedPayment);
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePayment(@PathVariable UUID id) {
        return paymentService.deletePayment(id);
    }
}
