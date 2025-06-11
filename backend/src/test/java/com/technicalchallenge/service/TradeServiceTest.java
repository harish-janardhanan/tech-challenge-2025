package com.technicalchallenge.service;

import com.technicalchallenge.dto.TradeDTO;
import com.technicalchallenge.model.*;
import com.technicalchallenge.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {
    @Mock
    private TradeRepository tradeRepository;

    @Mock
    private TradeTypeRepository tradeTypeRepository;

    @Mock
    private TradeSubTypeRepository tradeSubTypeRepository;

    @Mock
    private TradeStatusRepository tradeStatusRepository;

    @Mock
    private TradeLegRepository tradeLegRepository;

    @Mock
    private CashflowRepository cashflowRepository;

    @InjectMocks
    private TradeService tradeService;

    private Trade trade;
    private Trade amendedTrade;
    private List<Trade> tradeList;
    private TradeStatus liveStatus;
    private TradeStatus deadStatus;
    private TradeLeg tradeLeg1;
    private TradeLeg tradeLeg2;
    private Cashflow cashflow1;
    private Cashflow cashflow2;

    @BeforeEach
    void setUp() {
        // Set up TradeStatus objects
        liveStatus = new TradeStatus();
        liveStatus.setId(1L);
        liveStatus.setTradeStatus("LIVE");

        deadStatus = new TradeStatus();
        deadStatus.setId(2L);
        deadStatus.setTradeStatus("DEAD");

        // Set up a Trade
        trade = new Trade();
        trade.setId(1L);
        trade.setTradeId(1001L);
        trade.setVersion(1);
        trade.setTradeStatus(liveStatus);
        trade.setTradeDate(LocalDateTime.now());
        trade.setStartDate(LocalDateTime.now().plusDays(1));
        trade.setMaturityDate(LocalDateTime.now().plusYears(5));
        trade.setValidityStartDate(LocalDateTime.now().minusDays(1));
        trade.setValidityEndDate(null);

        // Create TradeLeg objects
        tradeLeg1 = new TradeLeg();
        tradeLeg1.setLegId(1L);
        tradeLeg1.setNotional(BigDecimal.valueOf(1000000.0));
        tradeLeg1.setRate(0.05);
        tradeLeg1.setTrade(trade);
        tradeLeg1.setValidityStartDate(LocalDateTime.now().minusDays(1));
        tradeLeg1.setValidityEndDate(null);

        tradeLeg2 = new TradeLeg();
        tradeLeg2.setLegId(2L);
        tradeLeg2.setNotional(BigDecimal.valueOf(1000000.0));
        tradeLeg2.setRate(0.03);
        tradeLeg2.setTrade(trade);
        tradeLeg2.setValidityStartDate(LocalDateTime.now().minusDays(1));
        tradeLeg2.setValidityEndDate(null);

        // Create Cashflow objects
        cashflow1 = new Cashflow();
        cashflow1.setId(1L);
        cashflow1.setPaymentValue(BigDecimal.valueOf(25000.0));
        cashflow1.setValueDate(LocalDate.now().plusMonths(6));
        cashflow1.setLeg(tradeLeg1);
        cashflow1.setValidityStartDate(LocalDateTime.now().minusDays(1));
        cashflow1.setValidityEndDate(null);

        cashflow2 = new Cashflow();
        cashflow2.setId(2L);
        cashflow2.setPaymentValue(BigDecimal.valueOf(25000.0));
        cashflow2.setValueDate(LocalDate.now().plusYears(1));
        cashflow2.setLeg(tradeLeg1);
        cashflow2.setValidityStartDate(LocalDateTime.now().minusDays(1));
        cashflow2.setValidityEndDate(null);

        // Set up lists
        List<Cashflow> cashflows1 = new ArrayList<>();
        cashflows1.add(cashflow1);
        cashflows1.add(cashflow2);
        tradeLeg1.setCashflows(cashflows1);

        List<TradeLeg> tradeLegs = new ArrayList<>();
        tradeLegs.add(tradeLeg1);
        tradeLegs.add(tradeLeg2);
        trade.setTradeLegs(tradeLegs);

        // Set up amended trade (for testing amendment logic)
        amendedTrade = new Trade();
        amendedTrade.setId(2L);
        amendedTrade.setTradeId(1001L); // Same tradeId as original
        amendedTrade.setVersion(2); // Incremented version
        amendedTrade.setTradeStatus(liveStatus);
        amendedTrade.setTradeDate(LocalDateTime.now());
        amendedTrade.setStartDate(LocalDateTime.now().plusDays(2)); // Changed value
        amendedTrade.setMaturityDate(LocalDateTime.now().plusYears(5));
        amendedTrade.setValidityStartDate(LocalDateTime.now());
        amendedTrade.setValidityEndDate(null);

        // Set up trade list for testing getAllTrades
        tradeList = Arrays.asList(trade, amendedTrade);
    }

    @Test
    void testGetAllTrades() {
        // Given
        when(tradeRepository.findAll()).thenReturn(tradeList);

        // When
        List<Trade> result = tradeService.getAllTrades();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(trade.getTradeId(), result.get(0).getTradeId());
        assertEquals(amendedTrade.getTradeId(), result.get(1).getTradeId());
        verify(tradeRepository).findAll();
    }

    @Test
    void testFindTradeById() {
        // Given
        when(tradeRepository.findByTradeId(1001L)).thenReturn(List.of(trade));

        // When
        Optional<Trade> result = tradeService.getTradeById(1001L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(1001L, result.get().getTradeId());
        verify(tradeRepository).findByTradeId(1001L);
    }

    @Test
    void testFindTradeByIdWithMultipleVersions() {
        // Given
        // Both trades are valid trade versions, but only the latest one (amendedTrade) should be returned
        // because it has null validityEndDate
        trade.setValidityEndDate(LocalDateTime.now()); // Mark the older version as no longer valid
        when(tradeRepository.findByTradeId(1001L)).thenReturn(Arrays.asList(trade, amendedTrade));

        // When
        Optional<Trade> result = tradeService.getTradeById(1001L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(1001L, result.get().getTradeId());
        assertEquals(2, result.get().getVersion());
        verify(tradeRepository).findByTradeId(1001L);
    }

    @Test
    void testFindTradeByNonExistentId() {
        // Given
        when(tradeRepository.findByTradeId(9999L)).thenReturn(new ArrayList<>());

        // When
        Optional<Trade> result = tradeService.getTradeById(9999L);

        // Then
        assertFalse(result.isPresent());
        verify(tradeRepository).findByTradeId(9999L);
    }

    @Test
    void testSaveNewTrade() {
        // Given
        Trade newTrade = new Trade();
        newTrade.setTradeDate(LocalDateTime.now());
        newTrade.setTradeLegs(Arrays.asList(tradeLeg1, tradeLeg2));

        when(tradeRepository.findMaxTradeId()).thenReturn(Optional.of(1000L));
        when(tradeRepository.save(any(Trade.class))).thenAnswer(invocation -> {
            Trade savedTrade = invocation.getArgument(0);
            savedTrade.setId(3L);
            return savedTrade;
        });
        when(tradeStatusRepository.findAll()).thenReturn(Arrays.asList(liveStatus, deadStatus));

        // When
        Trade savedTrade = tradeService.saveTrade(newTrade, null);

        // Then
        assertNotNull(savedTrade);
        assertEquals(1001L, savedTrade.getTradeId());
        assertEquals(1, savedTrade.getVersion());
        assertEquals(liveStatus, savedTrade.getTradeStatus());
        assertNotNull(savedTrade.getValidityStartDate());
        assertNull(savedTrade.getValidityEndDate());
        verify(tradeRepository).findMaxTradeId();
        verify(tradeRepository).save(any(Trade.class));
        verify(tradeStatusRepository).findAll();
    }

    @Test
    void testSaveAmendedTrade() {
        // Given
        Trade amendingTrade = new Trade();
        amendingTrade.setTradeId(1001L); // Same as existing trade
        amendingTrade.setTradeDate(LocalDateTime.now());
        amendingTrade.setStartDate(LocalDateTime.now().plusDays(3));
        amendingTrade.setTradeLegs(Arrays.asList(tradeLeg1, tradeLeg2));

        when(tradeRepository.findByTradeId(1001L)).thenReturn(Arrays.asList(trade));
        when(tradeStatusRepository.findAll()).thenReturn(Arrays.asList(liveStatus, deadStatus));
        when(tradeRepository.save(any(Trade.class))).thenAnswer(invocation -> {
            Trade savedTrade = invocation.getArgument(0);
            if (savedTrade == trade) {
                // Original trade being marked as dead
                savedTrade.setValidityEndDate(LocalDateTime.now());
                savedTrade.setTradeStatus(deadStatus);
            } else {
                // New version being saved
                savedTrade.setId(4L);
            }
            return savedTrade;
        });

        // When
        Trade result = tradeService.saveTrade(amendingTrade, null);

        // Then
        assertNotNull(result);
        assertEquals(1001L, result.getTradeId());
        assertEquals(2, result.getVersion()); // Version incremented
        assertEquals(liveStatus, result.getTradeStatus());
        assertNotNull(result.getValidityStartDate());
        assertNull(result.getValidityEndDate());
        verify(tradeRepository, times(2)).save(any(Trade.class)); // Once for old, once for new
        verify(tradeStatusRepository).findAll();
    }

    @Test
    void testDeleteTrade() {
        // Given
        Long tradeId = 1001L;
        doNothing().when(tradeRepository).deleteById(tradeId);

        // When
        tradeService.deleteTrade(tradeId);

        // Then
        verify(tradeRepository).deleteById(tradeId);
    }

    @Test
    void testTerminateTrade() {
        // Given
        Long tradeId = 1001L;
        when(tradeRepository.findByTradeId(tradeId)).thenReturn(List.of(trade));
        when(tradeStatusRepository.findAll()).thenReturn(Arrays.asList(liveStatus, deadStatus));
        when(tradeRepository.save(any(Trade.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        Trade terminatedTrade = tradeService.terminateTrade(tradeId);

        // Then
        assertNotNull(terminatedTrade);
        assertEquals(tradeId, terminatedTrade.getTradeId());
        assertEquals(deadStatus, terminatedTrade.getTradeStatus());
        assertNotNull(terminatedTrade.getValidityEndDate());

        // All trade legs and cashflows should be terminated as well
        for (TradeLeg leg : terminatedTrade.getTradeLegs()) {
            assertNotNull(leg.getValidityEndDate());

            if (leg.getCashflows() != null) {
                for (Cashflow cf : leg.getCashflows()) {
                    assertNotNull(cf.getValidityEndDate());
                }
            }
        }

        verify(tradeRepository).findByTradeId(tradeId);
        verify(tradeRepository).save(trade);
        verify(tradeStatusRepository).findAll();
    }

    @Test
    void testTerminateNonExistentTrade() {
        // Given
        Long tradeId = 9999L;
        when(tradeRepository.findByTradeId(tradeId)).thenReturn(new ArrayList<>());

        // When and Then
        assertThrows(IllegalStateException.class, () -> {
            tradeService.terminateTrade(tradeId);
        });

        verify(tradeRepository).findByTradeId(tradeId);
        verifyNoMoreInteractions(tradeRepository);
    }

    @Test
    void testTerminateAlreadyTerminatedTrade() {
        // Given
        Long tradeId = 1001L;
        trade.setValidityEndDate(LocalDateTime.now().minusDays(1)); // Already terminated
        when(tradeRepository.findByTradeId(tradeId)).thenReturn(new ArrayList<>()); // No active trade found

        // When and Then
        assertThrows(IllegalStateException.class, () -> {
            tradeService.terminateTrade(tradeId);
        });

        verify(tradeRepository).findByTradeId(tradeId);
        verifyNoMoreInteractions(tradeRepository);
    }
}
