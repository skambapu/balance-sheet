package com.bian.statement.demo;

import com.bian.statement.client.CollectionResource;
import com.bian.statement.client.TransactionDTO;
import com.bian.statement.constants.TransactionType;
import com.bian.statement.service.TransactionService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    TransactionService transactionService;

    private static final String PATTERN_ZULU_LONG_MS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @BeforeEach
    public void setUp() {
        transactionService.deleteAllTransactions();

    }

    @AfterEach
    public void cleanUp() {
        transactionService.deleteAllTransactions();
    }

    @Test
    public void testFindTransactions() {
        TransactionDTO transactionDTO = buildTransactionDTO("abc", 89.1d, TransactionType.DEPOSIT, new Date());
        transactionService.createTransaction(transactionDTO);
        Map<String, Object> parameters =  new HashMap<>();
        parameters.put("accountNumber", "abc");
        parameters.put("type", TransactionType.DEPOSIT);
        CollectionResource<TransactionDTO> transactionDTOCollectionResource = transactionService.findTransactions(parameters);
        Assert.assertEquals(1, transactionDTOCollectionResource.getTotal());
    }

    @Test
    public void testFindTransactionsByDateRange() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(PATTERN_ZULU_LONG_MS);

        String transactionTs1 = "2020-01-01T00:00:30.000Z";
        TransactionDTO transactionDTO1 = buildTransactionDTO("abc", 89.1d, TransactionType.DEPOSIT, dateFormat.parse(transactionTs1));
        transactionService.createTransaction(transactionDTO1);

        String transactionTs2 = "2021-02-03T00:00:30.000Z";
        TransactionDTO transactionDTO2 = buildTransactionDTO("abc", 89.1d, TransactionType.DEPOSIT, dateFormat.parse(transactionTs2));
        transactionService.createTransaction(transactionDTO2);


        Map<String, Object> parameters =  new HashMap<>();
        parameters.put("accountNumber", "abc");
        parameters.put("type", TransactionType.DEPOSIT);

        parameters.put("startDate", dateFormat.parse("2021-01-01T00:00:30.000Z"));
        parameters.put("endDate", dateFormat.parse("2021-03-03T00:00:30.000Z"));
        CollectionResource<TransactionDTO> transactionDTOCollectionResource = transactionService.findTransactions(parameters);
        Assert.assertEquals(1, transactionDTOCollectionResource.getTotal());
    }

    @Test
    public void testFindTransactionsByDateRangeWithWrongAccountNumber() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(PATTERN_ZULU_LONG_MS);

        String transactionTs1 = "2020-01-01T00:00:30.000Z";
        TransactionDTO transactionDTO1 = buildTransactionDTO("abc", 89.1d, TransactionType.DEPOSIT, dateFormat.parse(transactionTs1));
        transactionService.createTransaction(transactionDTO1);

        String transactionTs2 = "2021-02-03T00:00:30.000Z";
        TransactionDTO transactionDTO2 = buildTransactionDTO("abc", 89.1d, TransactionType.DEPOSIT, dateFormat.parse(transactionTs2));
        transactionService.createTransaction(transactionDTO2);


        Map<String, Object> parameters =  new HashMap<>();
        parameters.put("accountNumber", "xyz");
        parameters.put("type", TransactionType.DEPOSIT);

        parameters.put("startDate", dateFormat.parse("2021-01-01T00:00:30.000Z"));
        parameters.put("endDate", dateFormat.parse("2021-03-03T00:00:30.000Z"));
        CollectionResource<TransactionDTO> transactionDTOCollectionResource = transactionService.findTransactions(parameters);
        Assert.assertEquals(0, transactionDTOCollectionResource.getTotal());
    }

    @Test
    public void testFindTransactionsByType() {
        TransactionDTO transactionDTO = buildTransactionDTO("abc", 89.1d, TransactionType.DEPOSIT, new Date());
        transactionService.createTransaction(transactionDTO);
        Map<String, Object> parameters =  new HashMap<>();
        parameters.put("accountNumber", "abc");
        parameters.put("type", TransactionType.WITHDRAW);
        CollectionResource<TransactionDTO> transactionDTOCollectionResource = transactionService.findTransactions(parameters);
        Assert.assertEquals(0, transactionDTOCollectionResource.getTotal());
    }

    @Test
    public void testFindTransactionsByAccountNumber() {
        TransactionDTO transactionDTO = buildTransactionDTO("abc", 89.1d, TransactionType.DEPOSIT, new Date());
        transactionService.createTransaction(transactionDTO);
        Map<String, Object> parameters =  new HashMap<>();
        parameters.put("accountNumber", "TestAccount");
        parameters.put("type", TransactionType.DEPOSIT);
        CollectionResource<TransactionDTO> transactionDTOCollectionResource = transactionService.findTransactions(parameters);
        Assert.assertEquals(0, transactionDTOCollectionResource.getTotal());
    }

    private TransactionDTO buildTransactionDTO(String accountNumber, Double amount, TransactionType type, Date transactionTs) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setAccountNumber(accountNumber);
        transactionDTO.setAmount(BigDecimal.valueOf(amount));
        transactionDTO.setTransactionTs(transactionTs);
        transactionDTO.setType(type);
        return transactionDTO;
    }
}
