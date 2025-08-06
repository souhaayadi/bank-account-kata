package bank.application.controller;

import bank.application.controller.dto.AmountRequest;
import bank.application.controller.dto.CreateAccountRequest;
import bank.domain.model.Account;
import bank.domain.useCase.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService service) {
        this.accountService = service;
    }

    @PostMapping
    @Operation(summary = "création d'un compte bancaire")
    public ResponseEntity<UUID> createAccount(@RequestBody CreateAccountRequest request) {
        UUID accountId = accountService.createAccountWithUser(request.email(), request.password());
        return ResponseEntity.created(URI.create("/accounts/" + accountId)).body(accountId);
    }

    @GetMapping("/{id}/statement")
    @Operation(summary = "récupérer un compte bancaire avec ses transactions")
    public ResponseEntity<Account> getStatement(@PathVariable UUID id) {
        return ResponseEntity.ok(accountService.getAccountWithDetails(id));
    }

    @PostMapping("/{id}/deposit")
    @Operation(summary = "ajouter de l'argent dans un compte bancaire")
    public ResponseEntity<Void> deposit(@PathVariable UUID id, @RequestBody AmountRequest request) {
        accountService.deposit(id, request.amount());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/withdraw")
    @Operation(summary = "retrait de l'argent d'un compte bancaire")
    public ResponseEntity<Void> withdraw(@PathVariable UUID id, @RequestBody AmountRequest request) {
        accountService.withdraw(id, request.amount());
        return ResponseEntity.ok().build();
    }

}
