package bank.infrastructure.mapper;

import bank.domain.model.Account;
import bank.domain.model.User;
import bank.infrastructure.entity.AccountEntity;
import bank.infrastructure.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "account", source = "account")
    UserEntity toEntity(User user);

    @InheritInverseConfiguration
    User toDomain(UserEntity entity);

    default AccountEntity map(Account account) {
        if (account == null || account.getId() == null) return null;
        AccountEntity ref = new AccountEntity();
        ref.setId(account.getId());
        return ref;
    }
}
