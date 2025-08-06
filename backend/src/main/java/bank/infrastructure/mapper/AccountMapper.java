package bank.infrastructure.mapper;

import bank.domain.model.Account;
import bank.infrastructure.entity.AccountEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {TransactionMapper.class})
public interface AccountMapper {

    @Mapping(target = "user", ignore = true)
    AccountEntity toEntity(Account account);

    @InheritInverseConfiguration
    Account toDomain(AccountEntity entity);
}
