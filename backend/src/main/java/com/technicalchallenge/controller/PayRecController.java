package com.technicalchallenge.controller;

import com.technicalchallenge.model.PayRec;
import com.technicalchallenge.service.PayRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/payrecs")
public class PayRecController {
    private static final Logger logger = LoggerFactory.getLogger(PayRecController.class);

    @Autowired
    private PayRecService payRecService;

    @GetMapping
    public List<PayRec> getAll() {
        logger.info("Fetching all pay recs");
        return payRecService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayRec> getById(@PathVariable Long id) {
        logger.debug("Fetching pay rec by id: {}", id);
        return payRecService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PayRec create(@RequestBody PayRec payRec) {
        logger.info("Creating new pay rec: {}", payRec);
        return payRecService.save(payRec);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayRec> update(@PathVariable Long id, @RequestBody PayRec payRec) {
        return payRecService.findById(id)
                .map(existing -> {
                    payRec.setId(id);
                    return ResponseEntity.ok(payRecService.save(payRec));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.warn("Deleting pay rec with id: {}", id);
        if (payRecService.findById(id).isPresent()) {
            payRecService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
