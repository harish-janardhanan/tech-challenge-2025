package com.technicalchallenge.controller;

import com.technicalchallenge.model.LegType;
import com.technicalchallenge.service.LegTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/leg-types")
public class LegTypeController {
    private static final Logger logger = LoggerFactory.getLogger(LegTypeController.class);

    @Autowired
    private LegTypeService legTypeService;

    @GetMapping
    public List<LegType> getAll() {
        logger.info("Fetching all leg types");
        return legTypeService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LegType> getById(@PathVariable Long id) {
        logger.debug("Fetching leg type by id: {}", id);
        return legTypeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public LegType create(@RequestBody LegType legType) {
        logger.info("Creating new leg type: {}", legType);
        return legTypeService.save(legType);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LegType> update(@PathVariable Long id, @RequestBody LegType legType) {
        return legTypeService.findById(id)
                .map(existing -> {
                    legType.setId(id);
                    return ResponseEntity.ok(legTypeService.save(legType));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.warn("Deleting leg type with id: {}", id);
        if (legTypeService.findById(id).isPresent()) {
            legTypeService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
