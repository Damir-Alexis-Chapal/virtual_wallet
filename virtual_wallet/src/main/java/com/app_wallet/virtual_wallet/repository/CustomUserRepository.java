package com.app_wallet.virtual_wallet.repository;

import com.app_wallet.virtual_wallet.dto.UserDTO;
import com.app_wallet.virtual_wallet.entity.UserEntity;
import com.app_wallet.virtual_wallet.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CustomUserRepository {

    @Autowired
    private UserRepository userRepository; // Usamos la interfaz de JPA

    @Autowired
    private UserMapper userMapper;

    public UserDTO save(UserDTO userDTO) {
        UserEntity entity = userMapper.dtoToEntity(userDTO);
        UserEntity savedEntity = userRepository.save(entity);
        return userMapper.entityToDto(savedEntity);
    }

    public Optional<UserDTO> findDtoById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::entityToDto);
    }

    public List<UserDTO> findAllDtos() {
        return userRepository.findAll().stream()
                .map(userMapper::entityToDto)
                .collect(Collectors.toList());
    }

    public UserDTO update(UserDTO userDTO) {
        if (userDTO.getId() == null) {
            throw new IllegalArgumentException("ID cannot be null for update");
        }
        return save(userDTO);
    }
}

