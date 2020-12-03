package com.clsa.marketdata;

import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.concurrent.TimeUnit;

/**
 * A class that handling market data feed
 *
 */
public class MarketDataProcessor implements IMessageListener {
    private final PublishSubject<MarketData> dataFeed = PublishSubject.create();

    public MarketDataProcessor() {
        dataFeed
                // 1 second non-overlapping sliding window
                .window(1, TimeUnit.SECONDS)
                .subscribe(marketDataObservable -> marketDataObservable
                        // find the first unique stock update entries
                        .distinct(MarketData::getSymbol)
                        // take the first 100 entries
                        .take(100)
                        // update
                        .subscribe(this::publishAggregatedMarketData, Throwable::printStackTrace));
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
