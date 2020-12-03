package com.clsa.marketdata;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        final IMessageListener marketDataProcessor = new MarketDataProcessor() {
            @Override
            public void publishAggregatedMarketData(MarketData data) {
                System.out.println(data);
            }
        };

        final List<String> symbols = Arrays.asList("FB", "AMZN", "AAPL", "NFLX", "GOOG");
        for (int i = 1; i <= 5000; i++) {
            for (int k = 0; k < symbols.size(); k++) {
                marketDataProcessor.onMessage(MarketData.builder().symbol(symbols.get(k)).bid(i).ask(i).updateTime(LocalDateTime.now()).build());
            }
            if (i % 100 == 0) {
                Thread.sleep(1000);
            }
        }
    }
}
