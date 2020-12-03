package com.clsa.marketdata;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

class MarketDataProcessorTest {
    private final IMessageListener marketDataProcessor = new MarketDataProcessor() {
        @Override
        public void publishAggregatedMarketData(MarketData data) {
            super.publishAggregatedMarketData(data);
            System.out.println(data);
        }
    };

    @Test
    void onMessage() {
        final List<String> symbols = Arrays.asList("FB", "AMZN", "AAPL", "NFLX", "GOOG");
        for (int i = 1; i <= 50; i++) {
            int finalI = i;
            await().atLeast(100, TimeUnit.MILLISECONDS).until(() -> {
                for (String symbol : symbols) {
                    marketDataProcessor.onMessage(MarketData.builder().symbol(symbol).bid(finalI).ask(finalI).updateTime(LocalDateTime.now()).build());
                }
                return true;
            });
        }
    }
}