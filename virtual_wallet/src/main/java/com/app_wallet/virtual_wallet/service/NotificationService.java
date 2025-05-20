package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.dto.NotificationDTO;
import com.app_wallet.virtual_wallet.entity.AccountEntity;
import com.app_wallet.virtual_wallet.entity.NotificationEntity;
import com.app_wallet.virtual_wallet.entity.ScheduledTransactionEntity;
import com.app_wallet.virtual_wallet.mapper.NotificationMapper;
import com.app_wallet.virtual_wallet.repository.AccountRepository;
import com.app_wallet.virtual_wallet.repository.NotificationRepository;
import com.app_wallet.virtual_wallet.repository.ScheduledTransactionRepository;
import com.app_wallet.virtual_wallet.structure.CircularList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository repository;
    private final AccountRepository accountRepository;
    private final ScheduledTransactionRepository scheduledRepository;
    private final Map<Long, CircularList<NotificationEntity>> userNotifications = new HashMap<>();

    @Autowired
    public NotificationService(NotificationRepository repository, 
                              AccountRepository accountRepository,
                              ScheduledTransactionRepository scheduledRepository) {
        this.repository = repository;
        this.accountRepository = accountRepository;
        this.scheduledRepository = scheduledRepository;
    }

    @Transactional
    public NotificationDTO createNotification(Long userId, String message, String type) {
        NotificationEntity entity = new NotificationEntity();
        entity.setUserId(userId);
        entity.setMessage(message);
        entity.setType(type);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setRead(false);
        
        NotificationEntity saved = repository.save(entity);
        
        // Agregar a la lista circular del usuario
        userNotifications.computeIfAbsent(userId, k -> new CircularList<>()).add(saved);
        
        return NotificationMapper.toDTO(saved);
    }

    public List<NotificationDTO> getUserNotifications(Long userId) {
        // Cargar notificaciones si no están en memoria
        if (!userNotifications.containsKey(userId)) {
            CircularList<NotificationEntity> list = new CircularList<>();
            repository.findByUserIdOrderByCreatedAtDesc(userId).forEach(list::add);
            userNotifications.put(userId, list);
        }
        
        // Convertir a DTO
        CircularList<NotificationEntity> notifications = userNotifications.get(userId);
        List<NotificationDTO> dtos = new java.util.ArrayList<>();
        for (NotificationEntity entity : notifications) {
            dtos.add(NotificationMapper.toDTO(entity));
        }
        return dtos;
    }

    @Transactional
    public void markAsRead(Long notificationId) {
        repository.findById(notificationId).ifPresent(entity -> {
            entity.setRead(true);
            repository.save(entity);
            
            // Actualizar en la lista circular
            Long userId = entity.getUserId();
            if (userNotifications.containsKey(userId)) {
                CircularList<NotificationEntity> list = userNotifications.get(userId);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getId().equals(notificationId)) {
                        list.get(i).setRead(true);
                        break;
                    }
                }
            }
        });
    }

    public long getUnreadCount(Long userId) {
        return repository.countByUserIdAndRead(userId, false);
    }

    // Verificar saldos bajos cada hora
    @Scheduled(fixedRate = 3600000)
    @Transactional
    public void checkLowBalances() {
        BigDecimal threshold = new BigDecimal("100.00");
        List<AccountEntity> lowBalanceAccounts = accountRepository.findAll().stream()
                .filter(account -> account.getBalance().compareTo(threshold) < 0)
                .collect(Collectors.toList());
        
        for (AccountEntity account : lowBalanceAccounts) {
            createNotification(
                account.getUserId(),
                "Low balance alert: Your account #" + account.getAccountNumber() + 
                " has a balance of $" + account.getBalance(),
                "WARNING"
            );
        }
    }

    // Recordatorios de transacciones programadas
    @Scheduled(fixedRate = 86400000) // Diario
    @Transactional
    public void sendScheduledTransactionReminders() {
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        LocalDateTime dayAfter = tomorrow.plusDays(1);
        
        List<ScheduledTransactionEntity> upcomingTransactions = scheduledRepository.findAll().stream()
                .filter(tx -> tx.isActive() && 
                             tx.getScheduledDate().isAfter(tomorrow) && 
                             tx.getScheduledDate().isBefore(dayAfter))
                .collect(Collectors.toList());
        
        for (ScheduledTransactionEntity transaction : upcomingTransactions) {
            createNotification(
                transaction.getUserId(),
                "Reminder: You have a scheduled " + transaction.getType().toLowerCase() + 
                " for $" + transaction.getAmount() + " tomorrow",
                "INFO"
            );
        }
    }
}