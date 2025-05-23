// src/main/java/com/app_wallet/virtual_wallet/entity/WalletEntity.java
package com.app_wallet.virtual_wallet.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name="wallets")
public class WalletEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="user_id", nullable=false)
  private Long userId;

  @Column(nullable=false)
  private String name;

  @Column(nullable=true)
  private BigDecimal amount;

  // getters y setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }
  public Long getUserId() { return userId; }
  public void setUserId(Long userId) { this.userId = userId; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public BigDecimal getAmount() { return amount; }
  public void setAmount(BigDecimal amount) { this.amount = amount; }
}
