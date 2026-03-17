package com.example.towerdashboard.controller;

import com.example.towerdashboard.model.Technician;
import com.example.towerdashboard.service.TechnicianService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class TechnicianController {
    private final TechnicianService techService;

    public TechnicianController(TechnicianService techService) {
        this.techService = techService;
    }

    // REST
    @PostMapping("/api/technicians")
    @ResponseBody
    public Technician addTechnician(@RequestBody Technician tech) {
        return techService.save(tech);
    }

    @GetMapping("/api/technicians")
    @ResponseBody
    public List<Technician> listTechs() {
        return techService.findAll();
    }

    // UI
    @GetMapping("/technicians")
    public String technicianPage(Model model) {
        model.addAttribute("technician", new Technician());
        model.addAttribute("technicians", techService.findAll());
        return "technicians";
    }

    @PostMapping("/technicians")
    public String addTechnicianForm(@ModelAttribute Technician tech) {
        techService.save(tech);
        return "redirect:/technicians";
    }
}
