package com.example.authsystem.controller;

import com.example.authsystem.model.User;
import com.example.authsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, org.springframework.ui.Model model) {
        String result = userService.registerUser(user);
        if ("User already exists".equals(result)) {
            model.addAttribute("error", result);
            return "register";
        }
        return "redirect:/login?registered";
    }

    @PostMapping("/api/login")
    @ResponseBody
    public String apiLogin(@RequestBody User user){
        return userService.loginUser(user.getUsername(), user.getPassword());
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }
}
