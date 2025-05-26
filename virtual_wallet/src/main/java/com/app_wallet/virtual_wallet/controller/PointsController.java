package com.app_wallet.virtual_wallet.controller;

import com.app_wallet.virtual_wallet.dto.SystemPointsDTO;
import com.app_wallet.virtual_wallet.dto.UserDTO;
import com.app_wallet.virtual_wallet.entity.SystemPointsEntity;
import com.app_wallet.virtual_wallet.mapper.SystemPointsMapper;
import com.app_wallet.virtual_wallet.model.RangeUser;
import com.app_wallet.virtual_wallet.repository.SystemPointsRepository;
import com.app_wallet.virtual_wallet.service.PointsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/points")
public class PointsController {
    private final PointsService service;
    private final SystemPointsRepository systemPointsRepository;

    public PointsController(PointsService service,  SystemPointsRepository systemPointsRepository) {
        this.service = service;
        this.systemPointsRepository = systemPointsRepository;
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

    @GetMapping("/my")
    public ResponseEntity<SystemPointsDTO> myPoints(HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long userId = user.getId();

        Optional<SystemPointsDTO> userPointsOpt = systemPointsRepository.findByUserId(userId)
                .map(SystemPointsMapper::toDTO);

        if (userPointsOpt.isPresent()) {
            return ResponseEntity.ok(userPointsOpt.get());
        } else {
            SystemPointsDTO emptyPoints = new SystemPointsDTO();
            emptyPoints.setAccumulatedPoints(0);
            emptyPoints.setRangeUser(RangeUser.BRONZE);
            return ResponseEntity.ok(emptyPoints);
        }
    }


    @GetMapping("/current")
    public ResponseEntity<SystemPointsDTO> current(@RequestParam Long userId) {

        SystemPointsEntity e = service
                .findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No points record for user " + userId
                ));
        return ResponseEntity.ok(SystemPointsMapper.toDTO(e));
    }

    @PostMapping("/redeem")
    public ResponseEntity<?> redeemPoints(@RequestBody Map<String, Integer> body, HttpSession session) {

        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Integer pointsToRedeem = body.get("points");
        if (pointsToRedeem == null || pointsToRedeem <= 0) {
            return ResponseEntity.badRequest().body("Invalid point amount");
        }

        boolean success = service.redeemPoints(user.getId(), pointsToRedeem);
        System.out.println("\n\nestado de la respuesta en redeem\n\n"+success +"\n\n");
        if (!success) {
            return ResponseEntity.badRequest().body("Insufficient points or invalid request");
        }

        return ResponseEntity.ok("Reward redeemed successfully");
    }

}
