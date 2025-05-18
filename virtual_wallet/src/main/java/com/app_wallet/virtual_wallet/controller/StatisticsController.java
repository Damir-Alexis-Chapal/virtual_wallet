package com.app_wallet.virtual_wallet.controller;

import com.app_wallet.virtual_wallet.model.Category;
import com.app_wallet.virtual_wallet.service.StatisticsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stats/patterns")
public class StatisticsController {

    private final StatisticsService stats;

    public StatisticsController(StatisticsService stats) {
        this.stats = stats;
    }

    @GetMapping("/categories")
    public List<Map<String,Object>> categoryPatterns(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return Collections.emptyList();
        }

        return stats.categoryTransitionCounts(userId)
                .entrySet()
                .stream()
                .map(e -> {
                    Map<String,Object> m = new HashMap<>();
                    m.put("category", e.getKey().name());
                    m.put("count",    e.getValue());
                    return m;
                })
                .collect(Collectors.toList());
    }
}
