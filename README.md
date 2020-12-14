# Market Data Aggregation and Throttle Control (Solution 2)

[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=januslinhc_MarketDataProcessor&metric=alert_status)](https://sonarcloud.io/dashboard?id=januslinhc_MarketDataProcessor) [![](https://github.com/januslinhc/MarketDataProcessor/workflows/build/badge.svg)](https://github.com/januslinhc/MarketDataProcessor)

## Description

This is the solution 2 based on another understanding of "Latest". Unlike the previous version (~main branch), which
assumed the "Latest" means the update data should be the latest at the time the market data publishing (publishing
perspective) and also assumed that the publishing operation is not limited to happen at the end of each time window.

In this version, it is to demonstrate the another understanding of "Latest", which refer to the latest market records in
each 1-sec time-window (time-window perspective). I have to collect all entries within the 1-sec time window first (as
we don't know is there further update within the time-window in the future), and then find the first 100 stock symbols
of the latest market records to publish.

## Prerequisite

- Java version 8/11/+

## Requirements

- Ensure that the publishAggregatedMarketData method is not called any more than 100 times/sec where this period is a
  sliding window.
- Ensure each symbol will not have more than one update per second
- Ensure each symbol will always have the latest market data when it is published

## Assumptions

- All calls to the onMessage method are made by one thread.
- The MarketDataProcessor receives messages from some source via the onMessage call back method. The incoming rate is
  unknown.
- Each second only have 100 or less symbols updates received.
- If there is an exception that more than 100 unique symbols updates received within 1-second time window, only the
  first 100 unique symbols should be handled.
- "latest market data" refer to the latest market records in each 1-sec time-window (time-window perspective). I have to
  collect all entries within the 1-sec time window first (as we don't know is there further update within the
  time-window in the future), and then find the first 100 stock symbols of the latest market records to publish.
- May publishes the same price if the latest price update in previous time-window, and the latest price update in
  current time-window are the same.
- The publishing events only occur at the end of each time-window.
- We will not publish updates of current time-window if the program terminate before the current time-window end.

## How to run the test cases

- execute `./gradlew test`

## Development Approach

- We used non-blocking and functional approaches to implement the program.
- We followed TDD approach to write test cases first and then implement the business logic, to make sure the code is
  testable and clean.

## Quality Report

- Please click the Sonarcloud badge above.