package com.clsa.marketdata;

import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MarketDataProcessor implements IMessageListener {
    private final Map<String, MarketData> dataHeap = new ConcurrentHashMap<>();
    private IRateLimiter rateLimiter = new RateLimiter(100);
    private final Deque<String> updated = new ConcurrentLinkedDeque<>();

    public MarketDataProcessor() {
        // publishing thread
        ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.submit(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    if (!updated.isEmpty()) {
                        int count = 0;
                        String symbolToUpdate = updated.poll();
                        while (symbolToUpdate != null) {
                            count++;
                            publishAggregatedMarketData(dataHeap.get(symbolToUpdate));
                            if (count < 100) {
                                symbolToUpdate = updated.poll();
                            } else {
                                symbolToUpdate = null;
                            }
                        }
                        updated.clear();
                    }
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
        if (!updated.contains(data.getSymbol())) {
            updated.add(data.getSymbol());
        }
        dataHeap.put(data.getSymbol(), data);
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
