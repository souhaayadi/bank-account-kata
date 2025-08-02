package com.example.bank.domain.useCase;

import com.example.bank.domain.exceptions.AccountNotFoundException;
import com.example.bank.domain.model.Account;
import com.example.bank.domain.port.AccountPort;

import java.math.BigDecimal;
import java.util.UUID;

import static com.example.bank.domain.exceptions.ExceptionMessages.ACCOUNT_NOT_FOUND;

public class AccountService {

    private final AccountPort repository;

    public AccountService(AccountPort repository) {
        this.repository = repository;
    }

    public void deposit(UUID accountId, BigDecimal amount) {
        Account account = repository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND + accountId));
        account.deposit(amount);
        repository.save(account);
    }

    public void withdraw(UUID accountId, BigDecimal amount) {
        Account account = repository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountId));
        account.withdraw(amount);
        repository.save(account);
    }
}