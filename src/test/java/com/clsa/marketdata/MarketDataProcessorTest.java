package com.clsa.marketdata;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;

class MarketDataProcessorTest {
    private final IMessageListener marketDataProcessor = new MarketDataProcessor() {
        @Override
        public void publishAggregatedMarketData(MarketData data) {
            super.publishAggregatedMarketData(data);
        }
    };

    @Test
    void onMessage() {
        for (int i = 0; i < 5000; i++) {
            MarketData marketData = MarketData.builder().symbol("HKT").bid(i).ask(i).updateTime(LocalDateTime.now()).build();
            marketDataProcessor.onMessage(marketData);
        }
    }
}