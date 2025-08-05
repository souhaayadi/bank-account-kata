package bank.domain.useCase;

import bank.domain.exceptions.AccountNotFoundException;
import bank.domain.exceptions.InvalidAmountException;
import bank.domain.model.Account;
import bank.domain.port.AccountPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static bank.domain.exceptions.ExceptionMessages.ACCOUNT_NOT_FOUND;
import static bank.domain.exceptions.ExceptionMessages.INVALID_AMOUNT;

@Service
public class AccountService {

    private final AccountPort repository;

    public AccountService(AccountPort repository) {
        this.repository = repository;
    }

    public UUID createAccount() {
        UUID id = UUID.randomUUID();
        Account newAccount = new Account(id, BigDecimal.ZERO, new ArrayList<>());
        repository.save(newAccount);
        return id;
    }

    public void deposit(UUID accountId, BigDecimal amount) {
        Account account = getAccountWithDetails(accountId);
        account.deposit(amount);
        repository.save(account);
    }

    public void withdraw(UUID accountId, BigDecimal amount) {
        Account account = getAccountWithDetails(accountId);
        account.withdraw(amount);
        repository.save(account);
    }

    public Account getAccountWithDetails(UUID accountId) {
        return repository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND + ' ' + accountId));
    }

}
