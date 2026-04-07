package com.example.blog_web.services;

import com.example.blog_web.models.User;
import com.example.blog_web.models.UserDto;
import com.example.blog_web.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service quản lý các yêu cầu xử lý nghiệp vụ đối với thực thể User.
 * <p>
 * Xử lý việc kiểm tra Username đã tồn tại chưa, mã hoá mật khẩu và lưu người dùng mới 
 * xuống CSDL khi đăng ký.
 * </p>
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean usernameExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Transactional
    public void registerUser(UserDto userDto) {
        User user = new User(userDto, passwordEncoder);
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
