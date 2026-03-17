package com.example.towerdashboard.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentId;

    private Long towerId;
    private Long techId;
    private Integer etaHours;

    @Enumerated(EnumType.STRING)
    private AssignmentStatus status;

    private LocalDateTime assignedTime;

    public Assignment() {}


    public LocalDateTime getResolved(){
        if(assignedTime != null && etaHours != null){
            return assignedTime.plusHours(etaHours);
        } else {
            return null;
        }
    }


    // getters and setters
//    public Long getAssignmentId() {
//        return assignmentId;
//    }
//
//    public void setAssignmentId(Long assignmentId) {
//        this.assignmentId = assignmentId;
//    }

//    public Long getTowerId() {
//        return towerId;
//    }
//
//    public void setTowerId(Long towerId) {
//        this.towerId = towerId;
//    }

//    public Long getTechId() {
//        return techId;
//    }
//
//    public void setTechId(Long techId) {
//        this.techId = techId;
//    }

//    public Integer getEtaHours() {
//        return etaHours;
//    }
//
//    public void setEtaHours(Integer etaHours) {
//        this.etaHours = etaHours;
//    }

//    public AssignmentStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(AssignmentStatus status) {
//        this.status = status;
//    }

//    public LocalDateTime getAssignedTime() {
//        return assignedTime;
//    }
//
//    public void setAssignedTime(LocalDateTime assignedTime) {
//        this.assignedTime = assignedTime;
//    }


}