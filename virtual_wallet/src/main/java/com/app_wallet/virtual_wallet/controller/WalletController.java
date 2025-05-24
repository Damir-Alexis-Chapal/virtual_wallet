package com.app_wallet.virtual_wallet.controller;

import com.app_wallet.virtual_wallet.dto.CreateWalletRequest;
import com.app_wallet.virtual_wallet.dto.WalletConnectionDTO;
import com.app_wallet.virtual_wallet.dto.WalletResponse;
import com.app_wallet.virtual_wallet.entity.WalletEntity;
import com.app_wallet.virtual_wallet.service.AccountService;
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
    private final AccountService accountService;

    public WalletController(WalletService walletService,
                            WalletGraphService graphService,
                            AccountService accountService) {
        this.walletService = walletService;
        this.graphService  = graphService;
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<WalletResponse> create(@RequestBody CreateWalletRequest req) {
        var w = walletService.create(req.userId(), req.name());
        return ResponseEntity.ok(new WalletResponse(w.getId(), w.getName(), w.getAmount()));
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

    // Deposit money from a real account into a wallet
    @PostMapping("/{walletId}/deposit")
    public ResponseEntity<BigDecimal> depositToWallet(
            @PathVariable Long walletId,
            @RequestParam BigDecimal amount,
            @RequestParam Long accountId
    ) {
        // 1) Withdraw from the real account
        accountService.withdrawFromAccount(accountId, amount);
        // 2) Deposit into the wallet
        BigDecimal newBal = walletService.deposit(walletId, amount);
        return ResponseEntity.ok(newBal);
    }

    // Withdraw money from a wallet back to a real account
    @PostMapping("/{walletId}/withdraw")
    public ResponseEntity<BigDecimal> withdrawFromWallet(
            @PathVariable Long walletId,
            @RequestParam BigDecimal amount,
            @RequestParam Long accountId
    ) {
        // 1) Withdraw from the wallet
        BigDecimal newBal = walletService.withdraw(walletId, amount);
        // 2) Deposit back into the real account
        accountService.depositToAccount(accountId, amount);
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

    @DeleteMapping("/{walletId}")
    public ResponseEntity<String> deleteWallet(@PathVariable Long walletId) {
        try {
            walletService.deleteWallet(walletId);
            return ResponseEntity.ok("Wallet deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting wallet: " + e.getMessage());
        }
    }
}
