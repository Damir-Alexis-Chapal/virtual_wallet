package com.app_wallet.virtual_wallet.controller;

import com.app_wallet.virtual_wallet.service.WalletGraphService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wallet/graph")
public class WalletGraphController {

    private final WalletGraphService graphService;

    public WalletGraphController(WalletGraphService graphService) {
        this.graphService = graphService;
    }

    @PostMapping("/build")
    public ResponseEntity<Void> buildGraph(@RequestParam Long userId) {
        graphService.buildGraph(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<Map<Long, List<Long>>> getGraph(@RequestParam Long userId) {
        // Aseguramos que esté cargado
        graphService.buildGraph(userId);
        return ResponseEntity.ok(graphService.getAdjacencyMap());
    }

    @GetMapping("/from/{walletId}")
    public ResponseEntity<List<Long>> getConnectionsFrom(
            @RequestParam Long userId,
            @PathVariable Long walletId) {
        graphService.buildGraph(userId);
        List<Long> targets = graphService.getConnectionsFrom(walletId);
        return ResponseEntity.ok(targets);
    }
}
