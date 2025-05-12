package com.app_wallet.virtual_wallet.mapper;

import com.app_wallet.virtual_wallet.dto.UserDTO;
import com.app_wallet.virtual_wallet.entity.AccountEntity;
import com.app_wallet.virtual_wallet.entity.NotificationEntity;
import com.app_wallet.virtual_wallet.entity.UserEntity;
import com.app_wallet.virtual_wallet.model.UserModel;
import com.app_wallet.virtual_wallet.utils.LinkedList;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    // Convertir de Entity a Model
    public UserModel entityToModel(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        UserModel model = new UserModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setEmail(entity.getEmail());
        model.setPassword(entity.getPassword());
        model.setSystemPoints(entity.getSystemPoints());

        // Convertir List a LinkedList para accounts
        LinkedList<AccountEntity> accountList = new LinkedList<>();
        if (entity.getAccounts() != null) {
            entity.getAccounts().forEach(accountList::add);
        }
        model.setAccounts(accountList);

        // Convertir List a LinkedList para notifications
        LinkedList<NotificationEntity> notificationList = new LinkedList<>();
        if (entity.getNotifications() != null) {
            entity.getNotifications().forEach(notificationList::add);
        }
        model.setNotifications(notificationList);

        return model;
    }

    // Convertir de Model a Entity
    public UserEntity modelToEntity(UserModel model) {
        if (model == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setEmail(model.getEmail());
        entity.setPassword(model.getPassword());
        entity.setSystemPoints(model.getSystemPoints());

        // Convertir LinkedList a List para accounts
        List<AccountEntity> accountList = new ArrayList<>();
        if (model.getAccounts() != null) {
            model.getAccounts().forEach(accountList::add);
        }
        entity.setAccounts(accountList);

        // Convertir LinkedList a List para notifications
        List<NotificationEntity> notificationList = new ArrayList<>();
        if (model.getNotifications() != null) {
            model.getNotifications().forEach(notificationList::add);
        }
        entity.setNotifications(notificationList);

        return entity;
    }

    // Convertir de Entity a DTO completo
    public UserDTO entityToDto(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setSystemPoints(entity.getSystemPoints());

        // Solo incluimos los IDs de las relaciones
        if (entity.getAccounts() != null) {
            dto.setAccountIds(entity.getAccounts().stream()
                    .map(account -> account.getId())
                    .collect(Collectors.toList()));
        }

        if (entity.getNotifications() != null) {
            dto.setNotificationIds(entity.getNotifications().stream()
                    .map(notification -> notification.getId())
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    // Convertir de DTO a Entity (para creación/actualización)
    public static UserEntity dtoToEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setSystemPoints(dto.getSystemPoints());
        // Nota: La contraseña normalmente se maneja por separado por seguridad

        return entity;
    }
}