package com.finologee.apifinologeebank.core.exception;

public class BlockedPaymentException extends RuntimeException {
    public BlockedPaymentException(String message) {
        super(message);
    }
}