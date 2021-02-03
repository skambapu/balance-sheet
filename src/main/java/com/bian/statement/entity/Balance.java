package com.bian.statement.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;


@Entity
public class Balance {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String globalId;
    private String accountNumber;
    private Date lastUpdateTs;
    private BigDecimal balance;

    @PrePersist
    public void prePersist() {
        this.globalId = UUID.randomUUID().toString();
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
