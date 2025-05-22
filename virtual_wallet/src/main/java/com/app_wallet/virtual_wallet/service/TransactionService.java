package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.dto.TransactionDTO;
import com.app_wallet.virtual_wallet.entity.TransactionEntity;
import com.app_wallet.virtual_wallet.entity.WalletConnectionEntity;
import com.app_wallet.virtual_wallet.mapper.TransactionMapper;
import com.app_wallet.virtual_wallet.repository.AccountRepository;
import com.app_wallet.virtual_wallet.repository.TransactionRepository;
import com.app_wallet.virtual_wallet.repository.WalletConnectionRepository;
import com.app_wallet.virtual_wallet.utils.LinkedList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final PointsService pointsService;
    private final WalletConnectionRepository walletConnectionRepository;
    private final WalletGraphService walletGraphService;
    private final AccountRepository accountRepo;

    public TransactionService(TransactionRepository transactionRepository,
                              PointsService pointsService,
                              WalletConnectionRepository walletConnectionRepository,
                              WalletGraphService walletGraphService,
                              AccountRepository accountRepo) {
        this.transactionRepository      = transactionRepository;
        this.pointsService              = pointsService;
        this.walletConnectionRepository = walletConnectionRepository;
        this.walletGraphService         = walletGraphService;
        this.accountRepo               = accountRepo;
    }

    @Transactional
    public void saveTransaction(TransactionDTO dto, Long userId, Long accountOriginId) {
        TransactionEntity entity = TransactionMapper.toEntity(dto, userId, accountOriginId);
        transactionRepository.save(entity);
        pointsService.addPointsForTransaction(userId, dto.getType(), dto.getAmount());
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
        List<TransactionEntity> sent =
                transactionRepository.findTop3ByUserIdOrderByDateDesc(userId);
        List<TransactionEntity> allTransfers = transactionRepository.findByType("TRANSFER");
        List<TransactionEntity> received =
                allTransfers.stream()
                        .filter(tx -> {
                            String dest = tx.getUserDestiny();
                            if (dest == null) return false;
                            try {
                                Long accNum = Long.parseLong(dest);
                                return accountRepo.findByAccountNumber(accNum)
                                        .map(a -> a.getUserId().equals(userId))
                                        .orElse(false);
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        })
                        .sorted(Comparator.comparing(TransactionEntity::getDate).reversed())
                        .limit(3)
                        .toList();
        List<TransactionEntity> combined =
                Stream.concat(sent.stream(), received.stream())
                        .sorted(Comparator.comparing(TransactionEntity::getDate).reversed())
                        .limit(3)
                        .toList();

        LinkedList<TransactionDTO> list = new LinkedList<>();
        for (TransactionEntity e : combined) {
            list.addTail(TransactionMapper.toDTO(e));
        }
        return list;
    }

    public LinkedList<TransactionDTO> getAllTransactions(Long userId) {
        List<TransactionEntity> sent = transactionRepository.findByUserIdOrderByDateDesc(userId);
        List<TransactionEntity> allTransfers = transactionRepository.findByType("TRANSFER");
        List<TransactionEntity> received = allTransfers.stream()
                .filter(tx -> {
                    String dest = tx.getUserDestiny();
                    if (dest == null) return false;
                    try {
                        Long accNum = Long.parseLong(dest);
                        return accountRepo.findByAccountNumber(accNum)
                                .map(ae -> ae.getUserId().equals(userId))
                                .orElse(false);
                    } catch (NumberFormatException e) {
                        return false;
                    }
                })
                .sorted(Comparator.comparing(TransactionEntity::getDate).reversed())
                .toList();
        List<TransactionEntity> combined = Stream
                .concat(sent.stream(), received.stream())
                .sorted(Comparator.comparing(TransactionEntity::getDate).reversed())
                .toList();
        LinkedList<TransactionDTO> list = new LinkedList<>();
        for (TransactionEntity e : combined) {
            list.addTail(TransactionMapper.toDTO(e));
        }
        return list;
    }
}
