package com.technicalchallenge.controller;

import com.technicalchallenge.model.Currency;
import com.technicalchallenge.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyController {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    @Autowired
    private CurrencyService currencyService;

    @GetMapping
    public List<Currency> getAll() {
        logger.info("Fetching all currencies");
        return currencyService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Currency> getById(@PathVariable Long id) {
        logger.debug("Fetching currency by id: {}", id);
        return currencyService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Currency create(@RequestBody Currency currency) {
        logger.info("Creating new currency: {}", currency);
        return currencyService.save(currency);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Currency> update(@PathVariable Long id, @RequestBody Currency currency) {
        return currencyService.findById(id)
                .map(existing -> {
                    currency.setId(id);
                    return ResponseEntity.ok(currencyService.save(currency));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.warn("Deleting currency with id: {}", id);
        if (currencyService.findById(id).isPresent()) {
            currencyService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
