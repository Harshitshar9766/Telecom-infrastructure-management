package com.example.towerdashboard.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Tower History")
public class TowerHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long towerId;

    @Enumerated(EnumType.STRING)
    private TowerStatus status;

    private LocalDateTime time;

    private String message;

    public TowerHistory(){ }


    public TowerHistory(Long towerId, TowerStatus status, LocalDateTime time, String message) {
        this.towerId = towerId;
        this.status = status;
        this.time = time;
        this.message = message;
    }

//    public void setStatus(Long towerId, TowerStatus status) {
//        this.towerId=towerId;
//        this.status=status;
//    }

}
