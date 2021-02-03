package com.bian.statement.controller;

import com.bian.statement.client.BalanceDTO;
import com.bian.statement.client.CollectionResource;
import com.bian.statement.service.BalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@RestController("/balances")
public class BalanceStatementController extends BaseResource{

    @Autowired
    BalanceService balanceService;

    @GetMapping("/findBalances")
    public Response findBalances(@QueryParam("accountNumber") String acctNumber, @QueryParam("sort") @DefaultValue("lastUpdateTs:DESC") String sort) {
        CollectionResource<BalanceDTO> balancesCollection = balanceService.findBalances(acctNumber, parseSortParam(sort));
        return Response.ok(balancesCollection).build();
    }


    @DeleteMapping("/deleteAllBalances")
    public Response deleteBalances() {
        boolean isDeleted = balanceService.deleteAllBalances();
        if(isDeleted) {
            return Response.noContent().build();
        } else {
            return Response.serverError().build();
        }
    }
}
