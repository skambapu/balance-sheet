package com.bian.statement.client;

import com.bian.statement.constants.TransactionType;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionDTO {

    private String id;
    private String accountNumber;
    private Date transactionTs;
    private BigDecimal amount;
    private TransactionType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Date getTransactionTs() {
        return transactionTs;
    }

    public void setTransactionTs(Date transactionTs) {
        this.transactionTs = transactionTs;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
