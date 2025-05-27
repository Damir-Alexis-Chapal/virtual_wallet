package com.app_wallet.virtual_wallet.controller;

import com.app_wallet.virtual_wallet.dto.TransactionDTO;
import com.app_wallet.virtual_wallet.dto.UserDTO;
import com.app_wallet.virtual_wallet.entity.AccountEntity;
import com.app_wallet.virtual_wallet.entity.ScheduledTransactionEntity;
import com.app_wallet.virtual_wallet.entity.UserEntity;
import com.app_wallet.virtual_wallet.model.Category;
import com.app_wallet.virtual_wallet.repository.AccountRepository;
import com.app_wallet.virtual_wallet.repository.BenefitRepository;
import com.app_wallet.virtual_wallet.repository.ScheduledTransactionRepository;
import com.app_wallet.virtual_wallet.service.AccountService;
import com.app_wallet.virtual_wallet.service.BenefitService;
import com.app_wallet.virtual_wallet.service.NotificationService;
import com.app_wallet.virtual_wallet.service.TransactionService;
import com.app_wallet.virtual_wallet.utils.MyHashMap;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountRepository accountRepository;
    private final BenefitService benefitService;
    private final NotificationService notificationService;
    private static final BigDecimal TRANSFER_FEE = BigDecimal.valueOf(4500);
    private static final BigDecimal WITHDRAWAL_FEE = BigDecimal.valueOf(6000);

    @Autowired
    private ScheduledTransactionRepository scheduledTransactionRepository;

    @Autowired
    public TransactionController(TransactionService transactionService,  AccountRepository accountRepository,
                                 NotificationService notificationService,  ScheduledTransactionRepository scheduledTransactionRepository,
                                 BenefitService benefitService) {
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
        this.notificationService = notificationService;
        this.scheduledTransactionRepository = scheduledTransactionRepository;
        this.benefitService = benefitService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendTransaction(
            @RequestParam Long accountNumber,
            @RequestParam BigDecimal amount,
            @RequestParam String description,
            @RequestParam Long accountOriginId,
            @RequestParam String category,
            HttpSession session
    ) {


        UserDTO user = (UserDTO) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        AccountEntity destinationAccount = accountRepository.findByAccountNumber(accountNumber).orElse(null);

        if (destinationAccount == null) {
            return ResponseEntity.status(404).body("Destination account not found");
        }

        AccountEntity originAccount = accountRepository.findById(accountOriginId).orElse(null);
        if (originAccount == null) {
            return ResponseEntity.status(404).body("Origin account not found");
        }

        Map<String, Object> result = benefitService.applyBenefits(user.getId(), amount, TRANSFER_FEE, "TRANSFER");
        BigDecimal amountWithFee = (BigDecimal) result.get("total");
        String benefitTitle = (String) result.get("benefit");

        if (originAccount.getBalance().compareTo(amountWithFee) < 0) {
            return ResponseEntity.status(400).body("Insufficient funds");
        }


        originAccount.setBalance(originAccount.getBalance().subtract(amountWithFee));
        destinationAccount.setBalance(destinationAccount.getBalance().add(amount));

        UserEntity destinationUser = destinationAccount.getUser();
        String destinationEmail = destinationUser.getEmail();

        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);

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

        TransactionDTO dto = new TransactionDTO();

        dto.setAmount(amount);
        dto.setDescription(description);
        dto.setType("TRANSFER");
        dto.setDate(LocalDateTime.now());
        dto.setDestination(String.valueOf(accountNumber));
        dto.setCategory(Category.valueOf(category));

        if (benefitTitle != null) {
            dto.setBenefitTitle(benefitTitle);
        }else{
            dto.setBenefitTitle("NONE");
        }

        transactionService.saveTransaction(dto, user.getId(), accountOriginId);

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Transaction completed successfully");
        return ResponseEntity.ok(response);

    }

    @PostMapping("/withdraw")
    @ResponseBody
    public ResponseEntity<String> withdraw(
            HttpSession session,
            @RequestParam Long accountOriginId,
            @RequestParam BigDecimal amount
    ) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        AccountEntity acct = accountRepository.findById(accountOriginId).orElse(null);
        if (acct == null) {
            return ResponseEntity.status(404).body("Account not found");
        }


        Map<String, Object> benefitResult = benefitService.applyBenefits(user.getId(), amount, TRANSFER_FEE, "WITHDRAWAL");
        BigDecimal amountWithFee = (BigDecimal) benefitResult.get("total");
        String benefitTitle = (String) benefitResult.get("benefit");

        if (acct.getBalance().compareTo(amountWithFee) < 0) {
            return ResponseEntity.status(400).body("Insufficient funds");
        }


        acct.setBalance(acct.getBalance().subtract(amountWithFee));
        accountRepository.save(acct);


        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(amount);
        dto.setDescription("WITHDRAWAL");
        dto.setType("WITHDRAWAL");
        dto.setDate(LocalDateTime.now());
        dto.setDestination(String.valueOf(acct.getAccountNumber()));
        dto.setCategory(Category.OTHER);


        if (benefitTitle != null) {
            dto.setBenefitTitle(benefitTitle);
        }else{
            dto.setBenefitTitle("NONE");
        }

        transactionService.saveTransaction(dto, user.getId(), acct.getId());

        return ResponseEntity.ok("Withdrawal completed successfully");
    }



    @GetMapping("/recent")
    @ResponseBody
    public List<TransactionDTO> recent(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        return (user == null)
                ? Collections.emptyList()
                : transactionService.getRecentTransactions(user.getId()).toJavaList();
    }

    @GetMapping("/all")
    @ResponseBody
    public List<TransactionDTO> all(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        return (user == null)
                ? Collections.emptyList()
                : transactionService.getAllTransactions(user.getId()).toJavaList();
    }


    @PostMapping("/deposit")
    @ResponseBody
    public ResponseEntity<?> deposit(HttpSession session,
                                     @RequestParam BigDecimal depositAmount,
                                     @RequestParam Long accountOriginNumber,
                                     @RequestParam Long accountDestiny
    ) {

        String category = "OTHER";
        String description = "DEPOSIT";
        String type = "DEPOSIT";

        UserDTO user = (UserDTO) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        System.out.println("/n/n cuenta destino "+accountDestiny.toString()+"/n/n/n");
        AccountEntity destinationAccount = accountRepository.findByAccountNumber(accountDestiny).orElse(null);


        if (destinationAccount == null) {
            return ResponseEntity.status(404).body("Destination account not found");
        }
        destinationAccount.setBalance(destinationAccount.getBalance().add(depositAmount));

        accountRepository.save(destinationAccount);

        TransactionDTO dto = new TransactionDTO();

        dto.setAmount(depositAmount);
        dto.setDescription(description);
        dto.setType(type);
        dto.setDate(LocalDateTime.now());
        dto.setDestination(String.valueOf(accountDestiny));
        dto.setCategory(Category.valueOf(category));


        transactionService.saveTransaction(dto, user.getId(), accountOriginNumber);


        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Transaction saved successfully");
        return ResponseEntity.ok(response);
    }



    @PostMapping("/schedule")
    @ResponseBody
    public ResponseEntity<?> scheduleTransaction(
            @RequestParam Long accountNumber,
            @RequestParam BigDecimal amount,
            @RequestParam String description,
            @RequestParam Long accountOriginId,
            @RequestParam String category,
            @RequestParam("scheduledDatetime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime scheduledDatetime,
            HttpSession session) {

        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        AccountEntity destinationAccount = accountRepository.findByAccountNumber(accountNumber).orElse(null);
        if (destinationAccount == null) {
            return ResponseEntity.status(404).body("Destination account not found");
        }

        AccountEntity originAccount = accountRepository.findById(accountOriginId).orElse(null);
        if (originAccount == null) {
            return ResponseEntity.status(404).body("Origin account not found");
        }

        if (originAccount.getBalance().compareTo(amount) < 0) {
            return ResponseEntity.status(400).body("Insufficient funds");
        }



        ScheduledTransactionEntity tx = new ScheduledTransactionEntity();
        Category categoryEnum = Category.valueOf(category);

        tx.setUserId(user.getId());
        tx.setUserDestiny(accountNumber.toString());
        tx.setAccountOrigin(accountOriginId);
        tx.setAmount(amount);
        tx.setDescription(description);
        tx.setCategory(categoryEnum);
        tx.setType("TRANSFER");
        tx.setScheduledDatetime(scheduledDatetime);
        tx.setExecuted(false); // MUY IMPORTANTE para distinguir que aún no se ha ejecutado

        try {
            scheduledTransactionRepository.save(tx);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error saving scheduled transaction: " + e.getMessage());
        }

        Map<String, String> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Transaction scheduled successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/scheduled/all")
    @ResponseBody
    public List<ScheduledTransactionEntity> getAllScheduled(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) return Collections.emptyList();
        return scheduledTransactionRepository.findByUserId(user.getId());
    }

    @GetMapping("/scheduled/executed")
    @ResponseBody
    public List<TransactionDTO> getScheduledExecuted(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) return Collections.emptyList();


        com.app_wallet.virtual_wallet.utils.LinkedList<TransactionDTO> ll =
                transactionService.getAllTransactions(user.getId());

        List<TransactionDTO> all = new ArrayList<>();
        for (TransactionDTO dto : ll) {
            all.add(dto);
        }


        return all.stream()
                .filter(tx -> "SCHEDULED".equals(tx.getType())
                        && (tx.getOrigin().equals(user.getId())
                        || accountRepository.findByAccountNumber(Long.parseLong(tx.getDestination()))
                        .map(a -> a.getUserId().equals(user.getId()))
                        .orElse(false)))
                .collect(Collectors.toList());
    }

    @PostMapping("/revert")
    public ResponseEntity<?> revertTransaction(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }


        TransactionDTO lastTransaction = transactionService.getMostRecentTransaction(user.getId());

        if (lastTransaction == null) {
            return ResponseEntity.status(404).body("No transactions to revert");
        }

        try {
            BigDecimal amount = lastTransaction.getAmount();
            Long destiny = Long.valueOf(lastTransaction.getDestination());
            AccountEntity originAccount = accountRepository.findById(lastTransaction.getOrigin()).orElse(null);
            if (originAccount == null) {
                return ResponseEntity.status(404).body("Origin account not found");
            }
            AccountEntity destinationAccount = accountRepository.findByAccountNumber(destiny).orElse(null);
            if (destinationAccount == null) {
                return ResponseEntity.status(404).body("Destination account not found");
            }

            originAccount.setBalance(originAccount.getBalance().add(amount));
            destinationAccount.setBalance(destinationAccount.getBalance().subtract(amount));

            accountRepository.save(originAccount);
            accountRepository.save(destinationAccount);

            transactionService.deleteTransactionById(lastTransaction.getId());
            return ResponseEntity.ok("Transaction reverted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error reverting transaction");
        }
    }


}
