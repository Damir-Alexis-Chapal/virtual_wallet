package com.app_wallet.virtual_wallet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "ask-for-log-sign-in"; // ask-for-log-sign-in.html
    }

    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // (Después creamos dashboard.html)
    }
}
