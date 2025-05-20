package com.app_wallet.virtual_wallet.mapper;

import com.app_wallet.virtual_wallet.dto.ScheduledTransactionDTO;
import com.app_wallet.virtual_wallet.entity.ScheduledTransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTransactionMapper {
    
    public static ScheduledTransactionDTO toDTO(ScheduledTransactionEntity entity) {
        if (entity == null) return null;
        
        ScheduledTransactionDTO dto = new ScheduledTransactionDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setSourceAccountId(entity.getSourceAccountId());
        dto.setDestinationAccount(entity.getDestinationAccount());
        dto.setAmount(entity.getAmount());
        dto.setDescription(entity.getDescription());
        dto.setType(entity.getType());
        dto.setCategory(entity.getCategory());
        dto.setScheduledDate(entity.getScheduledDate());
        dto.setFrequency(entity.getFrequency());
        dto.setActive(entity.isActive());
        
        return dto;
    }
    
    public static ScheduledTransactionEntity toEntity(ScheduledTransactionDTO dto) {
        if (dto == null) return null;
        
        ScheduledTransactionEntity entity = new ScheduledTransactionEntity();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setSourceAccountId(dto.getSourceAccountId());
        entity.setDestinationAccount(dto.getDestinationAccount());
        entity.setAmount(dto.getAmount());
        entity.setDescription(dto.getDescription());
        entity.setType(dto.getType());
        entity.setCategory(dto.getCategory());
        entity.setScheduledDate(dto.getScheduledDate());
        entity.setFrequency(dto.getFrequency());
        entity.setActive(dto.isActive());
        
        return entity;
    }
}