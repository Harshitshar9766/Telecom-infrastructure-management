package com.example.towerdashboard.repository;

import com.example.towerdashboard.model.Technician;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {
}
