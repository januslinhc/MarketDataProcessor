package com.clsa.marketdata;

import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * A class that handling market data feed
 */
public class MarketDataProcessor implements IMessageListener {
    private final PublishSubject<MarketData> dataFeed = PublishSubject.create();
    private final static int MAX_NO_OF_SYMBOLS_UPDATE_PER_TIMEWINDOW = 100;

    public MarketDataProcessor() {
        dataFeed
                // 1 second non-overlapping sliding window
                .buffer(1, TimeUnit.SECONDS)
                .subscribe(marketDataObservable -> {
                    // I use Map to group all market data received within 1 sliding window by the symbol String
                    // the best case of time complexity to update symbol record is O(1) in case there is no hash value collision
                    Map<String, MarketData> storage = new HashMap<>();
                    marketDataObservable
                            // I need to keep the order, so we use non-parallel forEach
                            // it is thread-safe and therefore I use HashMap
                            .forEach(marketData -> {
                                // only capture the first 100 symbols of their market data within the 1sec time-window
                                if (storage.containsKey(marketData.getSymbol()) || storage.keySet().size() < MAX_NO_OF_SYMBOLS_UPDATE_PER_TIMEWINDOW) {
                                    storage.put(marketData.getSymbol(), marketData);
                                }
                            });
                    // publish the latest data of first 100 symbols within the 1sec time-window
                    storage.keySet().forEach(s -> publishAggregatedMarketData(storage.get(s)));
                });
    }

    /**
     * Callback function for receiving incoming market data feed
     *
     * @param data Market data {@link MarketData}
     */
    @Override
    public void onMessage(MarketData data) {
        dataFeed.onNext(data); // non-blocking
    }

    /**
     * Publish aggregated and throttled market data
     *
     * @param data Market data {@link MarketData}
     */
    public void publishAggregatedMarketData(MarketData data) {
        // Do nothing, assume implemented.
    }
}
