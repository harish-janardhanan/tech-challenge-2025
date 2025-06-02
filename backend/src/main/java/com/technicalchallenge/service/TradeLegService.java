package com.technicalchallenge.service;

import com.technicalchallenge.dto.TradeLegDTO;
import com.technicalchallenge.model.TradeLeg;
import com.technicalchallenge.repository.TradeLegRepository;
import com.technicalchallenge.repository.CurrencyRepository;
import com.technicalchallenge.repository.LegTypeRepository;
import com.technicalchallenge.repository.IndexRepository;
import com.technicalchallenge.repository.HolidayCalendarRepository;
import com.technicalchallenge.repository.ScheduleRepository;
import com.technicalchallenge.repository.BusinessDayConventionRepository;
import com.technicalchallenge.repository.PayRecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class TradeLegService {
    private static final Logger logger = LoggerFactory.getLogger(TradeLegService.class);

    @Autowired
    private TradeLegRepository tradeLegRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private LegTypeRepository legTypeRepository;
    @Autowired
    private IndexRepository indexRepository;
    @Autowired
    private HolidayCalendarRepository holidayCalendarRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private BusinessDayConventionRepository businessDayConventionRepository;
    @Autowired
    private PayRecRepository payRecRepository;

    public List<TradeLeg> getAllTradeLegs() {
        logger.info("Retrieving all trade legs");
        return tradeLegRepository.findAll();
    }

    public Optional<TradeLeg> getTradeLegById(Long id) {
        logger.debug("Retrieving trade leg by id: {}", id);
        return tradeLegRepository.findById(id);
    }

    public TradeLeg saveTradeLeg(TradeLeg tradeLeg, TradeLegDTO dto) {
        logger.info("Saving trade leg: {}", tradeLeg);
        // Ensure TradeLeg is saved with related entities set, not just IDs
        return tradeLegRepository.save(tradeLeg);
    }

    public void deleteTradeLeg(Long id) {
        logger.warn("Deleting trade leg with id: {}", id);
        tradeLegRepository.deleteById(id);
    }

    // Business logic: notional > 0, trade, currency, legRateType required (enforced in controller)

    public void populateReferenceDataByName(TradeLeg tradeLeg, TradeLegDTO dto) {
        if (dto.getCurrency() != null) {
            var currency = currencyRepository.findAll().stream()
                .filter(c -> c.getCurrency().equalsIgnoreCase(dto.getCurrency()))
                .findFirst().orElse(null);
            if (currency == null) throw new IllegalArgumentException("Currency '" + dto.getCurrency() + "' does not exist");
            tradeLeg.setCurrency(currency);
        }
        if (dto.getLegRateType() != null) {
            var legType = legTypeRepository.findAll().stream()
                .filter(l -> l.getType().equalsIgnoreCase(dto.getLegRateType()))
                .findFirst().orElse(null);
            if (legType == null) throw new IllegalArgumentException("LegType '" + dto.getLegRateType() + "' does not exist");
            tradeLeg.setLegRateType(legType);
        }
        if (dto.getIndex() != null) {
            var index = indexRepository.findAll().stream()
                .filter(i -> i.getIndex().equalsIgnoreCase(dto.getIndex()))
                .findFirst().orElse(null);
            if (index == null) throw new IllegalArgumentException("Index '" + dto.getIndex() + "' does not exist");
            tradeLeg.setIndex(index);
        }
        if (dto.getHolidayCalendar() != null) {
            var holidayCalendar = holidayCalendarRepository.findAll().stream()
                .filter(h -> h.getHolidayCalendar().equalsIgnoreCase(dto.getHolidayCalendar()))
                .findFirst().orElse(null);
            if (holidayCalendar == null) throw new IllegalArgumentException("HolidayCalendar '" + dto.getHolidayCalendar() + "' does not exist");
            tradeLeg.setHolidayCalendar(holidayCalendar);
        }
        if (dto.getCalculationPeriodSchedule() != null) {
            var schedule = scheduleRepository.findAll().stream()
                .filter(s -> s.getSchedule().equalsIgnoreCase(dto.getCalculationPeriodSchedule()))
                .findFirst().orElse(null);
            if (schedule == null) throw new IllegalArgumentException("Schedule '" + dto.getCalculationPeriodSchedule() + "' does not exist");
            tradeLeg.setCalculationPeriodSchedule(schedule);
        }
        if (dto.getPaymentBusinessDayConvention() != null) {
            var paymentBDC = businessDayConventionRepository.findAll().stream()
                .filter(b -> b.getBdc().equalsIgnoreCase(dto.getPaymentBusinessDayConvention()))
                .findFirst().orElse(null);
            if (paymentBDC == null) throw new IllegalArgumentException("PaymentBusinessDayConvention '" + dto.getPaymentBusinessDayConvention() + "' does not exist");
            tradeLeg.setPaymentBusinessDayConvention(paymentBDC);
        }
        if (dto.getFixingBusinessDayConvention() != null) {
            var fixingBDC = businessDayConventionRepository.findAll().stream()
                .filter(b -> b.getBdc().equalsIgnoreCase(dto.getFixingBusinessDayConvention()))
                .findFirst().orElse(null);
            if (fixingBDC == null) throw new IllegalArgumentException("FixingBusinessDayConvention '" + dto.getFixingBusinessDayConvention() + "' does not exist");
            tradeLeg.setFixingBusinessDayConvention(fixingBDC);
        }
        if (dto.getPayReceiveFlag() != null) {
            var payRec = payRecRepository.findAll().stream()
                .filter(p -> p.getPayRec().equalsIgnoreCase(dto.getPayReceiveFlag()))
                .findFirst().orElse(null);
            if (payRec == null) throw new IllegalArgumentException("PayRec '" + dto.getPayReceiveFlag() + "' does not exist");
            tradeLeg.setPayReceiveFlag(payRec);
        }
    }
}
