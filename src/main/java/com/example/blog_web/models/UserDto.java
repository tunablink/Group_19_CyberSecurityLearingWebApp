package com.example.blog_web.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDto {
    @NotBlank(message = "Username khÃ´ng Ä‘Æ°á»£c Ä‘á»ƒ trá»‘ng")
    @Size(min = 4, max = 50, message = "Username pháº£i tá»« 4-50 kÃ½ tá»±")
    private String username;

    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[A-Z]).{6,60}$",
            message = "Password tá»‘i thiá»ƒu 6 kÃ½ tá»±, cÃ³ Ã­t nháº¥t 1 sá»‘ vÃ  1 chá»¯ hoa"
    )
    private String password;

    private String address;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}