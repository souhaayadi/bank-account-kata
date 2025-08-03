package bank.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private final BigDecimal amount;
    private final LocalDateTime date;
    private TransactionType type;

    public Transaction(BigDecimal amount, LocalDateTime date, TransactionType type) {
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}