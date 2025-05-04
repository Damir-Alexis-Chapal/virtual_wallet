package com.app_wallet.virtual_wallet.entity;

import java.time.LocalDate;

public class ScheduledTransaction {
    private Transaction transaction;
    private LocalDate date;

    public ScheduledTransaction() {
    }
    public ScheduledTransaction(Transaction transaction, LocalDate date) {
        this.transaction = transaction;
        this.date = date;
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
