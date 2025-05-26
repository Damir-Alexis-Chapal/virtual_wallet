package com.app_wallet.virtual_wallet.controller;

import com.app_wallet.virtual_wallet.dto.UserDTO;
import com.app_wallet.virtual_wallet.service.StatisticsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stats")
public class StatisticsController {

    private final StatisticsService stats;

    public StatisticsController(StatisticsService stats) {
        this.stats = stats;
    }

    @GetMapping("/patterns/categories")
    public List<Map<String, Object>> categoryPatterns(HttpSession session) {

        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) return List.of();
        Long userId = user.getId();
        return stats.getCategoryStatistics(userId);
    }

    @GetMapping("/clients/frequent")
    public List<Map<String, Object>> frequentTransfers(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) return List.of();
        Long userId = user.getId();
        return stats.getFrequentTransfers(userId);
    }

}