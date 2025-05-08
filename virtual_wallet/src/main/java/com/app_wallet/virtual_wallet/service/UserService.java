package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.dto.AccountDTO;
import com.app_wallet.virtual_wallet.dto.UserDTO;
import com.app_wallet.virtual_wallet.utils.LinkedList;
import com.app_wallet.virtual_wallet.utils.Node;

import java.util.Iterator;

public class UserService {

    private final LinkedList<UserDTO> users = new LinkedList<>();

    public void createUser(UserDTO user){
        users.add(user);
    }
    public void saveUser(UserDTO user){
        if (getUserById(user.getId()) != null) {
            updateUser(user);
        } else {
            createUser(user);
        }
    }
    public void updateUser(UserDTO user){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.deleteByIndex(i);  // Reemplaza el usuario en la misma posición
                users.add(user);
                break;               // Sale del bucle una vez reemplazado
            }
        }
    }
    public void deleteUser(UserDTO user){
        Iterator<UserDTO> iterator = users.iterator();
        while (iterator.hasNext()) {
            UserDTO use = iterator.next();
            if (use.getId().equals(id)) {
                iterator.remove();  // Elimina el elemento actual
            }
        }
    }
    public UserDTO getUserById(Long id){
        Node<UserDTO> current = users.getHead();
        while (current != null) {
            UserDTO user = current.getData();
            if (user.getID().equals(id)) {
                return user;  // Se encontró: devuelve el objeto
            }
            current = current.getNext();  // Avanza al siguiente nodo
        }
        return null;          // No se encontró ninguna coincidencia
    }
    public UserDTO getUserByName(String name){
        Node<UserDTO> current = users.getHead();
        while (current != null) {
            UserDTO user = current.getData();
            if (user.getName().equals(name)) {
                return user;  // Se encontró: devuelve el objeto
            }
            current = current.getNext();  // Avanza al siguiente nodo
        }
        return null;          // No se encontró ninguna coincidencia
    }
}
