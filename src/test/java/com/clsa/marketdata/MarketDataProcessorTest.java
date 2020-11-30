package com.clsa.marketdata;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MarketDataProcessorTest {
    private IMarketDataProcessor marketDataProcessor = new MarketDataProcessor();

    @Test
    void onMessage() {
        for (int i = 0; i < 5000; i++) {
            MarketData marketData = MarketData.builder().symbol("HKT").bid(i).ask(i).updateTime(LocalDateTime.now()).build();
            marketDataProcessor.onMessage(marketData);
        }
    }
}