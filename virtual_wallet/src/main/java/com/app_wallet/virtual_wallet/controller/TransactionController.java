package com.app_wallet.virtual_wallet.controller;

import com.app_wallet.virtual_wallet.dto.TransactionDTO;
import com.app_wallet.virtual_wallet.dto.UserDTO;
import com.app_wallet.virtual_wallet.entity.TransactionEntity;
import com.app_wallet.virtual_wallet.mapper.TransactionMapper;
import com.app_wallet.virtual_wallet.model.TransactionType;
import com.app_wallet.virtual_wallet.repository.TransactionRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;

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
        Long userId = user.getId();  // <- Aquí lo obtenés correctamente


        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(amount);
        dto.setDescription(description);
        dto.setType("TRANSFER");
        dto.setDate(LocalDateTime.now());
        dto.setDestination(email);

        Long accountOriginId = 1L;

        TransactionEntity entity = TransactionMapper.toEntity(dto, userId, accountOriginId);
        transactionRepository.save(entity);

        return ResponseEntity.ok("Transaction saved successfully.");
    }
}
