package bank.infrastructure.repository;

import bank.infrastructure.entity.AccountEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    @EntityGraph(attributePaths = "transactions")
    Optional<AccountEntity> findWithTransactionsById(UUID accountId);
}
