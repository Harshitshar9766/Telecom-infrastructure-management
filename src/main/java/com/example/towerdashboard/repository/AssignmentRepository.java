package com.example.towerdashboard.repository;

import com.example.towerdashboard.model.Assignment;
import com.example.towerdashboard.model.AssignmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findByTowerIdAndStatusIn(Long towerId, List<AssignmentStatus> inProgress);

}
