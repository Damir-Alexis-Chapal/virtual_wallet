package com.app_wallet.virtual_wallet.dto;

import com.app_wallet.virtual_wallet.entity.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ScheduledTransactionDTO {
    private Long id;
    private Long userId;
    private Long sourceAccountId;
    private String destinationAccount;
    private BigDecimal amount;
    private String description;
    private String type;
    private Category category;
    private LocalDateTime scheduledDate;
    private String frequency;
    private boolean active;
}
