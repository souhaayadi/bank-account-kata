package bank.domain.port;

import bank.domain.model.Account;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
public interface AccountPort {

    Optional<Account> findById(UUID accountId);
    void save(Account account);
}
