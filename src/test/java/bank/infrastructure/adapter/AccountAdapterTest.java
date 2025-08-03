package bank.infrastructure.adapter;

import bank.domain.model.Account;
import bank.domain.port.AccountPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ActiveProfiles("test")
class AccountAdapterTest {

    @Autowired
    AccountAdapter accountAdapter;

    @Autowired
    AccountPort accountPort;

    @Test
    void should_save_and_get_account() {
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, BigDecimal.ZERO, List.of());
        accountPort.save(account);

        Optional<Account> result = accountPort.findById(accountId);

        assertTrue(result.isPresent());
        assertEquals(accountId, result.get().getId());
        assertEquals(BigDecimal.ZERO, result.get().getBalance());
    }
}
