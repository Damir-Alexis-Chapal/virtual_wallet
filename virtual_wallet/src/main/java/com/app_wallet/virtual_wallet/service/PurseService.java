package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.dto.AccountDTO;
import com.app_wallet.virtual_wallet.dto.PurseDTO;
import com.app_wallet.virtual_wallet.dto.TransactionDTO;
import com.app_wallet.virtual_wallet.utils.LinkedList;

import java.util.Iterator;

public class PurseService {
    private final LinkedList<PurseDTO> purses = new LinkedList<>();

    public void createPurse(PurseDTO purse){
        purses.add(purse);
    }
    public void deletePurse(String id){
        Iterator<PurseDTO> iterator = purses.iterator();
        while (iterator.hasNext()) {
            PurseDTO pur = iterator.next();
            if (pur.getId().equals(id)) {
                iterator.remove();  // Elimina el elemento actual
            }
        }
    }
    public PurseDTO updatePurse(PurseDTO purse){
        for (int i = 0; i < purses.size(); i++) {
            PurseDTO pur = purses.getValue(i);
            if (pur.getId().equals(purse.getId())) {
                purses.deleteByIndex(i);      // Elimina la cuenta antigua
                purses.add(purse);   // Agrega la cuenta nueva
                return purse;          // Devuelve la cuenta actualizada
            }
        }
        return null;
    }
    public void addMoneyToPurse(Float amount, Long purseId){
    }
    public void getMoneyFromPurse(Float amount, Long purseId){
    }
    public Long getPurseIdByName(String name){
        return null;
    }



}
