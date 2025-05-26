package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.entity.TransactionEntity;
import com.app_wallet.virtual_wallet.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

  private final TransactionRepository trxRepo;

  public StatisticsService(TransactionRepository trxRepo) {
    this.trxRepo = trxRepo;
  }

  public List<Map<String, Object>> getCategoryStatistics(Long userId) {
    List<TransactionEntity> txs = trxRepo.findByUserIdOrderByDateAsc(userId);

    if (txs.isEmpty()) return List.of();
    System.out.println("Transacciones encontradas para userId " + userId + ": " + txs.size());

    Map<String, Long> categoryCounts = txs.stream()
            .filter(tx -> tx.getCategory() != null)
            .collect(Collectors.groupingBy(
                    tx -> tx.getCategory().name(),
                    Collectors.counting()
            ));

    return categoryCounts.entrySet().stream()
            .map(e -> Map.<String, Object>of(  // Nota el tipo explícito aquí
                    "category", e.getKey(),
                    "count", e.getValue(),
                    "percentage", (e.getValue() * 100) / txs.size()
            ))
            .collect(Collectors.toList());
  }

  public List<Map<String, Object>> getFrequentTransfers(Long userId) {
    List<TransactionEntity> txs = trxRepo.findByUserIdOrderByDateAsc(userId);

    if (txs.isEmpty()) return List.of();

    return txs.stream()
            .filter(tx -> "TRANSFER".equalsIgnoreCase(tx.getType()) && tx.getUserDestiny() != null)
            .collect(Collectors.groupingBy(
                    TransactionEntity::getUserDestiny,
                    Collectors.counting()
            ))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(10) // Limitar a los 10 más frecuentes
            .map(e -> {
              Map<String, Object> map = new HashMap<>();
              map.put("label", e.getKey());
              map.put("count", e.getValue());
              return map;
            })
            .collect(Collectors.toList());
  }

}