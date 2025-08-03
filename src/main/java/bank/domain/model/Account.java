package bank.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static bank.domain.exceptions.ExceptionMessages.INSUFFICIENT_FUNDS;

public class Account {

    private final UUID id;
    private BigDecimal balance;
    private final List<Transaction> transactions;

    public Account(UUID id) {
        this.id = id;
        this.balance = BigDecimal.ZERO;
        this.transactions = new ArrayList<>();
    }

    public Account(UUID id, BigDecimal balance, List<Transaction> transactions) {
        this.id = id;
        this.balance = balance;
        this.transactions = transactions;
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

    public void withdraw(BigDecimal amount) {
        if (amount.compareTo(balance) > 0) {
            throw new IllegalArgumentException(INSUFFICIENT_FUNDS);
        }
        balance = balance.subtract(amount);
        transactions.add(new Transaction(amount.negate()));
    }
}