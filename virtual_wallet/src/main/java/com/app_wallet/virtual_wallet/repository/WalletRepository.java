// src/main/java/com/app_wallet/virtual_wallet/repository/WalletRepository.java
package com.app_wallet.virtual_wallet.repository;

import com.app_wallet.virtual_wallet.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WalletRepository extends JpaRepository<WalletEntity,Long> {
  List<WalletEntity> findAllByUserId(Long userId);
}
