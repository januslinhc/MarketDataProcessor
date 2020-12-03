package com.clsa.marketdata;

public interface IMessageListener {
    /**
     * Receive incoming market data
     *
     * @param data
     */
    void onMessage(MarketData data);
}
