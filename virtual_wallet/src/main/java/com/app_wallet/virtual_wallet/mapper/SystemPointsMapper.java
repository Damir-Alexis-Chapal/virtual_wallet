package com.app_wallet.virtual_wallet.mapper;

import com.app_wallet.virtual_wallet.dto.SystemPointsDTO;
import com.app_wallet.virtual_wallet.entity.SystemPointsEntity;

public class SystemPointsMapper {
    public static SystemPointsDTO toDTO(SystemPointsEntity e) {
        if (e == null) return null;
        return new SystemPointsDTO(
            e.getId(),
            e.getAccumulatedPoints(),
            e.getRangeUser()
        );
    }

    public static SystemPointsEntity toEntity(SystemPointsDTO dto) {
        if (dto == null) return null;
        SystemPointsEntity e = new SystemPointsEntity();
        e.setId(dto.getId());
        e.setAccumulatedPoints(dto.getAccumulatedPoints());
        e.setRangeUser(dto.getRangeUser());
        return e;
    }
}
