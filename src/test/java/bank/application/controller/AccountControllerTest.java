package bank.application.controller;

import com.example.bank.domain.model.Account;
import com.example.bank.domain.model.Transaction;
import com.example.bank.domain.useCase.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    void should_return_account_statement_as_json() throws Exception {
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, BigDecimal.valueOf(120), List.of(
                new Transaction(BigDecimal.valueOf(100)),
                new Transaction(BigDecimal.valueOf(20))
        ));

        when(accountService.getStatement(accountId)).thenReturn(account);

        mockMvc.perform(get("/api/accounts/" + accountId + "/statement"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(accountId.toString()))
                .andExpect(jsonPath("$.balance").value(120))
                .andExpect(jsonPath("$.transactions").isArray())
                .andExpect(jsonPath("$.transactions.length()").value(2));
    }
}