package com.app_wallet.virtual_wallet.service;

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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PointsService {
    @Autowired
    private final SystemPointsRepository repo;

    private final BinaryTree<SystemPoints> pointsTree = new BinaryTree<>();

    @Autowired
    private final UserRepository userRepository; // asegúrate de tenerlo

    public PointsService(SystemPointsRepository repo, UserRepository userRepository) {
        this.repo = repo;
        this.userRepository = userRepository;

        repo.findAll().stream()
                .map(SystemPoints::new)
                .forEach(pointsTree::addRoot);
    }


    @Transactional
    public void addPointsForTransaction(Long userId, String type, BigDecimal amount) {
        int delta = switch (type) {
            case "DEPOSIT"    -> amount.divide(BigDecimal.valueOf(100)).intValue() * 1;
            case "WITHDRAWAL" -> amount.divide(BigDecimal.valueOf(100)).intValue() * 2;
            case "TRANSFER"   -> amount.divide(BigDecimal.valueOf(100)).intValue() * 3;
            default           -> 0;
        };

        SystemPointsEntity entity = repo.findByUserId(userId).orElseGet(() -> {
            UserEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalStateException("User not found with ID " + userId));
            return repo.save(new SystemPointsEntity(user));
        });

        pointsTree.eliminate(new SystemPoints(entity));
        entity.addPoints(delta);
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
}
