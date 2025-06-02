package com.technicalchallenge.service;

import com.technicalchallenge.dto.TradeDTO;
import com.technicalchallenge.model.Trade;
import com.technicalchallenge.repository.TradeRepository;
import com.technicalchallenge.repository.TradeStatusRepository;
import com.technicalchallenge.repository.TradeSubTypeRepository;
import com.technicalchallenge.repository.TradeTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class TradeService {
    private static final Logger logger = LoggerFactory.getLogger(TradeService.class);

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private TradeTypeRepository tradeTypeRepository;
    @Autowired
    private TradeSubTypeRepository tradeSubTypeRepository;
    @Autowired
    private TradeStatusRepository tradeStatusRepository;

    public List<Trade> getAllTrades() {
        logger.info("Retrieving all trades");
        return tradeRepository.findAll();
    }

    public Optional<Trade> getTradeById(Long id) {
        logger.debug("Retrieving trade by id: {}", id);
        return tradeRepository.findById(id);
    }

    public Trade saveTrade(Trade trade, TradeDTO dto) {
        logger.info("Saving trade: {}", trade);
        // Ensure Trade is saved with related entities set, not just IDs
        // Business logic: tradeDate, book, counterparty, status required (enforced in controller)
        return tradeRepository.save(trade);
    }

    public void deleteTrade(Long id) {
        logger.warn("Deleting trade with id: {}", id);
        tradeRepository.deleteById(id);
    }

    public void populateReferenceDataByName(Trade trade, TradeDTO dto) {
        if (dto.getTradeType() != null) {
            var tradeType = tradeTypeRepository.findAll().stream()
                .filter(t -> t.getTradeType().equalsIgnoreCase(dto.getTradeType()))
                .findFirst().orElse(null);
            if (tradeType == null) throw new IllegalArgumentException("TradeType '" + dto.getTradeType() + "' does not exist");
            trade.setTradeType(tradeType);
        }
        if (dto.getTradeSubType() != null) {
            var tradeSubType = tradeSubTypeRepository.findAll().stream()
                .filter(t -> t.getTradeSubType().equalsIgnoreCase(dto.getTradeSubType()))
                .findFirst().orElse(null);
            if (tradeSubType == null) throw new IllegalArgumentException("TradeSubType '" + dto.getTradeSubType() + "' does not exist");
            trade.setTradeSubType(tradeSubType);
        }
        if (dto.getTradeStatus() != null) {
            var tradeStatus = tradeStatusRepository.findAll().stream()
                .filter(t -> t.getTradeStatus().equalsIgnoreCase(dto.getTradeStatus()))
                .findFirst().orElse(null);
            if (tradeStatus == null) throw new IllegalArgumentException("TradeStatus '" + dto.getTradeStatus() + "' does not exist");
            trade.setTradeStatus(tradeStatus);
        }
    }
}
