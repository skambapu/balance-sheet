package com.bian.statement.mapper;

import com.bian.statement.client.BalanceDTO;
import com.bian.statement.client.TransactionDTO;
import com.bian.statement.entity.Balance;
import com.bian.statement.entity.Transaction;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;

@Component
public class BalanceMapper extends ConfigurableMapper {

    protected void configure(MapperFactory factory) {
        factory.registerClassMap(
                factory.classMap(Balance.class, BalanceDTO.class)
                        .mapNulls(false).mapNullsInReverse(false)
                        .byDefault()
                        .toClassMap()
        );
    }
}
