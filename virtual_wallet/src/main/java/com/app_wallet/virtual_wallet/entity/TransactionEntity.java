package com.app_wallet.virtual_wallet.entity;

import com.app_wallet.virtual_wallet.model.Category;
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
    private Long accountOrigin; //CUENTA BANCARIA DESDE DONDE SE ENVIA, SE TOMA EL ID

    @Column(name = "destiny")
    private String userDestiny; //

    private String description;

    @Column(name = "user_id")
    private Long userId;//PARA IDENTIFICAR LAS TRANSACCIONES CON EL USUARIO QUE LAS ENVIA

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(name = "benefit_title")
    private String benefitTitle;


    public TransactionEntity() {}

    public TransactionEntity(Long id, BigDecimal amount, String type, LocalDateTime date, Long accountOrigin,
                             String userDestiny, String description, Long userId ) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.accountOrigin = accountOrigin;
        this.userDestiny = userDestiny;
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

    public String getBenefitTitle() {
        return benefitTitle;
    }

    public void setBenefitTitle(String benefitTitle) {
        this.benefitTitle = benefitTitle;
    }
}
