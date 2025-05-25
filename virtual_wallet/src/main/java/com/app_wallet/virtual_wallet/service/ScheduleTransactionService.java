package com.app_wallet.virtual_wallet.service;

import com.app_wallet.virtual_wallet.dto.TransactionDTO;
import com.app_wallet.virtual_wallet.entity.AccountEntity;
import com.app_wallet.virtual_wallet.entity.ScheduledTransactionEntity;
import com.app_wallet.virtual_wallet.entity.UserEntity;
import com.app_wallet.virtual_wallet.repository.AccountRepository;
import com.app_wallet.virtual_wallet.repository.ScheduledTransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    @Scheduled(fixedRate = 60000) // cada minuto
    @Transactional
    public void processScheduledTransactions() {
        LocalDateTime now = LocalDateTime.now();
        List<ScheduledTransactionEntity> pending = repository.findByExecutedFalseAndScheduledDatetimeBefore(now);

        for (ScheduledTransactionEntity tx : pending) {

            AccountEntity destinationAccount = accountRepository.findByAccountNumber(Long.valueOf(tx.getUserDestiny())).orElse(null);

            AccountEntity originAccount = accountRepository.findById(tx.getAccountOrigin()).orElse(null);

            originAccount.setBalance(originAccount.getBalance().subtract(tx.getAmount()));
            destinationAccount.setBalance(destinationAccount.getBalance().add(tx.getAmount()));

            UserEntity destinationUser = destinationAccount.getUser();
            String destinationEmail = destinationUser.getEmail();

            accountRepository.save(originAccount);
            accountRepository.save(destinationAccount);

            tx.setExecuted(true);
            repository.save(tx);

            TransactionDTO dto = new TransactionDTO();
            dto.setDestination(tx.getUserDestiny());
            dto.setAmount(tx.getAmount());
            dto.setCategory(tx.getCategory());
            dto.setDescription(tx.getDescription());
            dto.setType("SCHEDULED");
            dto.setDate(LocalDateTime.now());



            transactionService.saveTransaction(dto, tx.getUserId(), tx.getAccountOrigin());

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

        }
    }

}

