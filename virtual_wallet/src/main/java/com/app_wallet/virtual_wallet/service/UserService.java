package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.dto.UserDTO;
import com.app_wallet.virtual_wallet.repository.CustomUserRepository;
import com.app_wallet.virtual_wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final CustomUserRepository customUserRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserService(CustomUserRepository customUserRepository,
                       UserRepository userRepository) {
        this.customUserRepository = customUserRepository;
        this.userRepository = userRepository;
    }

    // Crear un nuevo usuario
    public UserDTO createUser(UserDTO userDTO) {
        return customUserRepository.save(userDTO);
    }

    // Obtener usuario por ID
    public Optional<UserDTO> getUserById(Long id) {
        return customUserRepository.findDtoById(id);
    }

    // Obtener todos los usuarios como DTOs
    public List<UserDTO> getAllUsers() {
        return customUserRepository.findAllDtos();
    }

    // Actualizar un usuario
    public Optional<UserDTO> updateUser(Long id, UserDTO updatedDTO) {
        if (!id.equals(updatedDTO.getId())) {
            throw new IllegalArgumentException("ID in path doesn't match ID in DTO");
        }
        return Optional.of(customUserRepository.update(updatedDTO));
    }

    // Eliminar usuario por ID
    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Métodos adicionales específicos de negocio
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    /*
    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(userRepositoryExtensions::convertToDto);
    }
     */
}
