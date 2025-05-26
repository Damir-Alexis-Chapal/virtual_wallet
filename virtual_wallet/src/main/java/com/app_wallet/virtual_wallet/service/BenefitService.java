package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.dto.BenefitDTO;
import com.app_wallet.virtual_wallet.entity.AccountEntity;
import com.app_wallet.virtual_wallet.entity.BenefitEntity;
import com.app_wallet.virtual_wallet.entity.SystemPointsEntity;
import com.app_wallet.virtual_wallet.repository.AccountRepository;
import com.app_wallet.virtual_wallet.repository.BenefitRepository;
import com.app_wallet.virtual_wallet.repository.SystemPointsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BenefitService {

    @Autowired
    BenefitRepository benefitRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private final SystemPointsRepository repo;

    public BenefitService(SystemPointsRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public boolean getBenefit(int points, Long userId) {
        String title = switch (points) {
            case 100 -> "REDUX";
            case 500 -> "WITHDRAW";
            case 1000 -> "BONUS";
            default -> null;
        };

        if (title == null) return false;

        List<BenefitEntity> benefits = benefitRepository.findByUserId(userId);

        if (!benefits.isEmpty()) {
            if (title.equals("BONUS")) {
                redeemBonus(userId);
                return true;
            }
            return false;
        }

        BenefitEntity benefit = new BenefitEntity();
        benefit.setUserId(userId);
        benefit.setTitle(title);
        benefit.setActive(true);
        benefitRepository.save(benefit);

        return true;
    }


    public Map<String, Object> applyBenefits(Long userId, BigDecimal amount, BigDecimal fee, String type) {
        List<BenefitEntity> activeBenefits = benefitRepository.findByUserId(userId).stream()
                .filter(BenefitEntity::isActive)
                .toList();

        BigDecimal adjustedFee = fee;
        String appliedBenefit = null;

        switch (type.toUpperCase()) {
            case "TRANSFER":
                boolean hasRedux = activeBenefits.stream()
                        .anyMatch(b -> "REDUX".equalsIgnoreCase(b.getTitle()));
                if (hasRedux) {
                    adjustedFee = fee.multiply(BigDecimal.valueOf(0.90));
                    appliedBenefit = "REDUX";
                }
                break;

            case "WITHDRAWAL":
                boolean hasWithdraw = activeBenefits.stream()
                        .anyMatch(b -> "WITHDRAW".equalsIgnoreCase(b.getTitle()));
                if (hasWithdraw) {
                    adjustedFee = BigDecimal.ZERO;
                    appliedBenefit = "WITHDRAW";
                }
                break;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("total", amount.add(adjustedFee));
        result.put("benefit", appliedBenefit);

        return result;
    }

    public boolean userHasAnyBenefit( Long userId) {
        return !benefitRepository.findByUserId(userId).isEmpty();

    }

    public BenefitDTO getActualBenefit(Long userId) {
        List<BenefitEntity> benefits = benefitRepository.findByUserId(userId);
        if (benefits.isEmpty()) {
            return new BenefitDTO("No benefits", false);
        }

        BenefitEntity benefit = benefits.get(0);
        return new BenefitDTO(benefit.getTitle(), benefit.isActive());
    }

    public void redeemBonus(Long userId) {
        List<AccountEntity> accounts = accountRepository.findByUserId(userId);

        if (accounts.isEmpty()) {
            return;
        }

        BigDecimal totalBonus = new BigDecimal("50000");
        BigDecimal bonus = totalBonus.divide(new BigDecimal(accounts.size()), RoundingMode.DOWN);

        for (AccountEntity account : accounts) {
            account.setBalance(account.getBalance().add(bonus));
            accountRepository.save(account);
        }

    }





}
