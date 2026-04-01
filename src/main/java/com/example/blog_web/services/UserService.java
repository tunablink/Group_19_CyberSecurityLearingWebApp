package com.example.blog_web.services;

import com.example.blog_web.models.User;
import com.example.blog_web.models.UserDto;
import com.example.blog_web.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
