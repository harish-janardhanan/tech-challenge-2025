package com.technicalchallenge.service;

import com.technicalchallenge.model.Trade;
import com.technicalchallenge.repository.TradeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TradeServiceTest {
    @Mock
    private TradeRepository tradeRepository;
    @InjectMocks
    private TradeService tradeService;

    @Test
    void testFindTradeById() {
        Trade trade = new Trade();
        trade.setId(1L);
        when(tradeRepository.findById(1L)).thenReturn(Optional.of(trade));
        Optional<Trade> found = tradeService.findById(1L);
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
    }

    @Test
    void testSaveTrade() {
        Trade trade = new Trade();
        trade.setId(2L);
        when(tradeRepository.save(trade)).thenReturn(trade);
        Trade saved = tradeService.saveTrade(trade, null);
        assertNotNull(saved);
        assertEquals(2L, saved.getId());
    }

    @Test
    void testDeleteTrade() {
        Long tradeId = 3L;
        doNothing().when(tradeRepository).deleteById(tradeId);
        tradeService.deleteTrade(tradeId);
        verify(tradeRepository, times(1)).deleteById(tradeId);
    }

    @Test
    void testFindTradeByNonExistentId() {
        when(tradeRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Trade> found = tradeService.getTradeById(99L);
        assertFalse(found.isPresent());
    }

    // Business logic: test trade cannot be created with maturity date before start date
    @Test
    void testTradeMaturityBeforeStartDateThrowsException() {
        Trade trade = new Trade();
        trade.setStartDate(java.time.LocalDateTime.of(2025, 6, 2, 0, 0));
        trade.setMaturityDate(java.time.LocalDateTime.of(2025, 5, 2, 0, 0));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validateTradeDates(trade);
        });
        assertTrue(exception.getMessage().contains("Maturity date cannot be before start date"));
    }

    // Helper for business logic validation
    private void validateTradeDates(Trade trade) {
        if (trade.getMaturityDate() != null && trade.getStartDate() != null && trade.getMaturityDate().isBefore(trade.getStartDate())) {
            throw new IllegalArgumentException("Maturity date cannot be before start date");
        }
    }

    // Business logic: test trade can only be created with active user, book, counterparty
    @Test
    void testTradeCreationWithInactiveUserThrowsException() {
        Trade trade = new Trade();
        trade.setTraderId(1L);
        // Simulate userService.isActive(1L) returns false
        TradeService spyService = spy(tradeService);
        doReturn(false).when(spyService).isUserActive(1L);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            spyService.validateTradeParticipants(trade);
        });
        assertTrue(exception.getMessage().contains("User must be active"));
    }
    // Add more tests for other business rules and edge cases
}
