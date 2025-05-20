package com.app_wallet.virtual_wallet.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "scheduled_transactions")
@Data
public class ScheduledTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long userId;
    
    @Column(nullable = false)
    private Long sourceAccountId;
    
    @Column(nullable = false)
    private String destinationAccount;
    
    @Column(nullable = false)
    private BigDecimal amount;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private String type; // DEPOSIT, WITHDRAWAL, TRANSFER
    
    @Enumerated(EnumType.STRING)
    private Category category;
    
    @Column(nullable = false)
    private LocalDateTime scheduledDate;
    
    @Column(nullable = false)
    private String frequency; // ONCE, DAILY, WEEKLY, MONTHLY
    
    private boolean active = true;
}
