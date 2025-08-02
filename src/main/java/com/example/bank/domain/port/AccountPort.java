package com.example.bank.domain.port;

import com.example.bank.domain.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface AccountPort {

    Optional<Account> findById(UUID accountId);
    void save(Account account);
}
