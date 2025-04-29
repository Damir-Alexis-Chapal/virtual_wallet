package com.app_wallet.virtual_wallet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class TestController {
    @GetMapping("/say")
    public String sayHi(){
        return "index";
    }

}
