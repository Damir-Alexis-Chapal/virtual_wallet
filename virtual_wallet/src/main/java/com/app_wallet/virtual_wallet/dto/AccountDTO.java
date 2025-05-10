package com.app_wallet.virtual_wallet.dto;

import java.math.BigDecimal;

public class AccountDTO {
    private Long id;
    private BigDecimal balance;

    public AccountDTO() {}

    public AccountDTO(Long id, BigDecimal balance) {
        this.id = id;
        this.balance = balance;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
}
