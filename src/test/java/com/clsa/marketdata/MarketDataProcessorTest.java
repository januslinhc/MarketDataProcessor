package com.clsa.marketdata;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;

class MarketDataProcessorTest {
    @ParameterizedTest(name = "when 101 symbols with {0} update entries each and each symbol update received per 100MS, {1} publishing in total is expected")
    @CsvSource({"10,100", "11,200"})
    void publishAggregatedMarketDataMethodIsNotCalledAnyMoreThan100TimesPerSec(int noOfSymbolRecords, int expectedTotalPublish) throws InterruptedException {
        List<MarketData> publishedEntries = new ArrayList<>();
        IMessageListener marketDataProcessor = new MarketDataProcessor() {
            @Override
            public void publishAggregatedMarketData(MarketData data) {
                super.publishAggregatedMarketData(data);
                publishedEntries.add(data);
            }
        };
        final List<String> symbols = Arrays.asList("A", "AAL", "AAP", "AAPL", "ABBV", "ABC", "ABMD", "ABT", "ACN", "ADBE", "ADI", "ADM", "ADP", "ADSK", "AEE", "AEP", "AES", "AFL", "AIG", "AIV", "AIZ", "AJG", "AKAM", "ALB", "ALGN", "ALK", "ALL", "ALLE", "ALXN", "AMAT", "AMCR", "AMD", "AME", "AMGN", "AMP", "AMT", "AMZN", "ANET", "ANSS", "ANTM", "AON", "AOS", "APA", "APD", "APH", "APTV", "ARE", "ATO", "ATVI", "AVB", "AVGO", "AVY", "AWK", "AXP", "AZO", "BA", "BAC", "BAX", "BBY", "BDX", "BEN", "BF", "BIIB", "BIO", "BK", "BKNG", "BKR", "BLK", "BLL", "BMY", "BR", "BRK", "BSX", "BWA", "BXP", "C", "CAG", "CAH", "CARR", "CAT", "CB", "CBOE", "CBRE", "CCI", "CCL", "CDNS", "CDW", "CE", "CERN", "CF", "CFG", "CHD", "CHRW", "CHTR", "CI", "CINF", "CL", "CLX", "CMA", "CMCSA");
        final int msSleepTimeForEachSymbolRecord = 100;
        generateData(marketDataProcessor, symbols, msSleepTimeForEachSymbolRecord, noOfSymbolRecords);
        Assert.assertEquals(expectedTotalPublish, publishedEntries.size());
    }

    private void generateData(IMessageListener marketDataProcessor, List<String> symbols, int sleepTimeForEachSymbolRecord, int noOfSymbolRecords) throws InterruptedException {
        for (int i = 1; i <= noOfSymbolRecords; i++) {
            int finalI = i;
            for (String symbol : symbols) {
                marketDataProcessor.onMessage(MarketData.builder().symbol(symbol).bid(finalI).ask(finalI).updateTime(LocalDateTime.now()).build());
            }
            Thread.sleep(sleepTimeForEachSymbolRecord);
        }
    }
}