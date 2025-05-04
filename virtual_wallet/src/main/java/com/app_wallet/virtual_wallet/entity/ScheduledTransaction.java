package com.app_wallet.virtual_wallet.entity;

import java.time.LocalDate;

public class ScheduledTransaction {
    private Long id;
    private Transaction transaction;
    private LocalDate date;

    public ScheduledTransaction() {
    }

    public ScheduledTransaction(Long id, Transaction transaction, LocalDate date) {
        this.id = id;
        this.transaction = transaction;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
