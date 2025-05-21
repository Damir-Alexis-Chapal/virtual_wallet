package com.app_wallet.virtual_wallet.entity;

import com.app_wallet.virtual_wallet.model.Category;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "scheduled_transactions")
public class ScheduledTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String type; // DEPOSITO, RETIRO, TRANSFERENCIA

    @Column(name = "Origin")
    private Long accountOrigin; //CUENTA BANCARIA DESDE DONDE SE ENVIA, SE TOMA EL ID

    @Column(name = "destiny")
    private String userDestiny;

    private String description;

    @Column(name = "user_id")
    private Long userId;//PARA IDENTIFICAR LAS TRANSACCIONES CON EL USUARIO QUE LAS ENVIA

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(name = "scheduled_datetime")
    private LocalDateTime scheduledDatetime;

    private boolean executed = false;

    public ScheduledTransactionEntity() {}

    public ScheduledTransactionEntity(Long id, BigDecimal amount, String type, Long accountOrigin, String userDestiny, String description, Long userId,  Category category, LocalDateTime scheduledDatetime ) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.accountOrigin = accountOrigin;
        this.userDestiny = userDestiny;
        this.description = description;
        this.userId = userId;
        this.category = category;
        this.scheduledDatetime = scheduledDatetime;

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

    public Long getAccountOrigin() {
        return accountOrigin;
    }

    public void setAccountOrigin(Long accountOrigin) {
        this.accountOrigin = accountOrigin;
    }

    public String getUserDestiny() {
        return userDestiny;
    }

    public void setUserDestiny(String userDestiny) {
        this.userDestiny = userDestiny;
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

    public Category getCategory() { return category; }

    public void setCategory(Category category) { this.category = category; }

    public LocalDateTime getScheduledDatetime() { return scheduledDatetime; }
    public void setScheduledDatetime(LocalDateTime scheduledDatetime) {
        this.scheduledDatetime = scheduledDatetime;
    }
    public boolean isExecuted() { return executed; }
    public void setExecuted(boolean executed) { this.executed = executed; }


}
