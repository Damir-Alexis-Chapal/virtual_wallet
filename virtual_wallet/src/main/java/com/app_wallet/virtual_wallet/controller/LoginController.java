package com.app_wallet.virtual_wallet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "ask-for-log-sign-in"; // ask-for-log-sign-in.html
    }


    @GetMapping("/gotoapp")
    public String goToApp() {
        return "app";
    }
}
