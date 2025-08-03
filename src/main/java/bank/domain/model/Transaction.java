package bank.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {

    private final BigDecimal amount;
    private final LocalDateTime date;

    public Transaction(BigDecimal amount) {
        this.amount = amount;
        this.date = LocalDateTime.now();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getDate() {
        return date;
    }
}