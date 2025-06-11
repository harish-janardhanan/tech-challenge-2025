package com.technicalchallenge.service;

import com.technicalchallenge.dto.TradeDTO;
import com.technicalchallenge.model.Trade;
import com.technicalchallenge.model.TradeStatus;
import com.technicalchallenge.repository.TradeRepository;
import com.technicalchallenge.repository.TradeStatusRepository;
import com.technicalchallenge.repository.TradeSubTypeRepository;
import com.technicalchallenge.repository.TradeTypeRepository;
import com.technicalchallenge.repository.TradeLegRepository;
import com.technicalchallenge.repository.CashflowRepository;
import com.technicalchallenge.model.TradeLeg;
import com.technicalchallenge.model.Cashflow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @Autowired
    private TradeLegRepository tradeLegRepository;
    @Autowired
    private CashflowRepository cashflowRepository;

    public List<Trade> getAllTrades() {
        logger.info("Retrieving all trades");
        return tradeRepository.findAll();
    }

    public Optional<Trade> getTradeById(Long id) {
        logger.debug("Retrieving trade by id: {}", id);
        return tradeRepository.findByTradeId(id).stream().filter(x -> x.getValidityEndDate() == null).findFirst();
    }

    private TradeLeg deepCopyTradeLeg(TradeLeg oldLeg, Trade savedTrade) {
        TradeLeg newLeg = new TradeLeg();
        newLeg.setNotional(oldLeg.getNotional());
        newLeg.setRate(oldLeg.getRate());
        newLeg.setCurrency(oldLeg.getCurrency());
        newLeg.setLegRateType(oldLeg.getLegRateType());
        newLeg.setIndex(oldLeg.getIndex());
        newLeg.setHolidayCalendar(oldLeg.getHolidayCalendar());
        newLeg.setCalculationPeriodSchedule(oldLeg.getCalculationPeriodSchedule());
        newLeg.setPaymentBusinessDayConvention(oldLeg.getPaymentBusinessDayConvention());
        newLeg.setFixingBusinessDayConvention(oldLeg.getFixingBusinessDayConvention());
        newLeg.setPayReceiveFlag(oldLeg.getPayReceiveFlag());
        newLeg.setTrade(savedTrade);
        return tradeLegRepository.save(newLeg);
    }

    private List<Cashflow> deepCopyCashflows(List<Cashflow> oldCashflows, TradeLeg newLeg) {
        // Initialize newLeg's cashflows collection if it's null
        if (newLeg.getCashflows() == null) {
            newLeg.setCashflows(new ArrayList<>());
        } else {
            // Clear any existing cashflows to avoid orphan deletion errors
            newLeg.getCashflows().clear();
        }

        List<Cashflow> newCashflows = new ArrayList<>();
        // If there are no cashflows to copy, just return an empty list
        if (oldCashflows == null || oldCashflows.isEmpty()) {
            logger.debug("No cashflows to copy for leg {}", newLeg.getLegId());
            return newCashflows;
        }

        // Log the start of cashflow copying
        logger.info("Copying {} cashflows for trade leg {}", oldCashflows.size(), newLeg.getLegId());

        LocalDateTime now = LocalDateTime.now();
        for (Cashflow oldCf : oldCashflows) {
            Cashflow newCf = new Cashflow();
            newCf.setPaymentValue(oldCf.getPaymentValue());
            newCf.setValueDate(oldCf.getValueDate());
            newCf.setRate(oldCf.getRate());
            newCf.setPayRec(oldCf.getPayRec());
            newCf.setPaymentType(oldCf.getPaymentType());
            newCf.setPaymentBusinessDayConvention(oldCf.getPaymentBusinessDayConvention());

            // Set validity dates for the new cashflow
            newCf.setValidityStartDate(now);
            newCf.setValidityEndDate(null);

            // Set the leg reference to maintain bidirectional relationship
            newCf.setLeg(newLeg);

            // Save the cashflow
            Cashflow savedCashflow = cashflowRepository.save(newCf);

            // Add to both collections
            newCashflows.add(savedCashflow);
            newLeg.getCashflows().add(savedCashflow);
        }

        // Save the leg with its associated cashflows to ensure the relationship is persisted
        TradeLeg legWithCashflows = tradeLegRepository.save(newLeg);

        // Log the completion of cashflow copying
        logger.info("Successfully copied {} cashflows for leg {}",
                   legWithCashflows.getCashflows() != null ? legWithCashflows.getCashflows().size() : 0,
                   legWithCashflows.getLegId());

        return newCashflows;
    }

    @Transactional
    public Trade saveTrade(Trade trade, TradeDTO dto) {
        logger.info("Saving trade: {}", trade);
        LocalDateTime now = LocalDateTime.now();
        boolean isAmend = false;
        Trade previousLatest = null;

        var statuses = tradeStatusRepository.findAll();

        // Get LIVE and DEAD statuses once
        TradeStatus liveStatus = statuses.stream()
            .filter(ts -> "LIVE".equalsIgnoreCase(ts.getTradeStatus()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("LIVE trade status not found"));

        TradeStatus deadStatus = statuses.stream()
            .filter(ts -> "DEAD".equalsIgnoreCase(ts.getTradeStatus()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("DEAD trade status not found"));

        // CASE 1: New trade (no tradeId)
        if (trade.getTradeId() == null) {
            Long maxTradeId = tradeRepository.findMaxTradeId().orElse(1000L);
            trade.setTradeId(maxTradeId + 1);
            trade.setVersion(1);
            trade.setValidityStartDate(now);
            trade.setValidityEndDate(null);

            // Set validity start date for all legs and cashflows
            if (trade.getTradeLegs() != null) {
                for (TradeLeg leg : trade.getTradeLegs()) {
                    leg.setValidityStartDate(now);
                    leg.setValidityEndDate(null);

                    if (leg.getCashflows() != null) {
                        for (Cashflow cf : leg.getCashflows()) {
                            cf.setValidityStartDate(now);
                            cf.setValidityEndDate(null);
                        }
                    }
                }
            }

            // Always set status to LIVE for new trades
            trade.setTradeStatus(liveStatus);

            // For new trades, save as-is (with whatever legs were provided in the DTO)
            return tradeRepository.save(trade);
        }
        // CASE 2: Existing trade (has tradeId)
        else {
            var existingTrades = tradeRepository.findByTradeId(trade.getTradeId());

            // If records exist, handle as an amendment
            if (!existingTrades.isEmpty()) {
                // Find latest version of the trade
                previousLatest = existingTrades.stream()
                    .max((a, b) -> Integer.compare(a.getVersion(), b.getVersion()))
                    .orElseThrow(() -> new IllegalStateException("Could not find latest version"));

                // Mark previous version as DEAD
                previousLatest.setTradeStatus(deadStatus);
                previousLatest.setValidityEndDate(now);

                // Also set validity end date for all legs and cashflows in the previous version
                if (previousLatest.getTradeLegs() != null) {
                    for (TradeLeg leg : previousLatest.getTradeLegs()) {
                        leg.setValidityEndDate(now);

                        if (leg.getCashflows() != null) {
                            for (Cashflow cf : leg.getCashflows()) {
                                cf.setValidityEndDate(now);
                            }
                        }
                    }
                }

                tradeRepository.save(previousLatest);

                // Set up new version
                trade.setVersion(previousLatest.getVersion() + 1);
                isAmend = true;
            }
            // TradeID exists but no records found (unusual case)
            else {
                trade.setVersion(1);
            }

            trade.setValidityStartDate(now);
            trade.setValidityEndDate(null);

            // Always set status to LIVE for amendments
            trade.setTradeStatus(liveStatus);

            // CRITICAL: For amendments, we need to handle the legs differently
            if (isAmend && previousLatest != null) {
                // Initialize trade legs collection if needed
                if (trade.getTradeLegs() == null) {
                    trade.setTradeLegs(new ArrayList<>());
                }

                // Process the incoming legs from the DTO
                for (TradeLeg leg : trade.getTradeLegs()) {
                    // Set validity dates for each leg
                    leg.setValidityStartDate(now);
                    leg.setValidityEndDate(null);

                    // Set the trade reference (parent-child relationship)
                    leg.setTrade(trade);

                    // Process cashflows if any
                    if (leg.getCashflows() != null) {
                        for (Cashflow cf : leg.getCashflows()) {
                            cf.setValidityStartDate(now);
                            cf.setValidityEndDate(null);
                            cf.setLeg(leg);
                        }
                    }
                }

                // Save the amended trade with its legs
                logger.info("Saving amended trade {} with version {} and {} legs",
                    trade.getTradeId(), trade.getVersion(),
                    trade.getTradeLegs() != null ? trade.getTradeLegs().size() : 0);

                Trade savedTrade = tradeRepository.save(trade);

                logger.info("Completed amendment for trade {} version {} with {} legs",
                    savedTrade.getTradeId(), savedTrade.getVersion(),
                    savedTrade.getTradeLegs() != null ? savedTrade.getTradeLegs().size() : 0);

                return savedTrade;
            } else {
                // If it's not really an amendment or there are no legs to copy
                return tradeRepository.save(trade);
            }
        }
    }

    public void deleteTrade(Long id) {
        logger.warn("Deleting trade with id: {}", id);
        tradeRepository.deleteById(id);
    }


    @Transactional
    public Trade terminateTrade(Long id) {
        logger.info("Terminating trade with ID: {}", id);
        LocalDateTime now = LocalDateTime.now();

        // Find the active trade (one without validity end date)
        Trade trade = getTradeById(id)
            .orElseThrow(() -> new IllegalStateException("Trade not found with ID: " + id));

        // Check if trade is already terminated (has validityEndDate)
        if (trade.getValidityEndDate() != null) {
            throw new IllegalStateException("Trade is already terminated: " + id);
        }

        // Get DEAD status
        TradeStatus deadStatus = tradeStatusRepository.findAll().stream()
            .filter(ts -> "DEAD".equalsIgnoreCase(ts.getTradeStatus()))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("DEAD trade status not found"));

        // Set trade as terminated
        trade.setTradeStatus(deadStatus);
        trade.setValidityEndDate(now);

        // Set validity end dates for all trade legs and their cashflows
        if (trade.getTradeLegs() != null) {
            for (TradeLeg leg : trade.getTradeLegs()) {
                leg.setValidityEndDate(now);

                if (leg.getCashflows() != null) {
                    for (Cashflow cashflow : leg.getCashflows()) {
                        cashflow.setValidityEndDate(now);
                    }
                }
            }
        }

        // Save and return the terminated trade
        logger.info("Trade {} terminated successfully", id);
        return tradeRepository.save(trade);
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
