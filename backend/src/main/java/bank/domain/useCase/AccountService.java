package bank.domain.useCase;

import bank.domain.exceptions.AccountExistsException;
import bank.domain.exceptions.AccountNotFoundException;
import bank.domain.model.Account;
import bank.domain.model.User;
import bank.domain.port.AccountPort;
import bank.domain.port.UserPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static bank.domain.exceptions.ExceptionMessages.ACCOUNT_NOT_FOUND;
import static bank.domain.exceptions.ExceptionMessages.EXISTS_ACCOUNT;

@Service
public class AccountService {

    private final AccountPort accountPort;
    private final UserPort userPort;

    public AccountService(AccountPort accountPort, UserPort userPort) {
        this.accountPort = accountPort;
        this.userPort = userPort;
    }

    @Transactional
    public UUID createAccountWithUser(String email, String password) {
        if (userPort.existsByEmail(email)) {
            throw new AccountExistsException(EXISTS_ACCOUNT);
        }

        UUID id = UUID.randomUUID();
        Account account = new Account(id, BigDecimal.ZERO, new ArrayList<>());
        account = accountPort.save(account);

        UUID userId = UUID.randomUUID();
        User user = new User(userId, email, password, account);
        userPort.save(user);

        return id;
    }

    public void deposit(UUID accountId, BigDecimal amount) {
        Account account = getAccountWithDetails(accountId);
        account.deposit(amount);
        accountPort.save(account);
    }

    public void withdraw(UUID accountId, BigDecimal amount) {
        Account account = getAccountWithDetails(accountId);
        account.withdraw(amount);
        accountPort.save(account);
    }

    public Account getAccountWithDetails(UUID accountId) {
        return accountPort.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND + ' ' + accountId));
    }

}
