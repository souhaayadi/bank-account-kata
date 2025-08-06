package bank.infrastructure.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ACCOUNT_TABLE")
public class AccountEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private BigDecimal balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TransactionEntity> transactions = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
    private UserEntity user;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<TransactionEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionEntity> transactions) {
        this.transactions = transactions;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

}
