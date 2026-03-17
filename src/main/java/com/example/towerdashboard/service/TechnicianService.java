package com.example.towerdashboard.service;

import com.example.towerdashboard.model.Assignment;
import com.example.towerdashboard.model.AssignmentStatus;
import com.example.towerdashboard.model.Technician;
import com.example.towerdashboard.repository.AssignmentRepository;
import com.example.towerdashboard.repository.TechnicianRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TechnicianService {

    private final TechnicianRepository technicianRepository;
private final AssignmentRepository assignmentRepository;
    public TechnicianService(TechnicianRepository technicianRepository, AssignmentRepository assignmentRepository) {
        this.technicianRepository = technicianRepository;
        this.assignmentRepository = assignmentRepository;
    }

    public Technician save(Technician tech) {
        return technicianRepository.save(tech);
    }

    public List<Technician> findAll() {
        return technicianRepository.findAll();
    }

    public Optional<Technician> findById(Long id) {
        return technicianRepository.findById(id);
    }

    public  boolean isBusy(Long techId){
        List<Assignment> list = assignmentRepository.findAll();
        for (Assignment a: list){
            if(a.getTechId().equals(techId) && a.getStatus() != AssignmentStatus.COMPLETED){
            return true;
            }
        }

        return false;
    }


}
