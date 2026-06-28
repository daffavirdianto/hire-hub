package org.internal.hire.controller;

import jakarta.validation.Valid;
import org.internal.hire.dto.SignupForm;
import org.internal.hire.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signupForm") SignupForm form,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            log.warn("Signup validation failed for email={}", form.getEmail());
            return "signup";
        }

        try {
            authService.registerUser(form);
        } catch (IllegalArgumentException ex) {
            log.warn("Signup failed for email={}, reason={}", form.getEmail(), ex.getMessage());
            model.addAttribute("errorMessage", ex.getMessage());
            return "signup";
        }

        log.info("Signup completed for email={}", form.getEmail());
        return "redirect:/login?registered";
    }
}

