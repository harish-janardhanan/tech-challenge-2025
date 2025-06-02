package com.technicalchallenge.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TradeLegController.class)
public class TradeLegControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllTradeLegs() throws Exception {
        mockMvc.perform(get("/api/tradelegs"))
                .andExpect(status().isOk());
    }
    // Add more tests for POST, PUT, DELETE as needed
}
