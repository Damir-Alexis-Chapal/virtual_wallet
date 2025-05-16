package com.app_wallet.virtual_wallet.controller;

import com.app_wallet.virtual_wallet.dto.TransactionDTO;
import com.app_wallet.virtual_wallet.dto.UserDTO;
import com.app_wallet.virtual_wallet.service.TransactionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final HttpSession session;

    @Autowired
    public TransactionController(TransactionService transactionService, HttpSession session) {
        this.transactionService = transactionService;
        this.session = session;
    }
    @GetMapping("/recent")
    @ResponseBody
    public List<TransactionDTO> recent() {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return Collections.emptyList();
        }
        return transactionService
                .getRecentTransactions(user.getId())
                .toJavaList();
    }

    @GetMapping("/all")
    @ResponseBody
    public List<TransactionDTO> all() {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return Collections.emptyList();
        }
        return transactionService
                .getAllTransactions(user.getId())
                .toJavaList();
    }
}
