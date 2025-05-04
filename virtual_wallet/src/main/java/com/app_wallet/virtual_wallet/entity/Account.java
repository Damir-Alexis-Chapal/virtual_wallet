package com.app_wallet.virtual_wallet.entity;

import com.app_wallet.virtual_wallet.utils.LinkedList;

import java.math.BigDecimal;

public class Account {
    private Long id;
    private BigDecimal balance;
    private User user;
    LinkedList<Account> accounts = new LinkedList<>();

    private LinkedList<Transaction> transactionList = new LinkedList<>();
    private LinkedList<Purse> purses = new LinkedList<>();

    public Account() {
    }
    public Account(Long id, BigDecimal balance, User user, LinkedList<Transaction> transactionList,  LinkedList<Purse> purses) {
        this.id = id;
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

    public LinkedList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(LinkedList<Account> accounts) {
        this.accounts = accounts;
    }
}