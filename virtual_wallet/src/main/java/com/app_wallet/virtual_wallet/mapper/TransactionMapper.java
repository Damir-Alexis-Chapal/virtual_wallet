package com.app_wallet.virtual_wallet.mapper;

import com.app_wallet.virtual_wallet.dto.TransactionDTO;
import com.app_wallet.virtual_wallet.entity.TransactionEntity;

public class TransactionMapper {

    public static TransactionEntity toEntity(TransactionDTO dto, Long userId, Long accountOriginId) {
        TransactionEntity entity = new TransactionEntity();
        entity.setAmount(dto.getAmount());
        entity.setType(dto.getType());
        entity.setDate(dto.getDate());
        entity.setDescription(dto.getDescription());
        entity.setAccountOrigin(accountOriginId);
        entity.setUserDestiny(dto.getDestination());
        entity.setUserId(userId);
        entity.setCategory(dto.getCategory());
        return entity;
    }

    public static TransactionDTO toDTO(TransactionEntity entity) {
        if (entity == null) return null;

        TransactionDTO dto = new TransactionDTO();
        dto.setId(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setType(entity.getType());
        dto.setDate(entity.getDate());
        dto.setDescription(entity.getDescription());
        dto.setOrigin(entity.getAccountOrigin());
        dto.setDestination(entity.getUserDestiny());
        dto.setCategory(entity.getCategory());

        return dto;
    }
}
