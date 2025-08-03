package bank.application.controller;

import bank.domain.model.Account;
import bank.domain.model.Transaction;
import bank.domain.useCase.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
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

    @Test
    void should_accept_deposit_request_and_return_ok() throws Exception {
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(150);

        mockMvc.perform(
                     post("/api/accounts/" + accountId + "/deposit")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(amount.toString())
                )
                .andExpect(status().isOk());

        verify(accountService).deposit(accountId, amount);
    }

    @Test
    void should_accept_withdraw_request_and_return_ok() throws Exception {
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(75);

        mockMvc.perform(
                        post("/api/accounts/" + accountId + "/withdraw")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(amount.toString())
                )
                .andExpect(status().isOk());

        verify(accountService).withdraw(accountId, amount);
    }

    @Test
    void should_create_new_account_and_return_201_with_location() throws Exception {
        UUID newAccountId = UUID.randomUUID();

        when(accountService.createAccount()).thenReturn(newAccountId);

        mockMvc.perform(post("/api/accounts"))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/accounts/" + newAccountId));
    }
}