package com.app_wallet.virtual_wallet.service;


import com.app_wallet.virtual_wallet.dto.AccountDTO;
import com.app_wallet.virtual_wallet.entity.AccountEntity;
import com.app_wallet.virtual_wallet.entity.UserEntity;
import com.app_wallet.virtual_wallet.mapper.AccountMapper;
import com.app_wallet.virtual_wallet.repository.AccountRepository;
import com.app_wallet.virtual_wallet.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public void saveAccount(AccountDTO dto){
        UserEntity userEntity = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        AccountEntity entity = new AccountEntity();
        entity.setUser(userEntity);
        entity.setAccountNumber(dto.getAccountNumber());
        entity.setBalance(dto.getBalance());

        UserEntity user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        entity.setUser(user);

        accountRepository.save(entity);

    }
    public List<AccountDTO> getAccountsByUserId(Long userId) {
        List<AccountEntity> entities = accountRepository.findByUserId(userId);
        System.out.println("Found accounts for userId " + userId + ": " + entities.size());
        return entities.stream()
                .map(AccountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public BigDecimal depositToAccount(Long accountId, BigDecimal amount) {
        AccountEntity acct = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalStateException("Account not found: " + accountId));
        acct.setBalance(acct.getBalance().add(amount));
        accountRepository.save(acct);
        return acct.getBalance();
    }

    @Transactional
    public BigDecimal withdrawFromAccount(Long accountId, BigDecimal amount) {
        AccountEntity acct = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalStateException("Account not found: " + accountId));
        if (acct.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds in real account");
        }
        acct.setBalance(acct.getBalance().subtract(amount));
        accountRepository.save(acct);
        return acct.getBalance();
    }
}
