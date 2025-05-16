package com.app_wallet.virtual_wallet.dto;

import java.math.BigDecimal;

public class AccountDTO {
    private Long id;
    private BigDecimal balance;
    private Long userId;
    private Long accountNumber;

    public AccountDTO() {}

    public AccountDTO(Long id, BigDecimal balance, Long userId, Long accountNumber) {
        this.id = id;
        this.balance = balance;
        this.userId = userId;
        this.accountNumber = accountNumber;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getAccountNumber() { return accountNumber; }
    public void setAccountNumber(Long accountNumber) { this.accountNumber = accountNumber; }
}
