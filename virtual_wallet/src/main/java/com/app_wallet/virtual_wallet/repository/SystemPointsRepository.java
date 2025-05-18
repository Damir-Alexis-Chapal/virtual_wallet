package com.app_wallet.virtual_wallet.repository;

import com.app_wallet.virtual_wallet.entity.SystemPointsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SystemPointsRepository extends JpaRepository<SystemPointsEntity, Long> {
    Optional<SystemPointsEntity> findByUserId(Long userId);
}
