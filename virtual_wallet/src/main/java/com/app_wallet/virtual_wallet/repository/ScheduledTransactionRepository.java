package com.app_wallet.virtual_wallet.repository;

import com.app_wallet.virtual_wallet.entity.ScheduledTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduledTransactionRepository extends JpaRepository<ScheduledTransactionEntity, Long> {
    List<ScheduledTransactionEntity> findByUserId(Long userId);
    
    @Query("SELECT s FROM ScheduledTransactionEntity s WHERE s.active = true AND s.scheduledDate <= :now")
    List<ScheduledTransactionEntity> findDueTransactions(LocalDateTime now);
}