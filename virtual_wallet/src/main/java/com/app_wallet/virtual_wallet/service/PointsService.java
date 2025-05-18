package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.entity.SystemPointsEntity;
import com.app_wallet.virtual_wallet.model.SystemPoints;
import com.app_wallet.virtual_wallet.repository.SystemPointsRepository;
import com.app_wallet.virtual_wallet.utils.BinaryTree;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PointsService {
    private final SystemPointsRepository repo;
    private final BinaryTree<SystemPoints> pointsTree = new BinaryTree<>();

    public PointsService(SystemPointsRepository repo) {
        this.repo = repo;
        // Carga inicial en el árbol
        repo.findAll().stream()
                .map(SystemPoints::new)
                .forEach(pointsTree::addRoot);
    }

    @Transactional
    public void addPointsForTransaction(Long userId, String type, BigDecimal amount) {

        int delta = switch(type) {
            case "DEPOSIT"    -> amount.divide(BigDecimal.valueOf(100)).intValue() * 1;
            case "WITHDRAWAL" -> amount.divide(BigDecimal.valueOf(100)).intValue() * 2;
            case "TRANSFER"   -> amount.divide(BigDecimal.valueOf(100)).intValue() * 3;
            default           -> 0;
        };

        SystemPointsEntity entity = repo.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("No points for user " + userId));
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
