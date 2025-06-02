package com.technicalchallenge.service;

import com.technicalchallenge.model.TradeLeg;
import com.technicalchallenge.repository.TradeLegRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TradeLegServiceTest {
    @Mock
    private TradeLegRepository tradeLegRepository;
    @InjectMocks
    private TradeLegService tradeLegService;

    @Test
    void testFindTradeLegById() {
        TradeLeg tradeLeg = new TradeLeg();
        tradeLeg.setId(1L);
        when(tradeLegRepository.findById(1L)).thenReturn(Optional.of(tradeLeg));
        Optional<TradeLeg> found = tradeLegService.findById(1L);
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
    }
    // Add more tests for save, update, delete
}
