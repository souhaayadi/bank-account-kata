package bank.application.controller;

import bank.domain.model.Account;
import bank.domain.useCase.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/{id}/statement")
    public ResponseEntity<Account> getStatement(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getStatement(id));
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable UUID id, @RequestBody BigDecimal amount) {
        service.deposit(id, amount);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable UUID id, @RequestBody BigDecimal amount) {
        service.withdraw(id, amount);
        return ResponseEntity.ok().build();
    }
}