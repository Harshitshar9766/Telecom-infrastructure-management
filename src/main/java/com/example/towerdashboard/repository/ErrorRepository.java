package com.example.towerdashboard.repository;

import com.example.towerdashboard.model.ErrorReport;
import com.example.towerdashboard.model.ErrorStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ErrorRepository extends JpaRepository<ErrorReport, Long> {
    List<ErrorReport> findByStatus(ErrorStatus status);
}
