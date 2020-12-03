package com.clsa.marketdata;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class MarketDataProcessor implements IMessageListener {
    private final Map<String, Boolean> updated = new ConcurrentHashMap<>();
    private final AtomicInteger count = new AtomicInteger(0);

    public MarketDataProcessor() {
        // publishing thread
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    updated.clear();
                    count.set(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Receive incoming market data
     *
     * @param data
     */
    @Override
    public void onMessage(MarketData data) {
        if (count.get() < 100 && !updated.getOrDefault(data.getSymbol(), false)) {
            updated.put(data.getSymbol(), true);
            count.incrementAndGet();
            publishAggregatedMarketData(data);
        }
    }

    /**
     * Publish aggregated and throttled market data
     *
     * @param data
     */
    public void publishAggregatedMarketData(MarketData data) {
        System.out.println(data);
    }
}
