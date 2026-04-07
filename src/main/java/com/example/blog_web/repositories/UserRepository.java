package com.example.blog_web.repositories;

import com.example.blog_web.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * JPA Repository dành cho Entity User.
 * <p>
 * Cung cấp các thao tác CRUD cơ bản cho bảng `users` trong cơ sở dữ liệu,
 * đi kèm query tùy chỉnh (như tìm user dựa trên username lúc đăng nhập).
 * </p>
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}