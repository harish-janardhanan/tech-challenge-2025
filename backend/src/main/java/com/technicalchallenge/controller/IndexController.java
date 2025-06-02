package com.technicalchallenge.controller;

import com.technicalchallenge.model.Index;
import com.technicalchallenge.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/indices")
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private IndexService indexService;

    @GetMapping
    public List<Index> getAll() {
        logger.info("Fetching all indexes");
        return indexService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Index> getById(@PathVariable Long id) {
        logger.debug("Fetching index by id: {}", id);
        return indexService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createIndex(@RequestBody Index index) {
        logger.info("Creating new index: {}", index);
        return ResponseEntity.ok(indexService.save(index));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Index> update(@PathVariable Long id, @RequestBody Index index) {
        return indexService.findById(id)
                .map(existing -> {
                    index.setId(id);
                    return ResponseEntity.ok(indexService.save(index));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.warn("Deleting index with id: {}", id);
        if (indexService.findById(id).isPresent()) {
            indexService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
