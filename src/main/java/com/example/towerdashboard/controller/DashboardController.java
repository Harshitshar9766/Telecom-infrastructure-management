package com.example.towerdashboard.controller;

import com.example.towerdashboard.model.ErrorStatus;
import com.example.towerdashboard.model.TowerStatus;
import com.example.towerdashboard.service.ErrorService;
import com.example.towerdashboard.service.TowerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping
public class DashboardController {

    private final TowerService towerService;
    private final ErrorService errorService;

    public DashboardController(TowerService towerService, ErrorService errorService) {
        this.towerService = towerService;
        this.errorService = errorService;
    }

    @GetMapping("/api/dashboard/stats")
    @ResponseBody
    public Map<String, Object> stats() {
        Map<String,Object> m = new HashMap<>();
        long total = towerService.findAll().size();
        long active = towerService.countByStatus(TowerStatus.ACTIVE);
        long errors = towerService.countByStatus(TowerStatus.ERROR);
        long maintenance = towerService.countByStatus(TowerStatus.UNDER_MAINTENANCE);
        long fixed = towerService.countByStatus(TowerStatus.FIXED);
        m.put("totalTowers", total);
        m.put("activeTowers", active);
        m.put("towersWithErrors", errors);
        m.put("towersUnderMaintenance", maintenance);
        m.put("towersFixed", fixed);
        return m;
    }

    @GetMapping("/")
    public String dashboardPage(Model model) {
        model.addAllAttributes(stats());
        model.addAttribute("openErrors", errorService.findByStatus(ErrorStatus.OPEN));
        return "dashboard";
    }
}
