package com.example.takehometest.controller;

import com.example.takehometest.dto.AddPriceRequestDto;
import com.example.takehometest.dto.AveragePriceDto;
import com.example.takehometest.service.PriceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PriceController {
    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping("/prices")
    public void addPrice(@RequestBody AddPriceRequestDto addPriceRequestDto) {
        if (addPriceRequestDto.getPrice() == null) {
            throw new IllegalArgumentException("Price must be provided");
        }
        priceService.recordPrice(addPriceRequestDto.getPrice());
    }

    @GetMapping("/average-prices")
    public List<AveragePriceDto> getAveragePrices() {
        return priceService.getAveragePrices();
    }
}
