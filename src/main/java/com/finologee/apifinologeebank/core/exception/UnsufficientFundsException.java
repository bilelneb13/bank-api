package com.finologee.apifinologeebank.core.exception;

public class UnsufficientFundsException extends RuntimeException {
    public UnsufficientFundsException(String message) {
        super(message);
    }
}