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
    public ResponseEntity<TradeDTO> getTradeById(@PathVariable(name = "id") Long id) {
        logger.debug("Fetching trade by id: {}", id);
        return tradeService.getTradeById(id)
            .map(tradeMapper::toDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createTrade(@Valid @RequestBody TradeDTO tradeDTO) {
        logger.info("Creating new trade: {}", tradeDTO);
        // Validation: tradeDate, book, counterparty required
        if (tradeDTO.getTradeDate() == null) {
            return ResponseEntity.badRequest().body("Trade date is required");
        }
        if (tradeDTO.getBookName() == null || tradeDTO.getCounterpartyName() == null) {
            return ResponseEntity.badRequest().body("Book and Counterparty are required");
        }
        // Default tradeStatus to LIVE if not provided
        if (tradeDTO.getTradeStatus() == null) {
            tradeDTO.setTradeStatus("LIVE");
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrade(@PathVariable(name = "id") Long id, @Valid @RequestBody TradeDTO tradeDTO) {
        logger.info("Amending trade with id: {}, updated data: {}", id, tradeDTO);

        // Validation: tradeDate, book, counterparty required
        if (tradeDTO.getTradeDate() == null) {
            return ResponseEntity.badRequest().body("Trade date is required");
        }
        if (tradeDTO.getBookName() == null || tradeDTO.getCounterpartyName() == null) {
            return ResponseEntity.badRequest().body("Book and Counterparty are required");
        }

        // Ensure the path variable matches the DTO tradeId
        if (tradeDTO.getTradeId() == null) {
            tradeDTO.setTradeId(id);
        } else if (!tradeDTO.getTradeId().equals(id)) {
            return ResponseEntity.badRequest().body("Trade ID in path must match Trade ID in request body");
        }

        // Convert DTO to entity
        var entity = tradeMapper.toEntity(tradeDTO);
        tradeService.populateReferenceDataByName(entity, tradeDTO);

        // Save the amended trade (which will mark the previous version as DEAD)
        var saved = tradeService.saveTrade(entity, tradeDTO);
        return ResponseEntity.ok(tradeMapper.toDto(saved));
    }

    @PostMapping("/terminate/{id}")
    public ResponseEntity<?> terminateTrade(@PathVariable(name = "id") Long id) {
        logger.warn("Terminating trade with id: {}", id);

        // Check if trade exists
        Optional<Trade> tradeOpt = tradeService.getTradeById(id);
        if (tradeOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Terminate the trade
        Trade terminatedTrade = tradeService.terminateTrade(id);

        return ResponseEntity.ok(tradeMapper.toDto(terminatedTrade));
    }
}
