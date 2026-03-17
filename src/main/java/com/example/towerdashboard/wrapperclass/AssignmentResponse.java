package com.example.towerdashboard.wrapperclass;

import com.example.towerdashboard.model.Assignment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentResponse {
    private String message;
    private Assignment assignment;
    private int status;
    private LocalDateTime timeStamp = LocalDateTime.now();
    private String errorDetails;
    private String remainingTime;
}
