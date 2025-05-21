package com.app_wallet.virtual_wallet.repository;

import com.app_wallet.virtual_wallet.entity.TransactionEntity;
import com.app_wallet.virtual_wallet.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> findTop3ByUserIdOrderByDateDesc(Long userId);
    List<TransactionEntity> findByUserIdOrderByDateDesc(Long userId);
    List<TransactionEntity> findByUserIdOrderByDateAsc(Long userId);
    Optional<TransactionEntity> findFirstByUserIdOrderByDateAsc(Long userId);

}
