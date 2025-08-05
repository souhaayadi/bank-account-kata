package bank.domain.model;

import bank.domain.exceptions.InsufficientBalanceException;
import bank.domain.exceptions.InvalidAmountException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static bank.domain.exceptions.ExceptionMessages.INSUFFICIENT_FUNDS;
import static bank.domain.exceptions.ExceptionMessages.INVALID_AMOUNT;

public class Account {

    private final UUID id;
    private BigDecimal balance;
    private final List<Transaction> transactions;

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
        validateAmount(amount);
        balance = balance.add(amount);
        transactions.add(new Transaction(amount, LocalDateTime.now(), TransactionType.DEPOSIT));
    }

    public void withdraw(BigDecimal amount) {
        validateBalance(amount);
        validateAmount(amount);
        balance = balance.subtract(amount);
        transactions.add(new Transaction(amount.negate(), LocalDateTime.now(), TransactionType.WITHDRAW));
    }

    private void validateBalance(BigDecimal amount) {
        if (amount.compareTo(balance) > 0) {
            throw new InsufficientBalanceException(INSUFFICIENT_FUNDS);
        }
    }

    private static void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException(INVALID_AMOUNT);
        }
    }
}
