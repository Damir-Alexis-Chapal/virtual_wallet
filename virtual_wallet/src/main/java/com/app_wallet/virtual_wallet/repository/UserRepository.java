package com.app_wallet.virtual_wallet.repository;

import com.app_wallet.virtual_wallet.dto.UserDTO;
import com.app_wallet.virtual_wallet.entity.UserEntity;
import com.app_wallet.virtual_wallet.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    /*

     // Clase interna con implementaciones default para manejar DTOs
    @Repository
    class UserRepositoryImpl implements UserRepositoryExtensions {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private UserMapper userMapper;

        @Override
        public UserDTO save(UserDTO userDTO) {
            UserEntity entity = userMapper.dtoToEntity(userDTO);
            UserEntity savedEntity = userRepository.save(entity);
            return userMapper.entityToDto(savedEntity);
        }

        @Override
        public Optional<UserDTO> findDtoById(Long id) {
            return userRepository.findById(id)
                    .map(userMapper::entityToDto);
        }

        @Override
        public List<UserDTO> findAllDtos() {
            return userRepository.findAll().stream()
                    .map(userMapper::entityToDto)
                    .collect(Collectors.toList());
        }

        @Override
        public UserDTO update(UserDTO userDTO) {
            if (userDTO.getId() == null) {
                throw new IllegalArgumentException("ID cannot be null for update");
            }
            return save(userDTO);
        }
    }

    // Interfaz para extender las capacidades del repositorio
    interface UserRepositoryExtensions {
        UserDTO save(UserDTO userDTO);
        Optional<UserDTO> findDtoById(Long id);
        List<UserDTO> findAllDtos();
        UserDTO update(UserDTO userDTO);
    }


     */

}