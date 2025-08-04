package bank.application.controller;

import bank.application.controller.DTO.AmountRequest;
import bank.domain.model.Account;
import bank.domain.useCase.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;
import java.net.URI;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "création d'un compte bancaire")
    public ResponseEntity<Void> createAccount() {
        UUID id = service.createAccount();
        return ResponseEntity
                .created(URI.create("/api/accounts/" + id))
                .build();
    }

    @GetMapping("/{id}/statement")
    @Operation(summary = "récupérer un compte bancaire avec ses transactions")
    public ResponseEntity<Account> getStatement(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getStatement(id));
    }

    @PostMapping("/{id}/deposit")
    @Operation(summary = "ajouter de l'argent dans un compte bancaire")
    public ResponseEntity<Void> deposit(@PathVariable UUID id, @RequestBody AmountRequest request) {
        service.deposit(id, request.amount());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/withdraw")
    @Operation(summary = "retrait de l'argent d'un compte bancaire")
    public ResponseEntity<Void> withdraw(@PathVariable UUID id, @RequestBody AmountRequest request) {
        service.withdraw(id, request.amount());
        return ResponseEntity.ok().build();
    }

}
