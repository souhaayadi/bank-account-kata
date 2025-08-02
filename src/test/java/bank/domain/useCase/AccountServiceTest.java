package bank.domain.useCase;

import com.example.bank.domain.exceptions.AccountNotFoundException;
import com.example.bank.domain.model.Account;
import com.example.bank.domain.port.AccountPort;
import com.example.bank.domain.useCase.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
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

    @Nested
    class depositMoney {
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

    @Nested
    class withdrawMoney {
        @Test
        void should_withdraw_money_and_update_balance() {
            // Given
            Account account = new Account(accountId, BigDecimal.valueOf(200), new ArrayList<>());
            when(repository.findById(accountId)).thenReturn(Optional.of(account));

            // When
            service.withdraw(accountId, BigDecimal.valueOf(150));

            // Then
            assertEquals(BigDecimal.valueOf(50), account.getBalance());
            assertEquals(1, account.getTransactions().size());
            verify(repository).save(account);
        }

        @Test
        void should_throw_exception_when_withdrawing_more_than_balance() {
            // Given
            Account account = new Account(accountId, BigDecimal.valueOf(100), new ArrayList<>());
            when(repository.findById(accountId)).thenReturn(Optional.of(account));

            // When / Then
            assertThrows(IllegalArgumentException.class, () ->
                    service.withdraw(accountId, BigDecimal.valueOf(150))
            );

            verify(repository, never()).save(any());
        }


        @Test
        void should_throw_exception_when_withdrawing_on_nonexistent_account() {
            // Given
            when(repository.findById(accountId)).thenReturn(Optional.empty());

            // When / Then
            assertThrows(AccountNotFoundException.class, () ->
                    service.withdraw(accountId, BigDecimal.valueOf(100))
            );

            verify(repository, never()).save(any());
        }
    }
}