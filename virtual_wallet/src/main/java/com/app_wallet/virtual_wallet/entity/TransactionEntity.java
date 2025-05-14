package com.app_wallet.virtual_wallet.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_entity")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String type; // DEPOSITO, RETIRO, TRANSFERENCIA

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name = "Origin")
    private Long accountOrigin;

    @Column(name = "destiny")
    private Long accountDestiny;

    private String description;

    @Column(name = "user_id")
    private Long userId;

    public TransactionEntity() {}

    public TransactionEntity(Long id, BigDecimal amount, String type, LocalDateTime date, Long accountOrigin, Long accountDestiny, String description, Long userId ) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.accountOrigin = accountOrigin;
        this.accountDestiny = accountDestiny;
        this.description = description;
        this.userId = userId;

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

    public void setAmount(BigDecimal amount) {
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

    public Long getAccountOrigin() {
        return accountOrigin;
    }

    public void setAccountOrigin(Long accountOrigin) {
        this.accountOrigin = accountOrigin;
    }

    public Long getAccountDestiny() {
        return accountDestiny;
    }

    public void setAccountDestiny(Long accountDestiny) {
        this.accountDestiny = accountDestiny;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
