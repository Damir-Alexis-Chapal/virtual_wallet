package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.dto.TransactionDTO;
import com.app_wallet.virtual_wallet.entity.TransactionEntity;
import com.app_wallet.virtual_wallet.entity.WalletConnectionEntity;
import com.app_wallet.virtual_wallet.mapper.TransactionMapper;
import com.app_wallet.virtual_wallet.repository.TransactionRepository;
import com.app_wallet.virtual_wallet.repository.WalletConnectionRepository;
import com.app_wallet.virtual_wallet.utils.LinkedList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final PointsService pointsService;
    private final WalletConnectionRepository walletConnectionRepository;
    private final WalletGraphService walletGraphService;

    public TransactionService(TransactionRepository transactionRepository,
                              PointsService pointsService,
                              WalletConnectionRepository walletConnectionRepository,
                              WalletGraphService walletGraphService) {
        this.transactionRepository      = transactionRepository;
        this.pointsService              = pointsService;
        this.walletConnectionRepository = walletConnectionRepository;
        this.walletGraphService         = walletGraphService;
    }

    @Transactional
    public void saveTransaction(TransactionDTO dto, Long userId, Long accountOriginId) {
        // 1) Persisto la transacción en la BD
        TransactionEntity entity = TransactionMapper.toEntity(dto, userId, accountOriginId);
        transactionRepository.save(entity);

        // 2) Registro puntos
        pointsService.addPointsForTransaction(userId, dto.getType(), dto.getAmount());

        // 3) Si es transferencia entre monederos propios, guardo relación en el grafo
        if ("TRANSFER".equalsIgnoreCase(dto.getType())) {
            Long sourceWalletId = accountOriginId;
            Long targetWalletId;
            try {
                targetWalletId = Long.parseLong(dto.getDestination());
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Destino inválido para transferencia: " + dto.getDestination());
            }

            WalletConnectionEntity conn = new WalletConnectionEntity();
            conn.setUserId(userId);
            conn.setSourceWalletId(sourceWalletId);
            conn.setTargetWalletId(targetWalletId);

            walletConnectionRepository.save(conn);
            walletGraphService.buildGraph(userId);
        }
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
