package com.example.blog_web.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String roles;
    private String address;

    public User() {}

    public User(UserDto dto, org.springframework.security.crypto.password.PasswordEncoder encoder) {
        this.username = dto.getUsername();
        this.password = encoder.encode(dto.getPassword());
        this.address = dto.getAddress();
        this.roles = "USER";
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRoles() { return roles; }
    public void setRoles(String roles) { this.roles = roles; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}