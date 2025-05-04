package com.app_wallet.virtual_wallet.entity;

import com.app_wallet.virtual_wallet.utils.LinkedList;

import java.math.BigDecimal;

public class Account {
    private Long id;
    private String type; // "Ahorros", "Gastos diarios", etc.
    private BigDecimal balance;
    private User user;

    private LinkedList<Transaction> transactionList = new LinkedList<>();
    private LinkedList<Purse> purses = new LinkedList<>();

    public Account() {
    }
    public Account(Long id, String type, BigDecimal balance, User user, LinkedList<Transaction> transactionList,  LinkedList<Purse> purses) {
        this.id = id;
        this.type = type;
        this.balance = balance;
        this.user = user;
        this.transactionList = transactionList;
        this.purses = purses;

    }

    public LinkedList<Purse> getPurses() {
        return purses;
    }

    public void setPurses(LinkedList<Purse> purses) {
        this.purses = purses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LinkedList<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(LinkedList<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}