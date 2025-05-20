package com.app_wallet.virtual_wallet.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "wallet_connection")
public class WalletConnectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Para qué usuario pertenece esta conexión
    @Column(name = "user_id", nullable = false)
    private Long userId;

    // El monedero origen
    @Column(name = "source_wallet_id", nullable = false)
    private Long sourceWalletId;

    // El monedero destino
    @Column(name = "target_wallet_id", nullable = false)
    private Long targetWalletId;

    // getters / setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getSourceWalletId() { return sourceWalletId; }
    public void setSourceWalletId(Long sourceWalletId) { this.sourceWalletId = sourceWalletId; }

    public Long getTargetWalletId() { return targetWalletId; }
    public void setTargetWalletId(Long targetWalletId) { this.targetWalletId = targetWalletId; }
}
