package com.app_wallet.virtual_wallet.entity;

import java.math.BigDecimal;

public class Purse {
    private String id;
    private String name;
    private String description;
    BigDecimal amount;

    public Purse() {
    }

    public Purse(String id, String name, String description, BigDecimal amount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
