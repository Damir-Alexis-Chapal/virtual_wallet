package com.app_wallet.virtual_wallet.mapper;

import com.app_wallet.virtual_wallet.dto.AccountDTO;
import com.app_wallet.virtual_wallet.entity.AccountEntity;

public class AccountMapper {

    // DTO a Entity
    public static AccountEntity toEntity(AccountDTO dto) {
        if (dto == null) {
            return null;
        }

        AccountEntity entity = new AccountEntity();
        entity.setId(dto.getId());
        entity.setBalance(dto.getBalance());
        entity.setUserId(dto.getUserId());
        entity.setAccountNumber(dto.getAccountNumber());

        return entity;
    }

    // Entity a DTO
    public static AccountDTO toDTO(AccountEntity entity) {
        if (entity == null) {
            return null;
        }

        AccountDTO dto = new AccountDTO();
        dto.setId(entity.getId());
        dto.setBalance(entity.getBalance());
        dto.setUserId(entity.getUserId());
        dto.setAccountNumber(entity.getAccountNumber());

        return dto;
    }
}
