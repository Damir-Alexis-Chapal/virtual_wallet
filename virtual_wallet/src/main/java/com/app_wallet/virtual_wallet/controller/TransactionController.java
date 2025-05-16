package com.app_wallet.virtual_wallet.controller;

import com.app_wallet.virtual_wallet.dto.TransactionDTO;
import com.app_wallet.virtual_wallet.dto.UserDTO;
import com.app_wallet.virtual_wallet.service.TransactionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendTransaction(
            @RequestParam String email,
            @RequestParam BigDecimal amount,
            @RequestParam String description,
            HttpSession session
    ) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(amount);
        dto.setDescription(description);
        dto.setType("TRANSFER");
        dto.setDate(LocalDateTime.now());
        dto.setDestination(email);

        Long accountOriginId = 1L;

        transactionService.saveTransaction(dto, user.getId(), accountOriginId); // <- Esto va al service

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
}
