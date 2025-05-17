package com.app_wallet.virtual_wallet.controller;

import com.app_wallet.virtual_wallet.dto.AccountDTO;
import com.app_wallet.virtual_wallet.dto.UserDTO;
import com.app_wallet.virtual_wallet.repository.CustomUserRepository;
import com.app_wallet.virtual_wallet.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private CustomUserRepository customUserRepository;
    @Autowired
    private AccountService accountService;

    // Registro de usuario
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> registerUser(@RequestParam String name,
                                               @RequestParam String email,
                                               @RequestParam String password) {
        UserDTO dto = new UserDTO();
        dto.setName(name);
        dto.setEmail(email);
        dto.setPassword(password);
        customUserRepository.save(dto);

        return ResponseEntity.ok("Usuario registrado exitosamente");
    }

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> credentials,
                                       HttpSession session) {

        String email = credentials.get("email");
        String password = credentials.get("password");

        Optional<UserDTO> optionalUser = customUserRepository.findByEmail(email);

        if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(password)) {
            session.setAttribute("user", optionalUser.get());
            return ResponseEntity.ok().body(Map.of("message", "Login successful"));
        } else {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid credentials"));
        }
    }

    @PostMapping("/getAccounts")
    @ResponseBody
    public ResponseEntity<?> getAccounts(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(401).body("Usuario no autenticado");
        }

        List<AccountDTO> accounts = accountService.getAccountsByUserId(user.getId());
        return ResponseEntity.ok(accounts);
    }




}
