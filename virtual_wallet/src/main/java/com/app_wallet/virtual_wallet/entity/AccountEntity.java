package com.app_wallet.virtual_wallet.entity;

import com.app_wallet.virtual_wallet.utils.LinkedList;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_id") // Esto conecta con `mappedBy = "user"` en UserEntity
    private UserEntity user;

    @Transient
    private LinkedList<TransactionEntity> transactionList = new LinkedList<>();

    @Transient
    private LinkedList<PurseEntity> purses = new LinkedList<>();

    public AccountEntity() {
    }

    public AccountEntity(Long id, BigDecimal balance, UserEntity user, LinkedList<TransactionEntity> transactionList, LinkedList<PurseEntity> purses) {
        this.id = id;
        this.balance = balance;
        this.user = user;
        this.transactionList = transactionList;
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

    public LinkedList<TransactionEntity> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(LinkedList<TransactionEntity> transactionList) {
        this.transactionList = transactionList;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public LinkedList<PurseEntity> getPurses() {
        return purses;
    }

    public void setPurses(LinkedList<PurseEntity> purses) {
        this.purses = purses;
    }
}
