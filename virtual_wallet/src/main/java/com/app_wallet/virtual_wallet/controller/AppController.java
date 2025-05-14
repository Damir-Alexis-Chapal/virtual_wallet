package com.app_wallet.virtual_wallet.controller;

import com.app_wallet.virtual_wallet.dto.UserDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class AppController {

    @GetMapping("/gotoapp")
    public String appView(HttpSession session, Model model) {
        // Obtener el usuario de la sesión
        UserDTO user = (UserDTO) session.getAttribute("user");
        System.out.println("usuario en la app");
        if (user != null) {
            // Pasar los datos del usuario a la vista
            model.addAttribute("user", user);
            System.out.println("usuario en la app 2");
        } else {
            // Redirigir al login si no hay un usuario en la sesión
            return "redirect:/login";
        }

        return "app";
    }
}

