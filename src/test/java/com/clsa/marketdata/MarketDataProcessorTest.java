package com.clsa.marketdata;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;

class MarketDataProcessorTest {
    //private IMessageListener marketDataProcessor = new MarketDataProcessor();

    @Test
    void onMessage() {
        IMessageListener marketDataProcessor = new MarketDataProcessor();
        for (int i = 0; i < 5000; i++) {
            MarketData marketData = MarketData.builder().symbol("HKT").bid(i).ask(i).updateTime(LocalDateTime.now()).build();
            marketDataProcessor.onMessage(marketData);
        }
    }
}