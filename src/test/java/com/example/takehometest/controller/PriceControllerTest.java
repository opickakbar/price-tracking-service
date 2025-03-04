package com.example.takehometest.controller;

import com.example.takehometest.dto.AddPriceRequestDto;
import com.example.takehometest.dto.AveragePriceDto;
import com.example.takehometest.service.PriceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PriceController.class)
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PriceService priceService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        Mockito.reset(priceService);
    }

    @Test
    void testAddPrice() throws Exception {
        AddPriceRequestDto request = new AddPriceRequestDto();
        request.setPrice(150.0);

        mockMvc.perform(post("/api/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(priceService, times(1)).recordPrice(150.0);
    }

    @Test
    void testAddPriceWithMissingValue() throws Exception {
        mockMvc.perform(post("/api/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // Empty JSON
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAveragePrices() throws Exception {
        List<AveragePriceDto> mockResponse = List.of(
                new AveragePriceDto("2025-01-01T12:01:31Z", 100.0),
                new AveragePriceDto("2025-01-01T12:01:30Z", 200.0)
        );

        when(priceService.getAveragePrices()).thenReturn(mockResponse);

        mockMvc.perform(get("/api/average-prices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].timestamp").value("2025-01-01T12:01:31Z"))
                .andExpect(jsonPath("$[0].avg_price").value(100.0))
                .andExpect(jsonPath("$[1].avg_price").value(200.0));

        verify(priceService, times(1)).getAveragePrices();
    }
}
