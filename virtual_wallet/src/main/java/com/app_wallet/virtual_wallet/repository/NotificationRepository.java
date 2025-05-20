package com.app_wallet.virtual_wallet.repository;

import com.app_wallet.virtual_wallet.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<NotificationEntity> findByUserIdAndReadOrderByCreatedAtDesc(Long userId, boolean read);
    long countByUserIdAndRead(Long userId, boolean read);
}