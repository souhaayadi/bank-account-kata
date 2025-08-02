package com.example.bank.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
public class Account {

    private final UUID id;
    private BigDecimal balance;
    private final List<Transaction> transactions;

    public Account(UUID id) {
        this.id = id;
        this.balance = BigDecimal.ZERO;
        this.transactions = new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void deposit(BigDecimal amount) {
        balance = balance.add(amount);
        transactions.add(new Transaction(amount));
    }
}