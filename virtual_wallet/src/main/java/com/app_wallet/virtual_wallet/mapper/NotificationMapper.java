package com.app_wallet.virtual_wallet.mapper;

import com.app_wallet.virtual_wallet.dto.NotificationDTO;
import com.app_wallet.virtual_wallet.entity.NotificationEntity;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    
    public static NotificationDTO toDTO(NotificationEntity entity) {
        if (entity == null) return null;
        
        NotificationDTO dto = new NotificationDTO();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setMessage(entity.getMessage());
        dto.setType(entity.getType());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setRead(entity.isRead());
        
        return dto;
    }
    
    public static NotificationEntity toEntity(NotificationDTO dto) {
        if (dto == null) return null;
        
        NotificationEntity entity = new NotificationEntity();
        entity.setId(dto.getId());
        entity.setUserId(dto.getUserId());
        entity.setMessage(dto.getMessage());
        entity.setType(dto.getType());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setRead(dto.isRead());
        
        return entity;
    }
}