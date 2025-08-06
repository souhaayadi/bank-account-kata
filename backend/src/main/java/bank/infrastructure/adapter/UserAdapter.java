package bank.infrastructure.adapter;

import bank.domain.model.User;
import bank.domain.port.UserPort;
import bank.infrastructure.mapper.UserMapper;
import bank.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter implements UserPort {

    private final UserRepository repository;
    private final UserMapper mapper;


    public UserAdapter(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return mapper.toDomain(repository.save(mapper.toEntity(user)));
    }
}
