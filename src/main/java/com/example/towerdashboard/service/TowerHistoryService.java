package com.example.towerdashboard.service;

import com.example.towerdashboard.model.TowerHistory;
import com.example.towerdashboard.model.TowerStatus;
import com.example.towerdashboard.repository.TowerHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TowerHistoryService {

    private final TowerHistoryRepository towerHistoryRepository;

    public TowerHistoryService(TowerHistoryRepository towerHistoryRepository) {
        this.towerHistoryRepository = towerHistoryRepository;
    }


    public  void log(Long towerId,TowerStatus status,String msg){
        TowerHistory h = new TowerHistory();

        h.setTowerId(towerId);
        h.setStatus(status);
        h.setTime(LocalDateTime.now());
        h.setMessage(msg);

        towerHistoryRepository.save(h);
    }
}
