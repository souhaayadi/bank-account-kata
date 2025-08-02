package bank.domain.useCase;

import com.example.bank.domain.exceptions.AccountNotFoundException;
import com.example.bank.domain.model.Account;
import com.example.bank.domain.port.AccountPort;
import com.example.bank.domain.useCase.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.nio.channels.AcceptPendingException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private AccountPort repository;
    private AccountService service;
    private UUID accountId;

    @BeforeEach
    void setUp() {
        repository = mock(AccountPort.class);
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
    @Test
    void should_throw_exception_when_depositing_on_none_existing_account() {
        // Given
        when(repository.findById(accountId)).thenReturn(Optional.empty());

        //when //then
        assertThrows(AccountNotFoundException.class, () ->
                service.deposit(accountId, BigDecimal.valueOf(100)));

        verify(repository, never()).save(any());

    }
}