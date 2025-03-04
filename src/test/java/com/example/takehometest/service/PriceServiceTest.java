package com.example.takehometest.service;

import com.example.takehometest.dto.AveragePriceDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PriceServiceTest {

    private PriceService priceService;

    @BeforeEach
    void setUp() {
        priceService = new PriceService();
    }

    @Test
    void testRecordPriceAndRetrieveAverage() throws InterruptedException {
        //simulate price updates at different times
        Thread.sleep(1000);
        priceService.recordPrice(100.0);
        priceService.recordPrice(200.0);
        priceService.recordPrice(300.0);

        // just to separate these records into different batch
        Thread.sleep(3000);
        priceService.recordPrice(900.0);
        priceService.recordPrice(100.0);
        priceService.recordPrice(500.0);

        // retrieve and validate average prices
        List<AveragePriceDto> averagePrices = priceService.getAveragePrices();
        assertEquals(30, averagePrices.size());

        // find the non-zero avg prices and validate (there should be 500 and 200)
        List<Double> nonZeroPrices = averagePrices.stream()
                .map(AveragePriceDto::getAvgPrice)
                .filter(avgPrice -> avgPrice > 0)
                .toList();
        assertEquals(List.of(500.0, 200.0), nonZeroPrices);
    }

    @Test
    void testAveragePricesWhenNoData() {
        // retrieve average prices when no prices have been recorded
        List<AveragePriceDto> averagePrices = priceService.getAveragePrices();

        // ensure all 30 timestamps exist
        assertEquals(30, averagePrices.size());

        // ensure all prices are 0.0
        assertTrue(averagePrices.stream().allMatch(entry -> entry.getAvgPrice() == 0.0));
    }

    @Test
    void testCleanupOldEntries() throws InterruptedException {
        // record a price
        priceService.recordPrice(100.0);

        // wait for 31 seconds (to ensure data gets removed)
        Thread.sleep(31000);

        // retrieve average prices and ensure all prices are 0.0 after cleanup
        List<AveragePriceDto> averagePrices = priceService.getAveragePrices();
        assertTrue(averagePrices.stream().allMatch(entry -> entry.getAvgPrice() == 0.0));
    }
}
