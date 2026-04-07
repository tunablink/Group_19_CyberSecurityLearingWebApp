package com.example.blog_web.models;

/**
 * Data Transfer Object (DTO) cho kết quả trả về khi đăng nhập thành công.
 * <p>
 * Chứa JWT token được tạo ra để client lưu lại và sử dụng gọi api 
 * cho các request sau này.
 * </p>
 */
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
