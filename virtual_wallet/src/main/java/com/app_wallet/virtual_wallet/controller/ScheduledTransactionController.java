package com.app_wallet.virtual_wallet.controller;

import com.app_wallet.virtual_wallet.dto.ScheduledTransactionDTO;
import com.app_wallet.virtual_wallet.entity.Category;
import com.app_wallet.virtual_wallet.service.ScheduledTransactionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.app_wallet.virtual_wallet.dto.UserDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/scheduled")
public class ScheduledTransactionController {

    private final ScheduledTransactionService service;

    @Autowired
    public ScheduledTransactionController(ScheduledTransactionService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<?> scheduleTransaction(
            @RequestParam Long sourceAccountId,
            @RequestParam String destinationAccount,
            @RequestParam BigDecimal amount,
            @RequestParam String description,
            @RequestParam String type,
            @RequestParam String category,
            @RequestParam String scheduledDate,
            @RequestParam String frequency,
            HttpSession session
    ) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        ScheduledTransactionDTO dto = new ScheduledTransactionDTO();
        dto.setUserId(user.getId());
        dto.setSourceAccountId(sourceAccountId);
        dto.setDestinationAccount(destinationAccount);
        dto.setAmount(amount);
        dto.setDescription(description);
        dto.setType(type);
        dto.setCategory(Category.valueOf(category));
        dto.setScheduledDate(LocalDateTime.parse(scheduledDate));
        dto.setFrequency(frequency);
        dto.setActive(true);

        ScheduledTransactionDTO saved = service.scheduleTransaction(dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getUserScheduledTransactions(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        List<ScheduledTransactionDTO> transactions = service.getUserScheduledTransactions(user.getId());
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelScheduledTransaction(@PathVariable Long id, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        service.cancelScheduledTransaction(id);
        return ResponseEntity.ok("Transaction cancelled successfully");
    }
    
    @GetMapping("/frequencies")
    public ResponseEntity<List<String>> getFrequencies() {
        List<String> frequencies = Arrays.asList("ONCE", "DAILY", "WEEKLY", "MONTHLY");
        return ResponseEntity.ok(frequencies);
    }
}