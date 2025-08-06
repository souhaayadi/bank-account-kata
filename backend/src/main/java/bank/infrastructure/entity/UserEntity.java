package bank.infrastructure.entity;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "USER_TABLE")
public class UserEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne
    @JoinColumn(name = "user_account_id", nullable = false,
    referencedColumnName = "id",
    foreignKey = @ForeignKey(name ="fk_user_account"))
    private AccountEntity account;

    public UserEntity() {}

    public UserEntity(UUID id, String email, String password, AccountEntity account) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.account = account;
    }

    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public AccountEntity getAccount() { return account; }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

}
