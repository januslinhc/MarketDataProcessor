package com.clsa.marketdata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Market Data Message Class
 */
@Data
@AllArgsConstructor
@Builder
public class MarketData {
    private String symbol;
    private double bid;
    private double ask;
    private double last;
    private LocalDateTime updateTime;
}
