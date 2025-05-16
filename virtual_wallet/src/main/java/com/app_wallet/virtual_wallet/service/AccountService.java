package com.app_wallet.virtual_wallet.service;


import com.app_wallet.virtual_wallet.dto.AccountDTO;
import com.app_wallet.virtual_wallet.entity.AccountEntity;
import com.app_wallet.virtual_wallet.mapper.AccountMapper;
import com.app_wallet.virtual_wallet.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public AccountDTO getAccountByName(String name){
        return null;
    }
    public AccountDTO getAccountById(Long id){
        return null;
    }
    public AccountDTO updateAccount(AccountDTO account){
        return null;
    }
    public void deleteAccount(Long id){

    }
    public void saveAccount(AccountDTO account){
        AccountEntity accountEntity = AccountMapper.toEntity(account);
        accountRepository.save(accountEntity);
    }
    public List<AccountDTO> getAccountsByUserId(Long userId) {
        List<AccountEntity> entities = accountRepository.findByUserId(userId);
        System.out.println("Found accounts for userId " + userId + ": " + entities.size());
        return entities.stream()
                .map(AccountMapper::toDTO)
                .collect(Collectors.toList());
    }



}
