package com.finologee.apifinologeebank.core.service;

import com.finologee.apifinologeebank.core.model.Balance;

import java.util.UUID;

public interface BalanceService {

    Balance createEmptyBalance(Balance balance);
    Balance updateBalance(Balance balance);

}
