package bank.infrastructure.adapter;

import bank.domain.model.Account;
import bank.domain.port.AccountPort;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class AccountAdapter implements AccountPort {


    @Override
    public Optional<Account> findById(UUID accountId) {
        return Optional.empty();
    }

    @Override
    public void save(Account account) {

    }
}
