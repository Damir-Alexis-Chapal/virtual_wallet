package com.app_wallet.virtual_wallet;

import com.app_wallet.virtual_wallet.dto.UserDTO;
import com.app_wallet.virtual_wallet.entity.SystemPointsEntity;
import com.app_wallet.virtual_wallet.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VirtualWalletApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirtualWalletApplication.class, args);
    }
    @Bean
    CommandLineRunner initDatabase(UserService userService) {
        return args -> {
            // Crear puntos del sistema
            SystemPointsEntity points = new SystemPointsEntity();
            // Crear usuario de prueba
            UserDTO user = new UserDTO();
            user.setName("Juan Pérez");
            user.setEmail("juan@example.com");
            user.setSystemPoints(points);

            userService.createUser(user);

            System.out.println("Usuario de prueba creado!");
        };
    }

}
