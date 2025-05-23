package com.app_wallet.virtual_wallet.controller;

import com.app_wallet.virtual_wallet.dto.AccountDTO;
import com.app_wallet.virtual_wallet.dto.UserDTO;
import com.app_wallet.virtual_wallet.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Controller
@RequestMapping("/Accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/add")
    public ResponseEntity<String> addAccount(@RequestParam Long accountNumber, HttpSession session){

        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        AccountDTO dto = new AccountDTO();
        dto.setAccountNumber(accountNumber);
        dto.setUserId(user.getId());
        dto.setBalance(BigDecimal.valueOf(ThreadLocalRandom.current().nextLong(1_000_000L, 20_000_001L)));

        accountService.saveAccount(dto);

        return ResponseEntity.ok("Account created successfully");
    }

}
