package com.finologee.apifinologeebank.core.service;

import com.finologee.apifinologeebank.core.model.Balance;
import com.finologee.apifinologeebank.core.repository.BalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@Slf4j
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService{
    private final BalanceRepository balanceRepository;

    @Override
    public Balance createEmptyBalance(Balance balance) {
        return balanceRepository.save(balance);
    }

    @Override
    public Balance updateBalance(Balance balance) {
        return balanceRepository.save(balance);
    }
}
