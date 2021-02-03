package com.bian.statement.mapper;

import com.bian.statement.client.TransactionDTO;
import com.bian.statement.entity.Transaction;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper extends ConfigurableMapper {

    protected void configure(MapperFactory factory) {
        factory.registerClassMap(
                factory.classMap(Transaction.class, TransactionDTO.class)
                        .mapNulls(false).mapNullsInReverse(false)
                        .byDefault()
                        .toClassMap()
        );
    }
}
