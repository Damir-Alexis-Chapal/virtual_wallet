package com.app_wallet.virtual_wallet.controller;

import com.app_wallet.virtual_wallet.dto.SystemPointsDTO;
import com.app_wallet.virtual_wallet.entity.SystemPointsEntity;
import com.app_wallet.virtual_wallet.mapper.SystemPointsMapper;
import com.app_wallet.virtual_wallet.service.PointsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping("/current")
    public ResponseEntity<SystemPointsDTO> current(@RequestParam Long userId) {
        // Obtengo la entidad de puntos o 404 si no existe
        SystemPointsEntity e = service
                .findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No points record for user " + userId
                ));
        // Mapeo y devuelvo
        return ResponseEntity.ok(SystemPointsMapper.toDTO(e));
    }
}
