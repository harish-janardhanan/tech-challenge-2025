package com.technicalchallenge.controller;

import com.technicalchallenge.dto.TradeDTO;
import com.technicalchallenge.mapper.TradeMapper;
import com.technicalchallenge.model.Trade;
import com.technicalchallenge.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/trades")
@Validated
public class TradeController {
    private static final Logger logger = LoggerFactory.getLogger(TradeController.class);

    @Autowired
    private TradeService tradeService;
    @Autowired
    private TradeMapper tradeMapper;

    @GetMapping
    public List<TradeDTO> getAllTrades() {
        logger.info("Fetching all trades");
        return tradeService.getAllTrades().stream()
            .map(tradeMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TradeDTO> getTradeById(@PathVariable Long id) {
        logger.debug("Fetching trade by id: {}", id);
        return tradeService.getTradeById(id)
            .map(tradeMapper::toDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createTrade(@Valid @RequestBody TradeDTO tradeDTO) {
        logger.info("Creating new trade: {}", tradeDTO);
        // Validation: tradeDate, book, counterparty, status required
        if (tradeDTO.getTradeDate() == null) {
            return ResponseEntity.badRequest().body("Trade date is required");
        }
        if (tradeDTO.getBook() == null || tradeDTO.getCounterparty() == null || tradeDTO.getTradeStatus() == null) {
            return ResponseEntity.badRequest().body("Book, Counterparty, and Status are required");
        }
        // Lookup reference data by name
        var entity = tradeMapper.toEntity(tradeDTO);
        tradeService.populateReferenceDataByName(entity, tradeDTO);
        var saved = tradeService.saveTrade(entity, tradeDTO);
        return ResponseEntity.ok(tradeMapper.toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrade(@PathVariable Long id) {
        logger.warn("Deleting trade with id: {}", id);
        tradeService.deleteTrade(id);
        return ResponseEntity.noContent().build();
    }

    // Accept and return Trade with related entities, not just IDs
}
