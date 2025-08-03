package bank.infrastructure.mapper;

import bank.domain.model.Account;
import bank.infrastructure.entiy.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountEntity toEntity(Account account);

    Account toDomain(AccountEntity entity);
}