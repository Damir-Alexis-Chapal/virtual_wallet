package com.app_wallet.virtual_wallet.service;


import com.app_wallet.virtual_wallet.dto.AccountDTO;
import com.app_wallet.virtual_wallet.utils.LinkedList;
import com.app_wallet.virtual_wallet.utils.Node;

import java.util.Iterator;


public class AccountService {

    private final LinkedList<AccountDTO>accounts = new LinkedList<>();

    public void createAccount(AccountDTO account){
        accounts.add(account);
    }
    public AccountDTO getAccountByName(String name){
        Node<AccountDTO> current = accounts.getHead();
        while (current != null) {
            AccountDTO account = current.getData();
            if (account.getName().equalsIgnoreCase(name)) {
                return account;  // Se encontró: devuelve el objeto
            }
            current = current.getNext();  // Avanza al siguiente nodo
        }

        return null;  // No se encontró ninguna coincidencia
    }

    public AccountDTO getAccountById(Long id){
        Node<AccountDTO> current = accounts.getHead();
        while (current != null) {
            AccountDTO account = current.getData();
            if (account.getID().equals(id)) {
                return account;  // Se encontró: devuelve el objeto
            }
            current = current.getNext();  // Avanza al siguiente nodo
        }
        return null;          // No se encontró ninguna coincidencia
    }

    public AccountDTO updateAccount(AccountDTO account){
        for (int i = 0; i < accounts.getSize(); i++) {
            AccountDTO acc = accounts.getValue(i);
            if (acc.getId().equals(account.getId())) {
                accounts.deleteByIndex(i);      // Elimina la cuenta antigua
                accounts.add(account);   // Agrega la cuenta nueva
                return account;          // Devuelve la cuenta actualizada
            }
        }
        return null;
    }

    public void deleteAccount(Long id) {
        Iterator<AccountDTO> iterator = accounts.iterator();
        while (iterator.hasNext()) {
            AccountDTO acc = iterator.next();
            if (acc.getId().equals(id)) {
                iterator.remove();  // Elimina el elemento actual
            }
        }
    }

    public void saveAccount(AccountDTO account){
            if (getAccountById(account.getId()) != null) {
                updateAccount(account);  // Si existe actualizar la cuenta
            } else {
                createAccount(account);
            }
        }
    }


}
