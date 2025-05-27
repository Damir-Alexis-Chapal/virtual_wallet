package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.dto.TransactionDTO;
import com.app_wallet.virtual_wallet.entity.AccountEntity;
import com.app_wallet.virtual_wallet.entity.ScheduledTransactionEntity;
import com.app_wallet.virtual_wallet.entity.UserEntity;
import com.app_wallet.virtual_wallet.repository.AccountRepository;
import com.app_wallet.virtual_wallet.repository.ScheduledTransactionRepository;
import com.app_wallet.virtual_wallet.utils.ScheduledQueue;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class ScheduleTransactionService {

    @Autowired
    private ScheduledTransactionRepository repository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    private final ScheduledQueue pq = new ScheduledQueue();

    @Scheduled(fixedRate = 60000) // cada minuto

    @Transactional
    public void processScheduledTransactions() {
        LocalDateTime now = LocalDateTime.now();

        //limpiar cola antes de cargarla
        pq.clear();

        // ② Carga todas las pendientes en la cola
        List<ScheduledTransactionEntity> pending =
                repository.findByExecutedFalseAndScheduledDatetimeBefore(now);
        pending.forEach(pq::enqueue);

        // ③ Procesa mientras la cabeza esté lista para ejecutarse
        while (!pq.isEmpty() && pq.peek().getScheduledDatetime().isBefore(now.plusSeconds(1))) {
            ScheduledTransactionEntity tx = pq.dequeue();

            // ——— Mismo flujo de débito/crédito que tenías ———
            AccountEntity origin      = accountRepository.findById(tx.getAccountOrigin()).orElseThrow();
            AccountEntity destination = accountRepository
                    .findByAccountNumber(Long.valueOf(tx.getUserDestiny()))
                    .orElseThrow();

            origin.setBalance(origin.getBalance().subtract(tx.getAmount()));
            destination.setBalance(destination.getBalance().add(tx.getAmount()));
            accountRepository.save(origin);
            accountRepository.save(destination);

            // ——— Marca como ejecutada y guarda ———
            tx.setExecuted(true);
            repository.save(tx);

            // ——— Guarda en TransactionService ———
            TransactionDTO dto = new TransactionDTO();
            dto.setType("SCHEDULED");
            dto.setAmount(tx.getAmount());
            dto.setDescription(tx.getDescription());
            dto.setDestination(tx.getUserDestiny());
            dto.setCategory(tx.getCategory());
            dto.setDate(LocalDateTime.now());
            transactionService.saveTransaction(dto, tx.getUserId(), tx.getAccountOrigin());

            /**
             * SI QUIEREN PROBAR LOS MESAJES QUITEN ESTE COMENTARIO, PERO NO ABUSEN DE ESO PORQUE SINO SE NOS ACABA LA PRUEBA GRATIS
             *
             *             !!OJO QUE SOLO FUNCIONA CON EL NUMERO DE CRISTHIAN Y EL MIO!!!
             *             SI METEN OTRO NO SIRVE
             *
             * UserEntity userDest = destination.getUser();
             *             String destinationEmail = userDest.getEmail();
             *             UserEntity userOrig = origin.getUser();
             *             String originEmail = userOrig.getEmail();
             *
             *             // notifications to destiny
             *             notificationService.sendEmail(destinationEmail, "BLINKER:",
             *                     userOrig.getName() + " sent you $" + tx.getAmount() + " (" + tx.getDescription() + ")");
             *             notificationService.sendSMS(userDest.getPhone(),
             *                     "BLINKER: " + userOrig.getName() + " sent you $" + tx.getAmount());
             *
             *             // notifications to origin
             *             notificationService.sendEmail(originEmail, "BLINKER:",
             *                     userOrig.getName() + ", you sent $" + tx.getAmount() + " to " + userDest.getName());
             *             notificationService.sendSMS(userOrig.getPhone(),
             *                     "BLINKER: You sent $" + tx.getAmount() + " to " + userDest.getName());
             */
        }
    }
}

/*
            SI QUIEREN PROBAR LOS MESAJES QUITEN ESTE COMENTARIO, PERO NO ABUSEN DE ESO PORQUE SINO SE NOS ACABA LA PRUEBA GRATIS

            !!OJO QUE SOLO FUNCIONA CON EL NUMERO DE CRISTHIAN Y EL MIO!!!
            SI METEN OTRO NO SIRVE



                //notifications
            //to destiny
            notificationService.sendEmail(destinationEmail, "BLINKER: ", user.getName() + " send you "+ "$"+amount + " to " + description);
            notificationService.sendSMS(destinationUser.getPhone(), "BLINKER: "+ user.getName() + " send you " + "$"+amount+ " to " + description);
            //to origin
            notificationService.sendEmail(user.getEmail(), "BLINKER: ", user.getName() + ", you send "+ "$"+amount + " to " + destinationUser.getName());
            notificationService.sendSMS(user.getPhone(), "BLINKER: "+ user.getName() + ", you send " + "$"+amount+ " to " + destinationUser.getName());

         */