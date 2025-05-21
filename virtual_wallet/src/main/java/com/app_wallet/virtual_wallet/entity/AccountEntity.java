package com.app_wallet.virtual_wallet.entity;

import com.app_wallet.virtual_wallet.model.WalletType;
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

    @Column(name = "wallet_id", nullable = false)
    private Long walletId;

    @Column(nullable = false, unique=true)
    private Long accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "wallet_type")
    private WalletType type;

    public AccountEntity() {
    }

    public AccountEntity(Long id, BigDecimal balance, Long userId, Long walletId, Long accountNumber, WalletType type) {
        this.id = id;
        this.balance = balance;
        this.userId = userId;
        this.walletId = walletId;
        this.accountNumber = accountNumber;
        this.type = type;
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
    public Long getWalletId() { return walletId; }
    public void setWalletId(Long walletId) { this.walletId = walletId; }
    public Long getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }
    public WalletType getType() {
        return type;
    }
    public void setType(WalletType type) {
        this.type = type;
    }
}
