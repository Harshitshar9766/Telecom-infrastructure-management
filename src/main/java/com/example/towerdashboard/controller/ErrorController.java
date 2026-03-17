package com.example.towerdashboard.controller;

import com.example.towerdashboard.model.ErrorReport;
import com.example.towerdashboard.model.ErrorStatus;
import com.example.towerdashboard.service.ErrorService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class ErrorController {

    private final ErrorService errorService;

    public ErrorController(ErrorService errorService) {
        this.errorService = errorService;
    }

    // REST
    @PostMapping("/api/errors/report")
    @ResponseBody
    public ErrorReport reportError(@RequestBody ErrorReport report) {
        return errorService.reportError(report);
    }

    @GetMapping("/api/errors")
    @ResponseBody
    public List<ErrorReport> getAllErrors() {
        return errorService.findAll();
    }

    @GetMapping("/api/errors/open")
    @ResponseBody
    public List<ErrorReport> getOpenErrors() {
        return errorService.findByStatus(ErrorStatus.OPEN);
    }

    @PutMapping("/api/errors/{id}/status")
    @ResponseBody
    public ErrorReport updateStatus(@PathVariable Long id, @RequestParam ErrorStatus status) {
        return errorService.updateStatus(id, status);
    }

    // UI pages
    @GetMapping("/errors/report")
    public String errorReportPage(Model model) {
        model.addAttribute("errorReport", new ErrorReport());
        return "error_report";
    }

    @PostMapping("/errors/report")
    public String submitErrorReport(@ModelAttribute ErrorReport report) {
        errorService.reportError(report);
        return "redirect:/"; // after reporting go back to dashboard
    }
}
