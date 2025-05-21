// src/main/java/com/app_wallet/virtual_wallet/service/WalletService.java
package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.dto.WalletResponse;
import com.app_wallet.virtual_wallet.entity.AccountEntity;
import com.app_wallet.virtual_wallet.entity.WalletEntity;
import com.app_wallet.virtual_wallet.repository.WalletRepository;
import com.app_wallet.virtual_wallet.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletService {
  private final WalletRepository walletRepo;
  private final AccountRepository accountRepo;

  public WalletService(WalletRepository walletRepo,
                       AccountRepository accountRepo) {
    this.walletRepo  = walletRepo;
    this.accountRepo = accountRepo;
  }

  public WalletEntity create(Long userId, String name) {
    WalletEntity w = new WalletEntity();
    w.setUserId(userId);
    w.setName(name);
    return walletRepo.save(w);
  }

  public List<WalletResponse> listWithBalance(Long userId) {
    return walletRepo.findAllByUserId(userId)
            .stream()
            .map(w -> {
              BigDecimal bal = accountRepo.findByWalletId(w.getId())
                      .stream()
                      .map(a -> a.getBalance())
                      .reduce(BigDecimal.ZERO, BigDecimal::add);
              return new WalletResponse(w.getId(), w.getName(), bal);
            })
            .toList();
  }

  @Transactional
  public BigDecimal deposit(Long walletId, BigDecimal amount) {
    // Para simplificar: asumo que cada wallet tiene una sola "cuenta" interna
    // o usamos la primera de la lista
    AccountEntity acct = accountRepo.findByWalletId(walletId)
            .stream().findFirst()
            .orElseThrow(() -> new IllegalStateException("No account for wallet " + walletId));
    acct.setBalance(acct.getBalance().add(amount));
    accountRepo.save(acct);
    return acct.getBalance();
  }

  @Transactional
  public BigDecimal withdraw(Long walletId, BigDecimal amount) {
    AccountEntity acct = accountRepo.findByWalletId(walletId)
            .stream().findFirst()
            .orElseThrow(() -> new IllegalStateException("No account for wallet " + walletId));
    if (acct.getBalance().compareTo(amount) < 0) {
      throw new IllegalArgumentException("Insufficient funds");
    }
    acct.setBalance(acct.getBalance().subtract(amount));
    accountRepo.save(acct);
    return acct.getBalance();
  }

  @Transactional
  public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Amount must be positive");
    }

    var from = accountRepo.findByWalletId(fromWalletId).stream().findFirst()
            .orElseThrow(() -> new IllegalStateException("Origin wallet not found"));
    var to = accountRepo.findByWalletId(toWalletId).stream().findFirst()
            .orElseThrow(() -> new IllegalStateException("Target wallet not found"));

    if (from.getBalance().compareTo(amount) < 0) {
      throw new IllegalArgumentException("Insufficient funds");
    }

    from.setBalance(from.getBalance().subtract(amount));
    to.setBalance(to.getBalance().add(amount));

    accountRepo.save(from);
    accountRepo.save(to);
  }
}
