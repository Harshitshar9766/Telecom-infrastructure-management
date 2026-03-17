package com.example.towerdashboard.service;

import com.example.towerdashboard.model.Assignment;
import com.example.towerdashboard.model.AssignmentStatus;
import com.example.towerdashboard.model.TowerStatus;
import com.example.towerdashboard.repository.AssignmentRepository;
import com.example.towerdashboard.wrapperclass.AssignmentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class AssignmentService {

    private static final Logger logger = LoggerFactory.getLogger(AssignmentService.class);

    private final AssignmentRepository assignmentRepository;
    private final TowerService towerService;

    public AssignmentService(AssignmentRepository assignmentRepository, TowerService towerService) {
        this.assignmentRepository = assignmentRepository;
        this.towerService = towerService;
    }


    public ResponseEntity<AssignmentResponse> assign(Assignment assignment) {


        try {
            List<Assignment> existing = assignmentRepository.findByTowerIdAndStatusIn(
                    assignment.getTowerId(), List.of(AssignmentStatus.IN_PROGRESS, AssignmentStatus.ASSIGNED));
            String remainingTime = null;
            if (!existing.isEmpty()) {

                //case 1
                LocalDateTime now = LocalDateTime.now();
                Assignment activeAssignment = existing.get(0);
                LocalDateTime resolvedAt = activeAssignment.getResolved();

              remainingTime = "";
                if (resolvedAt == null || resolvedAt.isBefore(now)) {
                    remainingTime = "Already overdue or resolved now";
                } else {
                    Duration remaning = Duration.between(now, resolvedAt);
                    long hours = remaning.toHours();
                    long minutes = remaning.toMinutes() % 60;
                    remainingTime = String.format("%d hours, %d minutes", hours, minutes);
                }
                logger.info("Request is already exists for Tower ID {} at {}. Time left to resolve: {}", activeAssignment.getTowerId(), now, remainingTime);

                AssignmentResponse response = new AssignmentResponse(
                        "Tower already has active assignments",
                        activeAssignment,
                        HttpStatus.BAD_REQUEST.value(),
                        LocalDateTime.now(),
                        null,
                        remainingTime
                );
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

            }

            //case 2
            assignment.setAssignedTime(LocalDateTime.now());
            assignment.setStatus(AssignmentStatus.ASSIGNED);
            Assignment saved = assignmentRepository.save(assignment);

            LocalDateTime now = LocalDateTime.now();

            LocalDateTime resolvedAt = saved.getResolved();

            if (resolvedAt != null && resolvedAt.isAfter(now)) {

                Duration remaining =
                        Duration.between(now, resolvedAt);

                long hours =
                        remaining.toHours();

                long minutes =
                        remaining.toMinutes() % 60;

                remainingTime =
                        String.format(
                                "%d hours, %d minutes",
                                hours,
                                minutes
                        );
            }
            // when assignment created, set tower under maintenance
            towerService.updateStatus(assignment.getTowerId(), TowerStatus.UNDER_MAINTENANCE);
            logger.info("Assignment created successfully: {}", saved.getAssignmentId());

            AssignmentResponse response = new AssignmentResponse(
                    "Assignment created successfully",
                    saved,
                    HttpStatus.OK.value(),
                    LocalDateTime.now(),
                    null,
                    remainingTime
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error assigning tower", e);
//            throw new RuntimeException(e);
            AssignmentResponse response = new AssignmentResponse(
                    "Error assigning tower",
                    null,
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    LocalDateTime.now(),
                    e.getMessage(),
                    null
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);

        }

    }

    private void info(String s) {
    }

    public List<Assignment> findAll() {
        return assignmentRepository.findAll();
    }

    public Assignment updateStatus(Long id, AssignmentStatus status) {

        Optional<Assignment> opt =
                assignmentRepository.findById(id);

        if (opt.isPresent()) {

            Assignment a = opt.get();

            a.setStatus(status);

            Assignment saved = assignmentRepository.save(a);



            // if completed, mark tower fixed
            if (status == AssignmentStatus.COMPLETED) {

                towerService.updateStatus(
                        a.getTowerId(),
                        TowerStatus.FIXED
                );
            }

            return saved;

        } else {
            throw new RuntimeException("Assignment not found");
        }
    }




}
