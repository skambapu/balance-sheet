package com.bian.statement.demo;

import com.bian.statement.client.BalanceDTO;
import com.bian.statement.client.CollectionResource;
import com.bian.statement.controller.BaseResource;
import com.bian.statement.service.BalanceService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class BalanceServiceTest {

    @Autowired
    BalanceService balanceService;

    private static final String PATTERN_ZULU_LONG_MS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @BeforeEach
    public void setUp() {
        balanceService.deleteAllBalances();

    }

    @AfterEach
    public void cleanUp() {
        balanceService.deleteAllBalances();
    }

    @Test
    public void testFindBalances() {
        BalanceDTO balanceDTO = buildBalanceDTO("abc", 89.1d, new Date());
        balanceService.createBalance(balanceDTO);
        CollectionResource<BalanceDTO> balanceDTOCollectionResource = balanceService.findBalances("abc", null);
        Assert.assertEquals(1, balanceDTOCollectionResource.getTotal());
    }

    @Test
    public void testFindLatestBalance() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(PATTERN_ZULU_LONG_MS);

        String lastUpdateTsStr1 = "2020-01-01T00:00:30.000Z";
        BalanceDTO balanceDTO1 = buildBalanceDTO("abc", 89.1d, dateFormat.parse(lastUpdateTsStr1));
        balanceDTO1 = balanceService.createBalance(balanceDTO1);

        String lastUpdateTsStr2 = "2021-01-01T00:00:30.000Z";
        BalanceDTO balanceDTO2 = buildBalanceDTO("abc", 89.1d, dateFormat.parse(lastUpdateTsStr2));
        balanceDTO2 = balanceService.createBalance(balanceDTO2);


        CollectionResource<BalanceDTO> balanceDTOCollectionResource = balanceService.findBalances("abc", BaseResource.parseSortParam("lastUpdateTs:DESC"));
        Assert.assertEquals(2, balanceDTOCollectionResource.getTotal());
        Assert.assertEquals(balanceDTO2.getId(), balanceDTOCollectionResource.getContent().get(0).getId());
    }

    private BalanceDTO buildBalanceDTO(String accountNumber, Double amount,  Date lastUpdateTs) {
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setAccountNumber(accountNumber);
        balanceDTO.setBalance(BigDecimal.valueOf(amount));
        balanceDTO.setLastUpdateTs(lastUpdateTs);
        return balanceDTO;
    }
}
