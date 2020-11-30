package com.clsa.marketdata;

import com.google.inject.Provides;

public class RateLimiter implements IRateLimiter {
    private final int MAXTimesPerSecond;
    private long timeWindowStartTime = -1;
    private int count = 0;
    private final static long ONE_SEC_NANO_TIME = 1_000_000_000;

    public RateLimiter(int maxCountPerSecond) {
        MAXTimesPerSecond = maxCountPerSecond;
    }

    @Override
    public boolean acquire() {
        try {
            long currentTime = System.nanoTime();
            if (timeWindowStartTime == -1) {
                count++;
                timeWindowStartTime = currentTime;
            } else if (currentTime - timeWindowStartTime <= ONE_SEC_NANO_TIME) {
                if (count < MAXTimesPerSecond) {
                    count++;
                } else {
                    Thread.sleep(((timeWindowStartTime + ONE_SEC_NANO_TIME) - currentTime) / 1_000_000);
                    return false;
                }
            } else {
                count = 1;
                timeWindowStartTime = currentTime;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
