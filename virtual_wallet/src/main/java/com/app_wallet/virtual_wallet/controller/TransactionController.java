package com.app_wallet.virtual_wallet.controller;

import com.app_wallet.virtual_wallet.dto.TransactionDTO;
import com.app_wallet.virtual_wallet.dto.UserDTO;
import com.app_wallet.virtual_wallet.entity.AccountEntity;
import com.app_wallet.virtual_wallet.model.Category;
import com.app_wallet.virtual_wallet.repository.AccountRepository;
import com.app_wallet.virtual_wallet.service.AccountService;
import com.app_wallet.virtual_wallet.service.TransactionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final AccountRepository     accountRepository;
    @Autowired
    public TransactionController(TransactionService transactionService,  AccountRepository accountRepository) {
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
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

        AccountEntity destinationAccount = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Destination account not found"));

        AccountEntity originAccount = accountRepository.findById(accountOriginId).orElse(null);
        if (originAccount == null) {
            return ResponseEntity.status(404).body("Origin account not found");
        }

        if (originAccount.getBalance().compareTo(amount) < 0) {
            return ResponseEntity.status(400).body("Insufficient funds");
        }

        originAccount.setBalance(originAccount.getBalance().subtract(amount));
        destinationAccount.setBalance(destinationAccount.getBalance().add(amount));


        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);

        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(amount);
        dto.setDescription(description);
        dto.setType("TRANSFER");
        dto.setDate(LocalDateTime.now());
        dto.setDestination(String.valueOf(accountNumber));
        dto.setCategory(Category.valueOf(category));


        transactionService.saveTransaction(dto, user.getId(), accountOriginId);

        return ResponseEntity.ok("Transaction saved successfully.");
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
        AccountEntity destinationAccount = accountRepository.findByAccountNumber(accountDestiny)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Destination account not found"));

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

        return ResponseEntity.ok("Transaction saved successfully.");


    }


}
