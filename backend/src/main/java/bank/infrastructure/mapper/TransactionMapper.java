package bank.infrastructure.mapper;

import bank.domain.model.Transaction;
import bank.infrastructure.entity.TransactionEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionEntity toEntity(Transaction transaction);

    Transaction toDomain(TransactionEntity entity);

    List<TransactionEntity> toEntityList(List<Transaction> transactions);

    List<Transaction> toDomainList(List<TransactionEntity> entities);
}
