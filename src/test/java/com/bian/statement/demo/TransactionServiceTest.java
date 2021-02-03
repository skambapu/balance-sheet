package com.bian.statement.demo;

import com.bian.statement.client.CollectionResource;
import com.bian.statement.client.TransactionDTO;
import com.bian.statement.constants.TransactionType;
import com.bian.statement.service.TransactionService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    TransactionService transactionService;

    @Test
    public void testFindTransactions() {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccountNumber("abc");
        transactionDTO.setAmount(BigDecimal.valueOf(89.1));
        LocalDateTime transactionTs = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
        transactionDTO.setTransactionTs(transactionTs.toString());
        transactionDTO.setType(TransactionType.DEPOSIT);
        transactionService.createTransaction(transactionDTO);
        Map<String, Object> parameters =  new HashMap<>();
        CollectionResource<TransactionDTO> transactionDTOCollectionResource = transactionService.findTransactions(parameters);
        Assert.assertEquals(1, transactionDTOCollectionResource.getTotal());
    }

}
