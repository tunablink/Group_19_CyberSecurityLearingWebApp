package com.example.blog_web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Utility controller trình diễn các UI components dùng chung toàn cục.
 * <p>
 * Cung cấp trang tĩnh báo cáo hoặc trang động nhằm trưng bày tập hợp
 * các phần tử UI chuẩn (như nút, thông báo alert) để tiện tham khảo khi dev frontend.
 * </p>
 */
@Controller
public class UiController {

    @GetMapping("/ui-components")
    public String showUiComponents(Model model) {
        model.addAttribute("successMessage", "This is a demonstration of a success alert!");
        model.addAttribute("infoMessage", "This is a demonstration of an info alert.");
        model.addAttribute("errorMessage", "This is a demonstration of an error alert.");
        return "ui-components";
    }
}
