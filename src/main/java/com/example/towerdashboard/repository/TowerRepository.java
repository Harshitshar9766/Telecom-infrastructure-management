package com.example.towerdashboard.repository;

import com.example.towerdashboard.model.Tower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TowerRepository extends JpaRepository<Tower, Long> {
}
