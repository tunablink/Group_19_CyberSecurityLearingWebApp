package com.example.blog_web.controllers;

import com.example.blog_web.models.UserDto;
import com.example.blog_web.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegister(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserDto());
        }
        return "register";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid UserDto userDto,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (userService.usernameExists(userDto.getUsername())) {
            result.rejectValue("username", "error.username", "Username đã tồn tại!");
        }

        if (result.hasErrors()) {
            return "register";
        }

        userService.registerUser(userDto);
        redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công!");
        return "redirect:/login";
    }
}