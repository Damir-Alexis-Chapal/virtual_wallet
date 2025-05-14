package com.app_wallet.virtual_wallet.entity;

import com.app_wallet.virtual_wallet.utils.LinkedList;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account_entity")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(name = "user_id")
    private Long userId;


    public AccountEntity() {
    }

    public AccountEntity(Long id, BigDecimal balance, Long userId) {
        this.id = id;
        this.balance = balance;
        this.userId = userId;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
