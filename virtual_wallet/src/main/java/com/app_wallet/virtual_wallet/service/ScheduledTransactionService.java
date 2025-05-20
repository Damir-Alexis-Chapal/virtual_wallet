package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.dto.ScheduledTransactionDTO;
import com.app_wallet.virtual_wallet.dto.TransactionDTO;
import com.app_wallet.virtual_wallet.entity.ScheduledTransactionEntity;
import com.app_wallet.virtual_wallet.mapper.ScheduledTransactionMapper;
import com.app_wallet.virtual_wallet.repository.ScheduledTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

@Service
public class ScheduledTransactionService {

    private final ScheduledTransactionRepository repository;
    private final TransactionService transactionService;
    private final PriorityQueue<ScheduledTransactionEntity> transactionQueue;

    @Autowired
    public ScheduledTransactionService(ScheduledTransactionRepository repository, 
                                      TransactionService transactionService) {
        this.repository = repository;
        this.transactionService = transactionService;
        // Cola de prioridad ordenada por fecha programada
        this.transactionQueue = new PriorityQueue<>(
            Comparator.comparing(ScheduledTransactionEntity::getScheduledDate)
        );
    }

    @Transactional
    public ScheduledTransactionDTO scheduleTransaction(ScheduledTransactionDTO dto) {
        ScheduledTransactionEntity entity = ScheduledTransactionMapper.toEntity(dto);
        ScheduledTransactionEntity saved = repository.save(entity);
        
        // Agregar a la cola de prioridad
        transactionQueue.add(saved);
        
        return ScheduledTransactionMapper.toDTO(saved);
    }

    public List<ScheduledTransactionDTO> getUserScheduledTransactions(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(ScheduledTransactionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void cancelScheduledTransaction(Long id) {
        repository.findById(id).ifPresent(entity -> {
            entity.setActive(false);
            repository.save(entity);
        });
    }

    // Ejecutar cada minuto para procesar transacciones programadas
    @Scheduled(fixedRate = 60000)
    @Transactional
    public void processScheduledTransactions() {
        LocalDateTime now = LocalDateTime.now();
        List<ScheduledTransactionEntity> dueTransactions = repository.findDueTransactions(now);
        
        // Procesar transacciones vencidas
        for (ScheduledTransactionEntity scheduled : dueTransactions) {
            // Crear y ejecutar la transacción
            TransactionDTO transaction = new TransactionDTO();
            transaction.setAmount(scheduled.getAmount());
            transaction.setDescription(scheduled.getDescription());
            transaction.setType(scheduled.getType());
            transaction.setDestination(scheduled.getDestinationAccount());
            transaction.setCategory(scheduled.getCategory());
            transaction.setDate(LocalDateTime.now());
            
            transactionService.saveTransaction(transaction, scheduled.getUserId(), scheduled.getSourceAccountId());
            
            // Actualizar la próxima fecha programada según la frecuencia
            switch (scheduled.getFrequency()) {
                case "ONCE":
                    scheduled.setActive(false);
                    break;
                case "DAILY":
                    scheduled.setScheduledDate(scheduled.getScheduledDate().plusDays(1));
                    break;
                case "WEEKLY":
                    scheduled.setScheduledDate(scheduled.getScheduledDate().plusWeeks(1));
                    break;
                case "MONTHLY":
                    scheduled.setScheduledDate(scheduled.getScheduledDate().plusMonths(1));
                    break;
            }
            
            repository.save(scheduled);
        }
    }
}