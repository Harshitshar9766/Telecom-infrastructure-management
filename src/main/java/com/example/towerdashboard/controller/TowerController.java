package com.example.towerdashboard.controller;

import com.example.towerdashboard.model.Tower;
import com.example.towerdashboard.model.TowerStatus;
import com.example.towerdashboard.service.TowerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class TowerController {

    private final TowerService towerService;

    public TowerController(TowerService towerService) {
        this.towerService = towerService;
    }

    // REST endpoints
    @GetMapping("/api/towers")
    @ResponseBody
    public List<Tower> getAllTowers() {
        return towerService.findAll();
    }

    @PostMapping("/api/towers")
    @ResponseBody
    public Tower createTower(@RequestBody Tower tower) {
        tower.setStatus(TowerStatus.ACTIVE);
        return towerService.save(tower);
    }

    @GetMapping("/api/towers/{id}")
    @ResponseBody
    public ResponseEntity<Tower> getTower(@PathVariable Long id) {
        return towerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/api/towers/{id}/status")
    @ResponseBody
    public Tower updateStatus(@PathVariable Long id, @RequestParam TowerStatus status) {
        return towerService.updateStatus(id, status);
    }

    // UI page
    @GetMapping("/towers")
    public String towerListPage(Model model) {
        model.addAttribute("towers", towerService.findAll());
        return "tower_list";
    }
}
