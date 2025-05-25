package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.dto.BenefitDTO;
import com.app_wallet.virtual_wallet.entity.BenefitEntity;
import com.app_wallet.virtual_wallet.repository.BenefitRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BenefitService {

    @Autowired
    BenefitRepository benefitRepository;

    @Transactional
    public boolean getBenefit(int points, Long userId) {
        System.out.println("Puntos recibidos: " + points);
        String title = switch (points) {
            case 100 -> "REDUX";
            case 500 -> "WITHDRAW";
            case 1000 -> "BONUS";
            default -> null;
        };
        System.out.println("Título generado: " + title);

        if (title == null) return false;

        if (userHasAnyBenefit(userId)) return false;

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
        result.put("benefit", appliedBenefit); // null si no se aplicó ninguno

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




}
