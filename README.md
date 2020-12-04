# Market Data Aggregation and Throttle Control

[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=januslinhc_MarketDataProcessor&metric=alert_status)](https://sonarcloud.io/dashboard?id=januslinhc_MarketDataProcessor) [![](https://github.com/januslinhc/MarketDataProcessor/workflows/build/badge.svg)](https://github.com/januslinhc/MarketDataProcessor)

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
- "latest market data" means the data I published were the most recent market data I received at that moment.
- May publishes the same price if the first price update in previous time-window, and the first price update in current
  time-window are the same.

## How to run the test cases

- execute `./gradlew test`

## Development Approach

- We used non-blocking and functional approaches to implement the program.
- We followed TDD approach to write test cases first and then implement the business logic, to make sure the code is
  testable and clean.

## Quality Report

- Please click the Sonarcloud badge above.