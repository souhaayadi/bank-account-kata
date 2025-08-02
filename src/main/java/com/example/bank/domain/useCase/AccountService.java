package com.example.bank.domain.useCase;

import com.example.bank.domain.model.Account;
import com.example.bank.domain.port.AccountPort;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountService {

    private final AccountPort repository;

    public AccountService(AccountPort repository) {
        this.repository = repository;
    }

    public void deposit(UUID accountId, BigDecimal amount) {
        Account account = repository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        account.deposit(amount);
        repository.save(account);
    }
}