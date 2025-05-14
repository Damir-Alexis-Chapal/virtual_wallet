package com.app_wallet.virtual_wallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite TODO
                )
                .formLogin().disable() // Desactiva login por formulario
                .oauth2Login().disable() // Desactiva login por Google
                .logout().disable(); // Desactiva logout si no lo necesitas

        return http.build();
    }
}


