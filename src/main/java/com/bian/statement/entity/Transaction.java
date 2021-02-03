package com.bian.statement.entity;

import com.bian.statement.constants.TransactionType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String globalId;
    private String accountNumber;
    private Date transactionTs;
    private BigDecimal amount;
    private TransactionType type;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
