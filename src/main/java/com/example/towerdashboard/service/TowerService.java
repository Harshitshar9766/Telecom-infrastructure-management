package com.example.towerdashboard.service;

import com.example.towerdashboard.model.ErrorReport;
import com.example.towerdashboard.model.Tower;
import com.example.towerdashboard.model.TowerHistory;
import com.example.towerdashboard.model.TowerStatus;
import com.example.towerdashboard.repository.ErrorRepository;
import com.example.towerdashboard.repository.TowerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TowerService {

    private final TowerRepository towerRepository;
    private  final ErrorRepository errorRepository;
    private final TowerHistoryService towerHistoryService;

    public TowerService(TowerRepository towerRepository, ErrorRepository errorRepository, TowerHistoryService towerHistoryService) {
        this.towerRepository = towerRepository;
        this.errorRepository = errorRepository;
        this.towerHistoryService = towerHistoryService;
    }

    public List<Tower> findAll() {
        return towerRepository.findAll();
    }

    public Optional<Tower> findById(Long id) {
        return towerRepository.findById(id);
    }

    public Tower save(Tower tower) {
        tower.setLastUpdated(LocalDateTime.now());
        return towerRepository.save(tower);
    }

    public Tower updateStatus(Long id, TowerStatus status) {
        Optional<Tower> opt = towerRepository.findById(id);
        if (opt.isPresent()) {
            Tower t = opt.get();
            t.setStatus(status);
            t.setLastUpdated(LocalDateTime.now());
            Tower saved = towerRepository.save(t);
            towerHistoryService.log(id, status, "Status changed to "+status);
            return towerRepository.save(t);

        } else {
           System.out.println("Tower not found with id = " + id);
            return null;
//            throw new RuntimeException("Tower not found");
        }
    }

    public long countByStatus(TowerStatus status) {
        return towerRepository.findAll().stream().filter(t -> t.getStatus() == status).count();
    }


    public long countAll(){
        return towerRepository.count();
    }

    public long countError(){
        return towerRepository.findAll()
                .stream()
                .filter(tower -> tower.getStatus() == TowerStatus.ERROR )
                .count();
    }

    public long countFixed(){
        return towerRepository.findAll()
                .stream()
                .filter(tower -> tower.getStatus() == TowerStatus.FIXED)
                .count();
    }

    public  List<Tower> findByStatus(TowerStatus status){
        return towerRepository.findAll()
                .stream()
                .filter(t -> t.getStatus() == status)
                .toList();
    }



}
