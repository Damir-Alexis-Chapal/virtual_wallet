package com.app_wallet.virtual_wallet.entity;

import java.time.LocalDate;

public class ScheduledTransactionEntity {
    private Long id;
    private TransactionEntity transaction;
    private LocalDate date;

    public ScheduledTransactionEntity() {
    }

    public ScheduledTransactionEntity(Long id, TransactionEntity transaction, LocalDate date) {
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

    public TransactionEntity getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionEntity transaction) {
        this.transaction = transaction;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
