package com.bian.statement.controller;

import com.bian.statement.client.CollectionResource;
import com.bian.statement.client.TransactionDTO;
import com.bian.statement.constants.TransactionType;
import com.bian.statement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController("/transactions")
public class TransactionStatementController extends BaseResource{

    @Autowired
    TransactionService transactionService;

    @GetMapping("/findTransactions")
    public Response findTransactions(@QueryParam("accountNumber") String acctNumber, @QueryParam("startDate") Date startDate, @QueryParam("endDate")Date endDate, @QueryParam("transactionType") TransactionType type) {
        Map<String, Object> params = new HashMap<>();
        params.put("accountNumber", acctNumber);
        params.put("startDate", startDate);
        params.put("endDate", endDate != null ? endDate : new Date());
        params.put("type", type);
        CollectionResource<TransactionDTO> transactionDTOCollectionResource = transactionService.findTransactions(params);
        return Response.ok(transactionDTOCollectionResource).build();
    }

    @DeleteMapping("/deleteAllTxn")
    public Response deleteAllTransactions() {
        boolean isDeleted = transactionService.deleteAllTransactions();
        if(isDeleted) {
            return Response.noContent().build();
        } else {
            return Response.serverError().build();
        }
    }
}
