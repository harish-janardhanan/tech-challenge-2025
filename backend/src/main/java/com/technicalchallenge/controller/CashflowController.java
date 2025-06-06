package com.technicalchallenge.controller;

import com.technicalchallenge.dto.CashflowDTO;
import com.technicalchallenge.mapper.CashflowMapper;
import com.technicalchallenge.model.Cashflow;
import com.technicalchallenge.service.CashflowService;
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
@RequestMapping("/api/cashflows")
@Validated
public class CashflowController {
    private static final Logger logger = LoggerFactory.getLogger(CashflowController.class);

    @Autowired
    private CashflowService cashflowService;
    @Autowired
    private CashflowMapper cashflowMapper;

    @GetMapping
    public List<CashflowDTO> getAllCashflows() {
        logger.info("Fetching all cashflows");
        return cashflowService.getAllCashflows().stream()
            .map(cashflowMapper::toDto)
            .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashflowDTO> getCashflowById(@PathVariable Long id) {
        logger.debug("Fetching cashflow by id: {}", id);
        return cashflowService.getCashflowById(id)
            .map(cashflowMapper::toDto)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createCashflow(@Valid @RequestBody CashflowDTO cashflowDTO) {
        logger.info("Creating new cashflow: {}", cashflowDTO);
        // Validation: value > 0, valueDate not null
        if (cashflowDTO.getPaymentValue() == null || cashflowDTO.getPaymentValue().signum() <= 0) {
            return ResponseEntity.badRequest().body("Cashflow value must be positive");
        }
        if (cashflowDTO.getValueDate() == null) {
            return ResponseEntity.badRequest().body("Value date is required");
        }
        var entity = cashflowMapper.toEntity(cashflowDTO);
        cashflowService.populateReferenceDataByName(entity, cashflowDTO);
        var saved = cashflowService.saveCashflow(entity);
        return ResponseEntity.ok(cashflowMapper.toDto(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCashflow(@PathVariable Long id) {
        logger.warn("Deleting cashflow with id: {}", id);
        cashflowService.deleteCashflow(id);
        return ResponseEntity.noContent().build();
    }

    // Accept and return Cashflow with related entities, not just IDs
}
