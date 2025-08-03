package bank.domain.useCase;

import bank.domain.exceptions.AccountNotFoundException;
import bank.domain.model.Account;
import bank.domain.port.AccountPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

import static bank.domain.exceptions.ExceptionMessages.ACCOUNT_NOT_FOUND;

@Service
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
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND + accountId));
        account.withdraw(amount);
        repository.save(account);
    }

    public Account getStatement(UUID accountId) {
        return repository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException(ACCOUNT_NOT_FOUND + accountId));
    }

}