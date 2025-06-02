package com.technicalchallenge.controller;

import com.technicalchallenge.model.TradeStatus;
import com.technicalchallenge.service.TradeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/trade-statuses")
public class TradeStatusController {
    private static final Logger logger = LoggerFactory.getLogger(TradeStatusController.class);

    @Autowired
    private TradeStatusService tradeStatusService;

    @GetMapping
    public List<TradeStatus> getAll() {
        logger.info("Fetching all trade statuses");
        return tradeStatusService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TradeStatus> getById(@PathVariable Long id) {
        logger.debug("Fetching trade status by id: {}", id);
        return tradeStatusService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TradeStatus create(@RequestBody TradeStatus tradeStatus) {
        logger.info("Creating new trade status: {}", tradeStatus);
        return tradeStatusService.save(tradeStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TradeStatus> update(@PathVariable Long id, @RequestBody TradeStatus tradeStatus) {
        return tradeStatusService.findById(id)
                .map(existing -> {
                    tradeStatus.setId(id);
                    return ResponseEntity.ok(tradeStatusService.save(tradeStatus));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.warn("Deleting trade status with id: {}", id);
        if (tradeStatusService.findById(id).isPresent()) {
            tradeStatusService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
