package com.example.takehometest.service;

import com.example.takehometest.dto.AveragePriceDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;

@Service
public class PriceService {

    private final ConcurrentSkipListMap<Long, List<Double>> priceMap = new ConcurrentSkipListMap<>();

    public void recordPrice(double price) {
        long timestamp = Instant.now().getEpochSecond();
        priceMap.computeIfAbsent(timestamp, k -> new ArrayList<>())
                .add(price);
        cleanupOldEntries();
    }

    public List<AveragePriceDto> getAveragePrices() {
        List<AveragePriceDto> result = new ArrayList<>();
        long now = Instant.now().getEpochSecond();
        long threshold = now - 30;
        for (long timestamp = now; timestamp > threshold; timestamp--) {
            List<Double> prices = priceMap.get(timestamp);
            double avg = 0.0;
            if (prices != null && !prices.isEmpty()) {
                avg = prices.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0.0);
            }
            result.add(new AveragePriceDto(Instant.ofEpochSecond(timestamp).toString(), avg));
        }
        return result;
    }

    private void cleanupOldEntries() {
        long threshold = Instant.now().getEpochSecond() - 30;
        priceMap.headMap(threshold).clear();
    }

}
