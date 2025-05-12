package com.app_wallet.virtual_wallet.entity;

import java.math.BigDecimal;

public class PurseEntity {
    private String id;
    private String name;
    private String type; // "Ahorros", "Gastos diarios", etc.
    private String description;
    private BigDecimal amount; // le agregué private

    public PurseEntity() {
    }

    public PurseEntity(String id, String name, String type, String description, BigDecimal amount) {
        this.id = id;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
