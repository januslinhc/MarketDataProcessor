package com.clsa.marketdata;

public interface IMarketDataProcessor {
    void onMessage(MarketData data);
}
