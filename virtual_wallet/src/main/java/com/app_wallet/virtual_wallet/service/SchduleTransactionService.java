package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.dto.ScheduledTransactionDTO;
import com.app_wallet.virtual_wallet.dto.TransactionDTO;
import com.app_wallet.virtual_wallet.utils.LinkedList;

public class SchduleTransactionService {

    private final LinkedList<ScheduledTransactionDTO> transactions = new LinkedList<>();

    public void createTransaction(ScheduledTransactionDTO scheduleTransactionDTO){
        transactions.add(scheduleTransactionDTO);
    }
    public void saveTransaction(SchduleTransactionService scheduleTransactionDTO){
    }
    
}
