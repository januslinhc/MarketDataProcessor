package com.clsa.marketdata;

public interface IMarketDataProcessor {
    /**
     * Receive incoming market data
     *
     * @param data
     */
    void onMessage(MarketData data);
}
