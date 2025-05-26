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
    w.setAmount(BigDecimal.ZERO);
    return walletRepo.save(w);
  }

  public List<WalletResponse> listWithBalance(Long userId) {
    return walletRepo.findAllByUserId(userId)
            .stream()
            .map(w -> new WalletResponse(w.getId(), w.getName(), w.getAmount()))
            .toList();
  }

  @Transactional
  public BigDecimal deposit(Long walletId, BigDecimal amount) {
    WalletEntity w = walletRepo.findById(walletId)
            .orElseThrow(() -> new IllegalStateException("Wallet no encontrada: " + walletId));
    w.setAmount(w.getAmount().add(amount));
    walletRepo.save(w);
    return w.getAmount();
  }

  @Transactional
  public BigDecimal withdraw(Long walletId, BigDecimal amount) {
    WalletEntity w = walletRepo.findById(walletId)
            .orElseThrow(() -> new IllegalStateException("Wallet no encontrada: " + walletId));
    if (w.getAmount().compareTo(amount) < 0) {
      throw new IllegalArgumentException("Fondos insuficientes");
    }
    w.setAmount(w.getAmount().subtract(amount));
    walletRepo.save(w);
    return w.getAmount();
  }


  @Transactional
  public void transfer(Long fromWalletId, Long toWalletId, BigDecimal amount) {
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new IllegalArgumentException("Amount must be positive");
    }

    WalletEntity from = walletRepo.findById(fromWalletId)
            .orElseThrow(() -> new IllegalArgumentException("Origin wallet not found"));
    WalletEntity to = walletRepo.findById(toWalletId)
            .orElseThrow(() -> new IllegalArgumentException("Target wallet not found"));

    if (from.getAmount().compareTo(amount) < 0) {
      throw new IllegalArgumentException("Insufficient funds");
    }

    from.setAmount(from.getAmount().subtract(amount));
    to.setAmount(to.getAmount().add(amount));

    walletRepo.save(from);
    walletRepo.save(to);
  }



  @Transactional
  public void deleteWallet(Long walletId) {
    // Verificar que la wallet existe
    WalletEntity wallet = walletRepo.findById(walletId)
            .orElseThrow(() -> new IllegalArgumentException("Wallet not found with id: " + walletId));

    // Opcional: Verificar que la wallet tenga balance 0 antes de eliminar
    if (wallet.getAmount().compareTo(BigDecimal.ZERO) != 0) {
      throw new IllegalArgumentException("Cannot delete wallet with non-zero balance. Current balance: " + wallet.getAmount());
    }

    // Eliminar la wallet
    walletRepo.deleteById(walletId);
  }

}
