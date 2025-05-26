package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.entity.BenefitEntity;
import com.app_wallet.virtual_wallet.entity.SystemPointsEntity;
import com.app_wallet.virtual_wallet.entity.UserEntity;
import com.app_wallet.virtual_wallet.model.SystemPoints;
import com.app_wallet.virtual_wallet.repository.SystemPointsRepository;
import com.app_wallet.virtual_wallet.repository.UserRepository;
import com.app_wallet.virtual_wallet.utils.BinaryTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PointsService {
    @Autowired
    private final SystemPointsRepository repo;
    private final BinaryTree<SystemPoints> pointsTree = new BinaryTree<>();
    private final SystemPointsEntity systemPointsEntity = new SystemPointsEntity();
    private final BenefitService benefitService;

    @Autowired
    private final UserRepository userRepository; // asegúrate de tenerlo

    public PointsService(SystemPointsRepository repo, UserRepository userRepository,  BenefitService benefitService) {
        this.repo = repo;
        this.userRepository = userRepository;
        this.benefitService = benefitService;

        repo.findAll().stream()
                .map(SystemPoints::new)
                .forEach(pointsTree::addRoot);
    }


    @Transactional
    public void addPointsForTransaction(Long userId, String type, BigDecimal amount) {
        BigDecimal baseUnits = amount.divide(BigDecimal.valueOf(100), RoundingMode.DOWN);

        BigDecimal delta = switch (type) {
            case "DEPOSIT"    -> baseUnits.multiply(BigDecimal.valueOf(0.01));
            case "WITHDRAWAL" -> baseUnits.multiply(BigDecimal.valueOf(0.02));
            case "TRANSFER"   -> baseUnits.multiply(BigDecimal.valueOf(0.03));
            default           -> BigDecimal.ZERO;
        };
        int deltaPoints = delta.intValue();

        SystemPointsEntity entity = repo.findByUserId(userId).orElseGet(() -> {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalStateException("User not found with ID " + userId));
            return repo.save(new SystemPointsEntity(user));
        });

        pointsTree.eliminate(new SystemPoints(entity));
        entity.addPoints(deltaPoints);
        repo.save(entity);
        pointsTree.addRoot(new SystemPoints(entity));
    }



    public List<SystemPoints> getTopUsers(int n) {
        List<SystemPoints> all = pointsTree.inOrderRout();
        Collections.reverse(all);
        return all.stream().limit(n).toList();
    }

    public List<SystemPointsEntity> getTopUsersEntities(int n) {
        return getTopUsers(n).stream()
                .map(sp -> repo.findByUserId(sp.getUserId())
                        .orElseThrow())
                .toList();
    }
    @Transactional
    public boolean redeemPoints(Long userId, int points) {

        Optional<SystemPointsEntity> optional = repo.findByUserId(userId);
        if (optional.isEmpty()) {
            return false;
        }

        SystemPointsEntity point = optional.get();
        if (point.getAccumulatedPoints() < 0) {
            return false;
        }

        boolean benefitGranted = benefitService.getBenefit(points, userId);
        if (!benefitGranted) {
            return false;
        }

        point.setAccumulatedPoints(point.getAccumulatedPoints()-points);
        repo.save(point);

        return true;
    }




    public Optional<SystemPointsEntity> findByUserId(Long userId) {
        return repo.findByUserId(userId);
    }
}
