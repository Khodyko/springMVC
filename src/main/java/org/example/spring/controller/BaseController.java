package org.example.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {
    public BaseController() {
        System.out.println("base controller!!!!!!!!!!!!!");
    }

    @GetMapping("/home")
    public String goHome() {
        System.out.println("go index from controller!");
        return "index";
    }
}
