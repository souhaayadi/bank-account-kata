package bank.domain.useCase;

import com.example.bank.domain.model.Account;
import com.example.bank.domain.port.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private AccountRepository repository;
    private AccountService service;
    private UUID accountId;

    @BeforeEach
    void setUp() {
        repository = mock(AccountRepository.class);
        service = new AccountService(repository);
        accountId = UUID.randomUUID();
    }

    @Test
    void should_deposit_money_and_update_balance() {
       // Given
        Account account = new Account(accountId);
        when(repository.findById(accountId)).thenReturn(Optional.of(account));

        // When
        service.deposit(accountId, BigDecimal.valueOf(100));

        // Then
        assertEquals(BigDecimal.valueOf(100), account.getBalance());
        assertEquals(1, account.getTransactions().size());
        verify(repository).save(account);
    }
}