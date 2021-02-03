package com.bian.statement.client;

import java.math.BigDecimal;
import java.util.Date;

public class BalanceDTO {
    private String id;
    private String accountNumber;
    private Date lastUpdateTs;
    private BigDecimal balance;

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

    public Date getLastUpdateTs() {
        return lastUpdateTs;
    }

    public void setLastUpdateTs(Date lastUpdateTs) {
        this.lastUpdateTs = lastUpdateTs;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
