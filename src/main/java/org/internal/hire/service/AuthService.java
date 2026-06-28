package org.internal.hire.service;

import org.internal.hire.dto.SignupForm;
import org.internal.hire.entity.User;
import org.internal.hire.entity.Role;
import org.internal.hire.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository UserRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = UserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(SignupForm form) {
        String normalizedEmail = form.getEmail().trim().toLowerCase();
        log.info("Register attempt for email={}", normalizedEmail);

        if (userRepository.existsByEmail(normalizedEmail)) {
            log.warn("Register rejected, email already exists: {}", normalizedEmail);
            throw new IllegalArgumentException("Email sudah terdaftar.");
        }

        User user = new User();
        user.setEmail(normalizedEmail);
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);
        log.info("User registered successfully, email={}", normalizedEmail);
    }
}

