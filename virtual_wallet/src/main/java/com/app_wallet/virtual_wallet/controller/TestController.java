package com.app_wallet.virtual_wallet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class TestController {
    @GetMapping("/welcome")
    public String sayHi(){
        return "test";
    }

    @GetMapping("/askforlogin")
    public String askLog(){
        return "ask-for-log-sign-in";
    }

    @GetMapping("/gotosignin")
    public String log(){
        return "sign-in";
    }

}
