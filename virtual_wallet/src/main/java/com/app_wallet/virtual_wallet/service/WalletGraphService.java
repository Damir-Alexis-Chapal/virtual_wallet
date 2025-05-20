package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.entity.WalletConnectionEntity;
import com.app_wallet.virtual_wallet.repository.WalletConnectionRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WalletGraphService {

    private final WalletConnectionRepository repository;
    private final Map<Long, List<Long>> adjacencyMap = new HashMap<>();

    public WalletGraphService(WalletConnectionRepository repository) {
        this.repository = repository;
    }

    public void buildGraph(Long userId) {
        adjacencyMap.clear();
        List<WalletConnectionEntity> connections = repository.findAllByUserId(userId);
        for (WalletConnectionEntity conn : connections) {
            adjacencyMap
                    .computeIfAbsent(conn.getSourceWalletId(), k -> new ArrayList<>())
                    .add(conn.getTargetWalletId());
        }
    }

    public List<Long> getConnectionsFrom(Long walletId) {
        return adjacencyMap.getOrDefault(walletId, Collections.emptyList());
    }

    public boolean isConnected(Long sourceWalletId, Long targetWalletId) {
        return adjacencyMap
                .getOrDefault(sourceWalletId, Collections.emptyList())
                .contains(targetWalletId);
    }

    public Map<Long, List<Long>> getAdjacencyMap() {
        return Collections.unmodifiableMap(adjacencyMap);
    }
}
