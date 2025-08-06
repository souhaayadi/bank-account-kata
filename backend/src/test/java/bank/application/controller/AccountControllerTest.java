package bank.application.controller;

import bank.application.controller.dto.AmountRequest;
import bank.application.controller.dto.CreateAccountRequest;
import bank.domain.model.Account;
import bank.domain.model.Transaction;
import bank.domain.model.TransactionType;
import bank.domain.useCase.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private AccountService accountService;

    @Test
    void should_return_account_statement_as_json() throws Exception {
        UUID accountId = UUID.randomUUID();
        Account account = new Account(accountId, BigDecimal.valueOf(120), List.of(
                new Transaction(BigDecimal.valueOf(100), LocalDateTime.now(), TransactionType.DEPOSIT),
                new Transaction(BigDecimal.valueOf(20), LocalDateTime.now(), TransactionType.DEPOSIT)
        ));

        when(accountService.getAccountWithDetails(accountId)).thenReturn(account);

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
        AmountRequest request = new AmountRequest(amount);

        mockMvc.perform(
                     post("/api/accounts/" + accountId + "/deposit")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());

        verify(accountService).deposit(accountId, amount);
    }

    @Test
    void should_accept_withdraw_request_and_return_ok() throws Exception {
        UUID accountId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.valueOf(75);
        AmountRequest request = new AmountRequest(amount);

        mockMvc.perform(
                        post("/api/accounts/" + accountId + "/withdraw")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk());

        verify(accountService).withdraw(accountId, amount);
    }

    @Test
    void should_create_new_account_and_return_201_with_location() throws Exception {
        UUID newAccountId = UUID.randomUUID();
        CreateAccountRequest request = new CreateAccountRequest("ayadi@gmail.com", "123");

        when(accountService.createAccountWithUser("ayadi@gmail.com", "123")).thenReturn(newAccountId);

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/accounts/" + newAccountId));
    }
}
