package com.example.authsystem.controller;

import com.example.authsystem.dto.LoginRequest;
import com.example.authsystem.dto.LoginResponse;
import com.example.authsystem.dto.RegisterRequest;
import com.example.authsystem.dto.RegisterResponse;
import com.example.authsystem.model.User;
import com.example.authsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<RegisterResponse> apiRegister(@Valid @RequestBody RegisterRequest request) {
        User registeredUser = userService.registerUser(request);
        RegisterResponse response = new RegisterResponse(
                registeredUser.getId(),
                registeredUser.getUsername(),
                "User registered successfully"
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<LoginResponse> apiLogin(@Valid @RequestBody LoginRequest request){
        User user = userService.loginUser(request);
        LoginResponse response = new LoginResponse(
                "Login successful",
                user.getUsername(),
                null
        );
        return ResponseEntity.ok(response);
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
