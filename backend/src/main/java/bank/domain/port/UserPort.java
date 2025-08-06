package bank.domain.port;

import bank.domain.model.User;

public interface UserPort {
    boolean existsByEmail(String email);
    User save(User user);
}
