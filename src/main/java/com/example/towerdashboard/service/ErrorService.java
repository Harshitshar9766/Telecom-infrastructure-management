package com.example.towerdashboard.service;

import com.example.towerdashboard.model.ErrorReport;
import com.example.towerdashboard.model.ErrorStatus;
import com.example.towerdashboard.repository.ErrorRepository;
import org.springframework.stereotype.Service;
import com.example.towerdashboard.service.TowerService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ErrorService {

    private final ErrorRepository errorRepository;
    private final TowerService towerService;

    public ErrorService(ErrorRepository errorRepository, TowerService towerService) {
        this.errorRepository = errorRepository;
        this.towerService = towerService;
    }

    public ErrorReport reportError(ErrorReport error) {
        error.setReportedTime(LocalDateTime.now());
        error.setStatus(ErrorStatus.OPEN);
        ErrorReport saved = errorRepository.save(error);
        // update tower status to ERROR
        towerService.updateStatus(error.getTowerId(), com.example.towerdashboard.model.TowerStatus.ERROR);
        return saved;
    }

    public List<ErrorReport> findAll() {
        return errorRepository.findAll();
    }

    public List<ErrorReport> findByStatus(ErrorStatus status) {
        return errorRepository.findByStatus(status);
    }

    public ErrorReport updateStatus(Long id, ErrorStatus status) {
        Optional<ErrorReport> opt = errorRepository.findById(id);
        if (opt.isPresent()) {
            ErrorReport e = opt.get();
            e.setStatus(status);
            return errorRepository.save(e);
        } else {
            throw new RuntimeException("Error not found");
        }
    }

    public void closeErrorByTower(Long towerId){

        List<ErrorReport> list = errorRepository.findAll();
        for (ErrorReport e: list){

            if (e.getErrorId().equals(towerId) && e.getStatus() == ErrorStatus.OPEN){
e.setStatus(ErrorStatus.RESOLVED);
errorRepository.save(e);
            }
        }
    }

    public  List<ErrorReport> findOldErrors(){
        LocalDateTime time = LocalDateTime.now().minusHours(5);

        return  errorRepository.findAll()
                .stream()
                .filter(e-> e.getReportedTime().isBefore(time) && e.getStatus() == ErrorStatus.OPEN)
                .toList();
    }

    public List<ErrorReport> findByTower(Long towerId){
        return errorRepository.findAll()
                .stream()
                .filter(t-> t.getTowerId().equals(towerId))
                .toList();
    }
}
