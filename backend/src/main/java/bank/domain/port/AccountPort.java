package bank.domain.port;

import bank.domain.model.Account;

import java.util.Optional;
import java.util.UUID;
public interface AccountPort {

    Optional<Account> findById(UUID accountId);
    Account save(Account account);
}
