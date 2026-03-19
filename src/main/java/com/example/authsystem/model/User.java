package com.example.authsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name ="users")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String email;

    private String role;

    // OAuth2 provider fields
    private String provider;
    
    @Column(name = "provider_id")
    private String providerId;
}
