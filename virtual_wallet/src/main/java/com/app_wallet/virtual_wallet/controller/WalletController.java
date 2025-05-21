// src/main/java/com/app_wallet/virtual_wallet/controller/WalletController.java
package com.app_wallet.virtual_wallet.controller;

import com.app_wallet.virtual_wallet.dto.CreateWalletRequest;
import com.app_wallet.virtual_wallet.dto.WalletConnectionDTO;
import com.app_wallet.virtual_wallet.dto.WalletResponse;
import com.app_wallet.virtual_wallet.entity.WalletEntity;
import com.app_wallet.virtual_wallet.service.WalletService;
import com.app_wallet.virtual_wallet.service.WalletGraphService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wallets")
public class WalletController {
    private final WalletService walletService;
    private final WalletGraphService graphService;

    public WalletController(WalletService walletService,
                            WalletGraphService graphService) {
        this.walletService = walletService;
        this.graphService  = graphService;
    }

    @PostMapping
    public ResponseEntity<WalletResponse> create(@RequestBody CreateWalletRequest req) {
        var w = walletService.create(req.userId(), req.name());
        // devolvemos también balance (cero recién creado)
        return ResponseEntity.ok(new WalletResponse(w.getId(), w.getName(), BigDecimal.ZERO));
    }

    @GetMapping
    public ResponseEntity<List<WalletResponse>> list(@RequestParam Long userId) {
        return ResponseEntity.ok(walletService.listWithBalance(userId));
    }

    @PostMapping("/graph/connect")
    public ResponseEntity<Void> connect(@RequestBody WalletConnectionDTO dto) {
        graphService.addConnection(dto.userId(), dto.sourceWalletId(), dto.targetWalletId());
        graphService.buildGraph(dto.userId());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/graph")
    public ResponseEntity<Map<Long,List<Long>>> graph(@RequestParam Long userId) {
        graphService.buildGraph(userId);
        return ResponseEntity.ok(graphService.getAdjacencyMap());
    }

    @GetMapping("/graph/from/{walletId}")
    public ResponseEntity<List<Long>> from(@RequestParam Long userId,
                                           @PathVariable Long walletId) {
        graphService.buildGraph(userId);
        return ResponseEntity.ok(graphService.getConnectionsFrom(walletId));
    }

    @PostMapping("/{walletId}/deposit")
    public ResponseEntity<BigDecimal> depositToWallet(
            @PathVariable Long walletId,
            @RequestParam BigDecimal amount
    ) {
        BigDecimal newBal = walletService.deposit(walletId, amount);
        return ResponseEntity.ok(newBal);
    }

    @PostMapping("/{walletId}/withdraw")
    public ResponseEntity<BigDecimal> withdrawFromWallet(
            @PathVariable Long walletId,
            @RequestParam BigDecimal amount
    ) {
        BigDecimal newBal = walletService.withdraw(walletId, amount);
        return ResponseEntity.ok(newBal);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferBetweenWallets(
            @RequestParam Long fromWalletId,
            @RequestParam Long toWalletId,
            @RequestParam BigDecimal amount
    ) {
        try {
            walletService.transfer(fromWalletId, toWalletId, amount);
            return ResponseEntity.ok("Transferencia exitosa");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
