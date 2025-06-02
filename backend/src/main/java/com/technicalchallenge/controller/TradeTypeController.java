package com.technicalchallenge.controller;

import com.technicalchallenge.model.TradeType;
import com.technicalchallenge.service.TradeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/trade-types")
public class TradeTypeController {
    private static final Logger logger = LoggerFactory.getLogger(TradeTypeController.class);

    @Autowired
    private TradeTypeService tradeTypeService;

    @GetMapping
    public List<TradeType> getAll() {
        logger.info("Fetching all trade types");
        return tradeTypeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TradeType> getById(@PathVariable Long id) {
        logger.debug("Fetching trade type by id: {}", id);
        return tradeTypeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TradeType create(@RequestBody TradeType tradeType) {
        logger.info("Creating new trade type: {}", tradeType);
        return tradeTypeService.save(tradeType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TradeType> update(@PathVariable Long id, @RequestBody TradeType tradeType) {
        return tradeTypeService.findById(id)
                .map(existing -> {
                    tradeType.setId(id);
                    return ResponseEntity.ok(tradeTypeService.save(tradeType));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.warn("Deleting trade type with id: {}", id);
        if (tradeTypeService.findById(id).isPresent()) {
            tradeTypeService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
