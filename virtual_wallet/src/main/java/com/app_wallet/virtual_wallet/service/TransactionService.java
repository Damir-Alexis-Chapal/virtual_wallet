package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.dto.TransactionDTO;
import com.app_wallet.virtual_wallet.entity.TransactionEntity;
import com.app_wallet.virtual_wallet.mapper.TransactionMapper;
import com.app_wallet.virtual_wallet.repository.TransactionRepository;
import com.app_wallet.virtual_wallet.utils.LinkedList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final PointsService pointsService;

    public TransactionService(TransactionRepository transactionRepository,
                              PointsService pointsService) {
        this.transactionRepository = transactionRepository;
        this.pointsService = pointsService;
    }

    @Transactional
    public void saveTransaction(TransactionDTO dto, Long userId, Long accountOriginId) {

        TransactionEntity entity = TransactionMapper.toEntity(dto, userId, accountOriginId);
        transactionRepository.save(entity);
        pointsService.addPointsForTransaction(userId, dto.getType(), dto.getAmount());
    }

    public LinkedList<TransactionDTO> getRecentTransactions(Long userId) {
        List<TransactionEntity> entities = transactionRepository.findTop3ByUserIdOrderByDateDesc(userId);
        LinkedList<TransactionDTO> customList = new LinkedList<>();
        for (TransactionEntity entity : entities) {
            customList.addTail(TransactionMapper.toDTO(entity));
        }
        return customList;
    }

    public LinkedList<TransactionDTO> getAllTransactions(Long userId) {
        List<TransactionEntity> entities = transactionRepository.findByUserIdOrderByDateDesc(userId);
        LinkedList<TransactionDTO> customList = new LinkedList<>();
        for (TransactionEntity entity : entities) {
            customList.addTail(TransactionMapper.toDTO(entity));
        }
        return customList;
    }
}
