package com.clsa.marketdata;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        IMessageListener marketDataProcessor = new MarketDataProcessor();
        for (int i = 0; i < 5000; i++) {
            MarketData marketData = MarketData.builder().symbol("HKT").bid(i + 1).ask(i + 1).updateTime(LocalDateTime.now()).build();
            marketDataProcessor.onMessage(marketData);

            if (i % 100 == 0) {
                Thread.sleep(1000);
            }
        }
    }
}
