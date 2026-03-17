package com.example.towerdashboard.controller;

import com.example.towerdashboard.model.Assignment;
import com.example.towerdashboard.model.AssignmentStatus;
import com.example.towerdashboard.service.AssignmentService;
import com.example.towerdashboard.service.TechnicianService;
import com.example.towerdashboard.service.TowerService;
import com.example.towerdashboard.wrapperclass.AssignmentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class AssignmentController {
    private final AssignmentService assignmentService;
    private final TechnicianService technicianService;
    private final TowerService towerService;

    public AssignmentController(AssignmentService assignmentService,
                                TechnicianService technicianService,
                                TowerService towerService) {
        this.assignmentService = assignmentService;
        this.technicianService = technicianService;
        this.towerService = towerService;
    }

    // REST
    @PostMapping("/api/assignments")
    @ResponseBody
    public ResponseEntity<AssignmentResponse> createAssignment(@RequestBody Assignment assignment) {
        return assignmentService.assign(assignment);
    }

    @GetMapping("/api/assignments")
    @ResponseBody
    public List<Assignment> getAllAssignments() {
        return assignmentService.findAll();
    }

    @PutMapping("/api/assignments/{id}/status")
    @ResponseBody
    public Assignment updateStatus(@PathVariable Long id, @RequestParam AssignmentStatus status) {
        return assignmentService.updateStatus(id, status);
    }

    // UI
    @GetMapping("/assignments")
    public String assignmentPage(Model model) {
        model.addAttribute("assignment", new Assignment());
        model.addAttribute("assignments", assignmentService.findAll());
        model.addAttribute("technicians", technicianService.findAll());
        model.addAttribute("towers", towerService.findAll());
        return "assignments";
    }

    @PostMapping("/assignments")
    public String assignForm(@ModelAttribute Assignment assignment) {
        assignmentService.assign(assignment);
        return "redirect:/assignments";
    }
}
