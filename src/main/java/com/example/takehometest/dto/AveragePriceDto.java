package com.example.takehometest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NonNull
public class AveragePriceDto {
    private String timestamp;

    @JsonProperty("avg_price")
    private Double avgPrice;
}
