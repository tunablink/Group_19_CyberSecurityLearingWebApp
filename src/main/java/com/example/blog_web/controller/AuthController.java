package com.example.blog_web.controller;

import com.example.blog_web.model.User;
import com.example.blog_web.model.UserDto;
import com.example.blog_web.repository.UserRepository;
import com.example.blog_web.config.JwtService;

import jakarta.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // ===== WEB REGISTER =====
    @GetMapping("/register")
    public String showRegister(org.springframework.ui.Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid UserDto userDto,
                                 org.springframework.validation.BindingResult result,
                                 org.springframework.ui.Model model) {

        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            result.rejectValue("username", "error.username", "Username đã tồn tại!");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "register";
        }

        userRepository.save(new User(userDto, passwordEncoder));
        model.addAttribute("success", true);
        model.addAttribute("user", new UserDto());
        return "register";
    }

    // ===== API REGISTER =====
    @PostMapping("/api/auth/register")
    @ResponseBody
    public String registerApi(@RequestBody UserDto userDto) {

        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            return "Username already exists";
        }

        userRepository.save(new User(userDto, passwordEncoder));
        return "Register success";
    }

    // ===== API LOGIN =====
    @PostMapping("/api/auth/login")
    @ResponseBody
    public String loginApi(@RequestBody UserDto request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        return jwtService.generateToken(request.getUsername());
    }
}