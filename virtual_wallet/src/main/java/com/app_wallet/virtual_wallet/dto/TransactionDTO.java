package com.app_wallet.virtual_wallet.dto;

import com.app_wallet.virtual_wallet.model.Category;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private BigDecimal amount;
    private String type;
    private LocalDateTime date;
    private String description;
    private Long origin;
    private String destination;
    private Category category;
    private String benefitTitle;


    public TransactionDTO() {}

    public TransactionDTO(Long id, BigDecimal amount, String type, LocalDateTime date, String description, Long origin, String destination, String benefitTitle) {
        this.id = id;
        this.amount = amount;
        this.type = type;
        this.date = date;
        this.description = description;
        this.origin = origin;
        this.destination = destination;
        this.benefitTitle = benefitTitle;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getOrigin() { return origin; }
    public void setOrigin(Long origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public String getBenefitTitle() {
        return benefitTitle;
    }

    public void setBenefitTitle(String benefitTitle) {
        this.benefitTitle = benefitTitle;
    }
}
