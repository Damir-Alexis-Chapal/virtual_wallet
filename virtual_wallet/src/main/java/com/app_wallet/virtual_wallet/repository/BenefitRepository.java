package com.app_wallet.virtual_wallet.repository;

import com.app_wallet.virtual_wallet.entity.AccountEntity;
import com.app_wallet.virtual_wallet.entity.BenefitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BenefitRepository extends JpaRepository<BenefitEntity, Long> {
    List<BenefitEntity> findByUserId(Long userId);
}
