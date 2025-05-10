package com.app_wallet.virtual_wallet.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ScheduledTransactionDTO {
    private Long id;
    private BigDecimal amount;
    private LocalDate date;

    public ScheduledTransactionDTO() {}

    public ScheduledTransactionDTO(Long id, BigDecimal amount, LocalDate date) {
        this.id = id;
        this.amount = amount;
        this.date = date;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
}
