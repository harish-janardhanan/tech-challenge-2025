package com.technicalchallenge.controller;

import com.technicalchallenge.service.TradeLegService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TradeLegController.class)
public class TradeLegControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TradeLegService tradeLegService;

    @Test
    void shouldReturnAllTradeLegs() throws Exception {
        mockMvc.perform(get("/api/tradeLegs"))
                .andExpect(status().isOk());
    }
    // Add more tests for POST, PUT, DELETE as needed
}
