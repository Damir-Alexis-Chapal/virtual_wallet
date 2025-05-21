package com.app_wallet.virtual_wallet.repository;

import com.app_wallet.virtual_wallet.entity.AccountEntity;
import com.app_wallet.virtual_wallet.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    List<AccountEntity> findByUserId(Long userId);
    List<AccountEntity> findByWalletId(Long walletId);
    Optional<AccountEntity> findByAccountNumber(Long accountNumber);


}
