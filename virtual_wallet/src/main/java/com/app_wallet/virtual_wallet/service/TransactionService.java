package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.dto.TransactionDTO;
import com.app_wallet.virtual_wallet.mapper.TransactionMapper;
import com.app_wallet.virtual_wallet.repository.TransactionRepository;
import com.app_wallet.virtual_wallet.utils.LinkedList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository repo;

    public TransactionService(TransactionRepository repo) {
        this.repo = repo;
    }

    public void createTransaction(TransactionDTO transactionDTO) {
        // lógica para crear transacción
    }

    public void saveTransaction(TransactionDTO transactionDTO) {
        // lógica para guardar transacción
    }

    public LinkedList<TransactionDTO> getRecentTransactions(Long userId) {
        List<TransactionDTO> dtoList = repo.findTop3ByUserIdOrderByDateDesc(userId)
                .stream()
                .map(TransactionMapper::toDTO)
                .toList();

        return LinkedList.fromJavaList(dtoList);
    }

    public LinkedList<TransactionDTO> getAllTransactions(Long userId) {
        List<TransactionDTO> dtoList = repo.findByUserIdOrderByDateDesc(userId)
                .stream()
                .map(TransactionMapper::toDTO)
                .toList();

        return LinkedList.fromJavaList(dtoList);
    }
}
