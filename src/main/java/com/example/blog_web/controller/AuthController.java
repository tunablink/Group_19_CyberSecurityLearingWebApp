package com.example.blog_web.controller;

import com.example.blog_web.model.LoginRequest;
import com.example.blog_web.model.LoginResponse;
import com.example.blog_web.model.User;
import com.example.blog_web.model.UserDto;
import com.example.blog_web.repository.UserRepository;
import com.example.blog_web.serivce.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/auth/login")
    @ResponseBody
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = tokenProvider.generateToken(authentication);
            System.out.println("✅ Authentication successful for user: " + loginRequest.getUsername());
            return ResponseEntity.ok(new LoginResponse(jwt));

        } catch (BadCredentialsException e) {
            System.err.println("❌ Authentication failed for user '" + loginRequest.getUsername() + "'. Reason: Invalid credentials.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Invalid username or password."));
        } catch (UsernameNotFoundException e) {
            System.err.println("❌ Authentication failed. Reason: User not found: '" + loginRequest.getUsername() + "'.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "User not found."));
        } catch (AuthenticationException e) {
            System.err.println("❌ Authentication failed for user '" + loginRequest.getUsername() + "'. Reason: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Authentication failed."));
        }
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid UserDto userDto,
                                 BindingResult result,
                                 Model model) {
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
}