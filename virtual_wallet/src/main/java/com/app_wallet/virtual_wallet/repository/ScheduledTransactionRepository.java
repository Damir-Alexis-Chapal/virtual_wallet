package com.app_wallet.virtual_wallet.repository;

import com.app_wallet.virtual_wallet.entity.ScheduledTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduledTransactionRepository extends JpaRepository<ScheduledTransactionEntity, Long> {
    List<ScheduledTransactionEntity> findByExecutedFalseAndScheduledDatetimeBefore(LocalDateTime time);
    List<ScheduledTransactionEntity> findByUserId(Long userId);
}

