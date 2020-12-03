package com.clsa.marketdata;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

class MarketDataProcessorTest {
    private final IMessageListener marketDataProcessor = new MarketDataProcessor() {
        @Override
        public void publishAggregatedMarketData(MarketData data) {
            super.publishAggregatedMarketData(data);
            System.out.println(data);
        }
    };

    @Test
    void onMessage() throws InterruptedException {
        final List<String> symbols = Arrays.asList("FB", "AMZN", "AAPL", "NFLX", "GOOG");
        for (int i = 1; i <= 100; i++) {
            for (int k = 0; k < symbols.size(); k++) {
                marketDataProcessor.onMessage(MarketData.builder().symbol(symbols.get(k)).bid(i).ask(i).updateTime(LocalDateTime.now()).build());
            }
            Thread.sleep(100);
        }
    }
}