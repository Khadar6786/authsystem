package com.example.authsystem.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class TestController {
    @GetMapping("/dashboard")
    public String userDashboard() {
        return "Welcome USER!";

    }
}

@RequestMapping("/admin")
class AdminController{

    @GetMapping("/panel")
    public String adminPanel() {
        return "Welcome ADMIN!";
    }

}