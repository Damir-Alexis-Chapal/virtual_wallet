package com.app_wallet.virtual_wallet.repository;

import com.app_wallet.virtual_wallet.entity.WalletConnectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface WalletConnectionRepository extends JpaRepository<WalletConnectionEntity, Long> {

    List<WalletConnectionEntity> findAllByUserId(Long userId);
}
