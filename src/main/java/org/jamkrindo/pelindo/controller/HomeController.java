package org.jamkrindo.pelindo.controller;

import org.jamkrindo.pelindo.entity.User;
import org.jamkrindo.pelindo.entity.Role;
import org.jamkrindo.pelindo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    private final UserRepository userRepository;

    public HomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User tidak ditemukan"));

        if (user.getRole() == Role.ADMIN) {
            log.info("Login success for admin email={}, redirect=/admin/pelamar", user.getEmail());
            return "redirect:/admin/pelamar";
        }
        log.info("Login success for user email={}, redirect=/dashboard", user.getEmail());
        return "redirect:/dashboard";
    }
}

