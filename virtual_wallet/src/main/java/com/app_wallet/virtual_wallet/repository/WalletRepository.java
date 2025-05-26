// src/main/java/com/app_wallet/virtual_wallet/repository/WalletRepository.java
package com.app_wallet.virtual_wallet.repository;

import com.app_wallet.virtual_wallet.entity.WalletEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<WalletEntity,Long> {
  List<WalletEntity> findAllByUserId(Long userId);
  Optional<WalletEntity> findByName(String name);
  Optional<WalletEntity> findById(Long id);
}
