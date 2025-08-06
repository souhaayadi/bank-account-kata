package bank.domain.model;

import java.util.UUID;

public record User(UUID id, String email, String password, Account account) {

}
