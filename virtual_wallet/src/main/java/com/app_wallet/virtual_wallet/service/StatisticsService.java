// src/main/java/com/app_wallet/virtual_wallet/service/StatisticsService.java
package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.model.Category;
import com.app_wallet.virtual_wallet.repository.TransactionRepository;
import com.app_wallet.virtual_wallet.utils.CategoryGraph;
import com.app_wallet.virtual_wallet.utils.MyHashMap;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StatisticsService {

  private final TransactionRepository trxRepo;

  public StatisticsService(TransactionRepository trxRepo) {
    this.trxRepo = trxRepo;
  }

  private CategoryGraph buildCategoryGraph(Long userId) {
    List<com.app_wallet.virtual_wallet.entity.TransactionEntity> all =
            trxRepo.findByUserIdOrderByDateAsc(userId);
    CategoryGraph g = new CategoryGraph();
    for (int i = 1; i < all.size(); i++) {
      Category from = all.get(i - 1).getCategory();
      Category to   = all.get(i).getCategory();
      g.addEdge(from, to);
    }
    return g;
  }

  public Map<Category,Integer> categoryTransitionCounts(Long userId) {
    Optional<com.app_wallet.virtual_wallet.entity.TransactionEntity> firstOp =
            trxRepo.findFirstByUserIdOrderByDateAsc(userId);
    if (firstOp.isEmpty()) return Collections.emptyMap();

    CategoryGraph g = buildCategoryGraph(userId);
    MyHashMap<Category,Integer> custom = g.breadthFirstCounts(firstOp.get().getCategory());
    Map<Category,Integer> result = new HashMap<>();

    for (Category cat : custom.keySet()) {
      result.put(cat, custom.get(cat));
    }
    return result;
  }

}
