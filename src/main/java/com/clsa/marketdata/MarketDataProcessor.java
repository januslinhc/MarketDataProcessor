package com.clsa.marketdata;

import com.google.inject.Inject;

import java.util.HashMap;

public class MarketDataProcessor implements IMarketDataProcessor {
    private final HashMap<String, MarketData> dataHeap = new HashMap<>();
    private IRateLimiter rateLimiter = new RateLimiter(100);

    /**
     * Receive incoming market data
     *
     * @param data
     */
    @Override
    public void onMessage(MarketData data) {
        while (!rateLimiter.acquire());
        System.out.println(data.getAsk());
        dataHeap.put(data.getSymbol(), data);
        publishAggregatedMarketData(data);
    }

    /**
     * Publish aggregated and throttled market data
     *
     * @param data
     */
    public void publishAggregatedMarketData(MarketData data) {
        // Do Nothing, assume implemented.
    }
}
