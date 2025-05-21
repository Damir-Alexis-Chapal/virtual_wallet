// src/main/java/com/app_wallet/virtual_wallet/service/StatisticsService.java
package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.dto.UserDTO;
import com.app_wallet.virtual_wallet.entity.TransactionEntity;
import com.app_wallet.virtual_wallet.model.Category;
import com.app_wallet.virtual_wallet.repository.AccountRepository;
import com.app_wallet.virtual_wallet.repository.TransactionRepository;
import com.app_wallet.virtual_wallet.utils.CategoryGraph;
import com.app_wallet.virtual_wallet.utils.ClientGraph;
import com.app_wallet.virtual_wallet.utils.MyHashMap;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticsService {

  private final TransactionRepository trxRepo;
  private final AccountRepository accountRepo;

  public StatisticsService(TransactionRepository trxRepo,  AccountRepository accountRepo) {
    this.trxRepo = trxRepo;
    this.accountRepo = accountRepo;
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

  public List<Map<String,Object>> frequentClientTransfers(Long userId) {
    ClientGraph g = buildClientGraph(userId);
    UserDTO start = new UserDTO(userId, /* opcional nombre */ null);
    MyHashMap<UserDTO,Integer> counts = g.breadthFirstCounts(start);

    return counts.keySet().stream()
            .filter(u -> !u.getId().equals(userId))
            .map(u -> Map.<String,Object>of(
                    "id",    u.getId(),
                    "label", "User "+u.getId(),
                    "count", counts.get(u)
            ))
            .toList();
  }

  public List<List<Map<String,Object>>> findAffinityGroups(Long userId) {
    ClientGraph g = buildClientGraph(userId);
    Set<UserDTO> seen = new HashSet<>();
    List<List<Map<String,Object>>> groups = new ArrayList<>();

    for (UserDTO u : g.getVertices()) {
      if (seen.add(u)) {
        List<Map<String,Object>> comp = new ArrayList<>();
        dfsCollect(u, g, seen, comp);
        groups.add(comp);
      }
    }
    return groups;
  }

  private void dfsCollect(UserDTO u, ClientGraph g, Set<UserDTO> seen, List<Map<String,Object>> comp) {
    comp.add(Map.of("id",u.getId(),"label","User "+u.getId()));
    for (UserDTO nb : g.neighbors(u)) {
      if (seen.add(nb)) dfsCollect(nb,g,seen,comp);
    }
  }

  private ClientGraph buildClientGraph(Long userId) {
    List<TransactionEntity> all = trxRepo.findByUserIdOrderByDateAsc(userId);
    ClientGraph graph = new ClientGraph();

    for (TransactionEntity trx : all) {
      if (!"SEND".equals(trx.getType())) continue;

      String destAccountNumber = trx.getUserDestiny();
      if (destAccountNumber == null || !destAccountNumber.matches("\\d+")) continue;

      try {
        Long destAccNum = Long.valueOf(destAccountNumber);

        accountRepo.findByAccountNumber(destAccNum).ifPresent(destAccount -> {
          Long destUserId = destAccount.getUserId();

          UserDTO from = new UserDTO(userId, "", "");
          UserDTO to   = new UserDTO(destUserId, "", "");

          graph.addEdge(from, to);
        });

      } catch (NumberFormatException e) {
        // Puedes registrar si quieres saber qué dato causó el problema
        System.err.println("Número de cuenta destino inválido: " + destAccountNumber);
      }
    }
    return graph;
  }




}
