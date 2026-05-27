package org.jamkrindo.pelindo.controller;

import jakarta.validation.Valid;
import org.jamkrindo.pelindo.entity.User;
import org.jamkrindo.pelindo.entity.Biodata;
import org.jamkrindo.pelindo.repository.UserRepository;
import org.jamkrindo.pelindo.service.BiodataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BiodataController {

    private static final Logger log = LoggerFactory.getLogger(BiodataController.class);

    private final UserRepository userRepository;
    private final BiodataService biodataService;

    public BiodataController(UserRepository userRepository, BiodataService biodataService) {
        this.userRepository = userRepository;
        this.biodataService = biodataService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication, Model model) {
        User user = currentUser(authentication);
        log.info("Access dashboard by userId={} email={}", user.getId(), user.getEmail());
        Biodata biodata = biodataService.findByUserId(user.getId());
        model.addAttribute("biodata", biodata);
        return "dashboard-user";
    }

    @GetMapping("/biodata/form")
    public String biodataForm(Authentication authentication, Model model) {
        User user = currentUser(authentication);
        log.info("Open biodata form by userId={} email={}", user.getId(), user.getEmail());
        Biodata biodata = biodataService.findByUserId(user.getId());
        if (biodata == null) {
            biodata = biodataService.emptyBiodata(user);
        }
        biodataService.initializeChildrenSlots(biodata);
        model.addAttribute("biodata", biodata);
        model.addAttribute("adminMode", false);
        model.addAttribute("formAction", "/biodata/form");
        return "biodata-form";
    }

    @PostMapping("/biodata/form")
    public String saveBiodata(Authentication authentication,
                              @Valid @ModelAttribute("biodata") Biodata biodata,
                              BindingResult bindingResult,
                              Model model) {
        User user = currentUser(authentication);
        if (bindingResult.hasErrors()) {
            log.warn("Biodata validation failed for userId={} email={}", user.getId(), user.getEmail());
            biodataService.initializeChildrenSlots(biodata);
            model.addAttribute("adminMode", false);
            model.addAttribute("formAction", "/biodata/form");
            return "biodata-form";
        }

        biodataService.saveForUser(biodata, user);
        log.info("Biodata saved by userId={} email={}", user.getId(), user.getEmail());
        return "redirect:/dashboard?saved";
    }

    private User currentUser(Authentication authentication) {
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User tidak ditemukan"));
    }
}

