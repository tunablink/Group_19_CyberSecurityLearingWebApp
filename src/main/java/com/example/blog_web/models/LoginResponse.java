package com.example.blog_web.models;

public class LoginResponse {
    private String token;
    private final String tokenType = "Bearer";

    public LoginResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }
}
