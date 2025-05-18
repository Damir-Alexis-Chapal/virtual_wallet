package com.app_wallet.virtual_wallet.controller;

import com.app_wallet.virtual_wallet.dto.SystemPointsDTO;
import com.app_wallet.virtual_wallet.mapper.SystemPointsMapper;
import com.app_wallet.virtual_wallet.service.PointsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/points")
public class PointsController {
    private final PointsService service;

    public PointsController(PointsService service) {
        this.service = service;
    }

    @PostMapping("/onTransaction")
    public ResponseEntity<?> onTx(
            @RequestParam Long userId,
            @RequestParam String type,
            @RequestParam BigDecimal amount
    ) {
        service.addPointsForTransaction(userId, type, amount);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/top")
    public List<SystemPointsDTO> top(@RequestParam(defaultValue = "5") int n) {
        return service.getTopUsersEntities(n).stream()
                .map(SystemPointsMapper::toDTO)
                .collect(Collectors.toList());
    }
}
