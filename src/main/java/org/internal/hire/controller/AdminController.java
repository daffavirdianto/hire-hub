package org.internal.hire.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.internal.hire.entity.Biodata;
import org.internal.hire.service.BiodataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private final BiodataService biodataService;

    public AdminController(BiodataService biodataService) {
        this.biodataService = biodataService;
    }

    @GetMapping("/pelamar")
    public String list(@RequestParam(name = "search", required = false) String search, Model model) {
        log.info("Admin open pelamar list, search={}", search);
        List<Biodata> biodataList = biodataService.findAll(search);
        model.addAttribute("biodataList", biodataList);
        model.addAttribute("search", search == null ? "" : search);
        return "admin-pelamar-list";
    }

    @GetMapping("/pelamar/{id}")
    public String detail(@PathVariable Long id, Model model) {
        log.info("Admin open pelamar detail id={}", id);
        Biodata biodata = biodataService.findByIdOrThrow(id);
        model.addAttribute("biodata", biodata);
        return "admin-pelamar-detail";
    }

    @GetMapping("/pelamar/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        log.info("Admin open edit form for pelamar id={}", id);
        Biodata biodata = biodataService.findByIdOrThrow(id);
        biodataService.initializeChildrenSlots(biodata);
        model.addAttribute("biodata", biodata);
        model.addAttribute("adminMode", true);
        model.addAttribute("formAction", "/admin/pelamar/" + id + "/edit");
        return "biodata-form";
    }

    @PostMapping("/pelamar/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("biodata") Biodata biodata,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            log.warn("Admin update validation failed for pelamar id={}", id);
            biodataService.initializeChildrenSlots(biodata);
            model.addAttribute("adminMode", true);
            model.addAttribute("formAction", "/admin/pelamar/" + id + "/edit");
            return "biodata-form";
        }

        biodataService.saveForAdmin(id, biodata);
        log.info("Admin updated pelamar id={}", id);
        return "redirect:/admin/pelamar/" + id + "?updated";
    }

    @PostMapping("/pelamar/{id}/delete")
    public String delete(@PathVariable Long id) {
        biodataService.deleteById(id);
        log.warn("Admin deleted pelamar id={}", id);
        return "redirect:/admin/pelamar?deleted";
    }
}

