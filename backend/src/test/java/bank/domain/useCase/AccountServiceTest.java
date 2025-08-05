package bank.domain.useCase;

import bank.domain.exceptions.AccountNotFoundException;
import bank.domain.exceptions.InsufficientBalanceException;
import bank.domain.exceptions.InvalidAmountException;
import bank.domain.model.Account;
import bank.domain.port.AccountPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
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
            Account account = new Account(accountId, BigDecimal.ZERO, new ArrayList<>());
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
        @Test
        void should_throw_exception_when_depositing_a_not_valid_amount() {
            // Given
            Account account = new Account(accountId, BigDecimal.ZERO, new ArrayList<>());
            when(repository.findById(accountId)).thenReturn(Optional.of(account));

            //when //then
            assertThrows(InvalidAmountException.class, () ->
                    service.deposit(accountId, BigDecimal.valueOf(-2)));

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
            assertThrows(InsufficientBalanceException.class, () ->
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

        @Test
        void should_throw_exception_when_withdrawing_an_invalid_amount() {
            // Given
            Account account = new Account(accountId, BigDecimal.valueOf(100), new ArrayList<>());
            when(repository.findById(accountId)).thenReturn(Optional.of(account));

            // When / Then
            assertThrows(InvalidAmountException.class, () ->
                    service.withdraw(accountId, BigDecimal.valueOf(-3))
            );

            verify(repository, never()).save(any());
        }
    }

    @Nested
    class getStatement {
        @Test
        void should_return_account_statement_with_all_transactions() {
            // Given
            Account account = new Account(accountId, BigDecimal.valueOf(100), new ArrayList<>());
            account.deposit(BigDecimal.valueOf(50));
            account.withdraw(BigDecimal.valueOf(30));

            when(repository.findById(accountId)).thenReturn(Optional.of(account));

            // When
            Account result = service.getAccountWithDetails(accountId);

            // Then
            assertEquals(accountId, result.getId());
            assertEquals(BigDecimal.valueOf(120), result.getBalance());
            assertEquals(2, result.getTransactions().size());
        }

        @Test
        void should_throw_exception_when_getting_statement_of_nonexistent_account() {
            // Given
            when(repository.findById(accountId)).thenReturn(Optional.empty());

            // When / Then
            assertThrows(AccountNotFoundException.class, () ->
                    service.getAccountWithDetails(accountId)
            );

            verify(repository, never()).save(any());
        }
    }
    @Nested
    class createAccount {
        @Test
        void should_create_new_account_with_zero_balance_and_empty_transactions() {
            // When
            UUID accountId = service.createAccount();

            // Then
            verify(repository).save(argThat(account ->
                    account.getId().equals(accountId)
                            && account.getBalance().compareTo(BigDecimal.ZERO) == 0
                            && account.getTransactions().isEmpty()
            ));
        }
    }
}
