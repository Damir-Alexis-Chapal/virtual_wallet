package com.app_wallet.virtual_wallet.repository;

import com.app_wallet.virtual_wallet.entity.SystemPointsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SystemPointsRepository extends JpaRepository<SystemPointsEntity, Long> {
    @Query("SELECT sp FROM SystemPointsEntity sp WHERE sp.user.id = :userId")
    Optional<SystemPointsEntity> findByUserId(@Param("userId") Long userId);

}
