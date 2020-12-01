package com.clsa.marketdata;

import com.google.inject.Inject;

import java.time.LocalDateTime;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class MarketDataProcessor implements IMarketDataProcessor {
    private final HashMap<String, MarketData> dataHeap = new HashMap<>();
    private IRateLimiter rateLimiter = new RateLimiter(100);
    private Deque<String> updated = new LinkedList<>();
    private com.google.common.util.concurrent.RateLimiter rateLimiter2 = com.google.common.util.concurrent.RateLimiter.create(100);

    /**
     * Receive incoming market data
     *
     * @param data
     */
    @Override
    public void onMessage(MarketData data) {
        //System.out.println(data.getAsk());
        rateLimiter.acquire();
        if (!updated.contains(data.getSymbol())) {
            updated.add(data.getSymbol());
            publishAggregatedMarketData(data);
        }
        dataHeap.put(data.getSymbol(), data);
        if (!updated.contains(data.getSymbol())) {
            updated.add(data.getSymbol());
        }
        if (!rateLimiter2.tryAcquire()) {
            rateLimiter2.acquire();
            //System.out.println(updated.size());
            System.out.println(LocalDateTime.now());
            String symbol;
            while (!updated.isEmpty()) {
                symbol = updated.poll();
                publishAggregatedMarketData(dataHeap.get(symbol));
            }
        }
    }

    /**
     * Publish aggregated and throttled market data
     *
     * @param data
     */
    public void publishAggregatedMarketData(MarketData data) {
        System.out.println(data);
        // Do Nothing, assume implemented.
    }
}
