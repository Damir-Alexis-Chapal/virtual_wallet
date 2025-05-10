package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.dto.UserDTO;
import com.app_wallet.virtual_wallet.entity.User;
import com.app_wallet.virtual_wallet.repository.UserRepository;
import com.app_wallet.virtual_wallet.utils.LinkedList;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public UserDTO createUser(UserDTO userDTO){
        User user = convertToUserEntity(userDTO);
        userRepository.save(user);
        return convertToUserDTO(user);
    }
    public void saveUser(UserDTO user){
    }
    public UserDTO updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).orElse(null);
        if (user == null) {
            return null;
        }
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        userRepository.save(user);
        return convertToUserDTO(user);
    }

    public boolean deleteUser(UserDTO userDTO){
        User user = userRepository.findById(userDTO.getId()).orElse(null);
        if (user == null) {
            return false;
        }
        userRepository.delete(user);
        return true;
    }

    public UserDTO getUserByName(String name) {
        User user = userRepository.findByName(name);
        if (user == null) {
            return null;
        }
        return convertToUserDTO(user);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        return convertToUserDTO(user);
    }

    public UserDTO convertToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }

    public User convertToUserEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword("");
        return user;
    }

    public LinkedList<UserDTO> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        LinkedList<UserDTO> dtos = new LinkedList<>();
        for (User user : users) {
            dtos.add(convertToUserDTO(user));
        }
        return dtos;
    }

}
