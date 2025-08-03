package bank.infrastructure.adapter;

import bank.domain.model.Account;
import bank.domain.port.AccountPort;
import bank.infrastructure.entiy.AccountEntity;
import bank.infrastructure.mapper.AccountMapper;
import bank.infrastructure.repository.AccountRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class AccountAdapter implements AccountPort {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountAdapter(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public Optional<Account> findById(UUID accountId) {
        return accountRepository.findById(accountId)
                .map(accountMapper::toDomain);
    }

    @Override
    public void save(Account account) {
        AccountEntity entity = accountMapper.toEntity(account);
        accountRepository.save(entity);
    }
}
