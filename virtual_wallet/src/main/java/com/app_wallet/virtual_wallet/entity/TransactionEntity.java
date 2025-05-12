package com.app_wallet.virtual_wallet.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionEntity {

    private Long id;
    private BigDecimal amount;
    private String type; // DEPOSITO, RETIRO, TRANSFERENCIA
    private LocalDateTime date;
    private AccountEntity account;
    private String description;

    public TransactionEntity() {
    }
    public TransactionEntity(Long id, BigDecimal amount, String type, LocalDateTime date, AccountEntity account, String description) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.account = account;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

