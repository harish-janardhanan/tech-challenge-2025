package com.technicalchallenge.controller;

import com.technicalchallenge.service.CounterpartyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CounterpartyController.class)
public class CounterpartyControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CounterpartyService counterpartyService;

    @Test
    void shouldReturnAllCounterparties() throws Exception {
        mockMvc.perform(get("/api/counterparties"))
                .andExpect(status().isOk());
    }
    // Add more tests for POST, PUT, DELETE as needed
}
