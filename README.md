# Indicators

[![Maven Central Version](https://img.shields.io/maven-central/v/trade.invision/indicators)](https://central.sonatype.com/artifact/trade.invision/indicators)
[![javadoc](https://javadoc.io/badge2/trade.invision/indicators/javadoc.svg)](https://javadoc.io/doc/trade.invision/indicators)
[![GitHub License](https://img.shields.io/github/license/invision-trading/indicators)](https://github.com/invision-trading/indicators/blob/main/LICENSE.txt)

A Java library that provides a variety of technical [indicators](https://www.investopedia.com/terms/i/indicator.asp) for
time series data, such as [OHLC bars](https://www.investopedia.com/terms/c/candlestick.asp).

## Installation

For `build.gradle.kts`:

```kotlin
implementation("trade.invision", "indicators", "1.0.0")
```

For `build.gradle`:

```groovy
implementation group: 'trade.invision', name: 'indicators', version: '1.0.0'
```

For `pom.xml`:

```xml
<dependency>
    <groupId>trade.invision</groupId>
    <artifactId>indicators</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Usage

There are two base classes in this library to be aware of:
[`Series`](src/main/java/trade/invision/indicators/series/Series.java) and
[`Indicator`](src/main/java/trade/invision/indicators/indicator/Indicator.java).

`Series` is a class that provides a simple interface for storing and retrieving values from a series. Values
can only be added to the `Series`, except for the last value, which can be replaced with a new value. When the
number of values stored in the `Series` exceed a configurable maximum upon adding a new value, then values at
the beginning of the `Series` are removed. This emulates the behavior of a bar/candlestick chart that displays
historical bars along with a continuously-updating end bar that reflects live trade data. There are two types of
`Series` this library provides: [`BarSeries`](src/main/java/trade/invision/indicators/series/bar/BarSeries.java)
(for a series of [`Bars`](src/main/java/trade/invision/indicators/series/bar/Bar.java)) and
[`NumSeries`](src/main/java/trade/invision/indicators/series/num/NumSeries.java) (for a series of
[`NumDatapoints`](src/main/java/trade/invision/indicators/series/num/NumDatapoint.java)).

`Indicator` is an abstract class for performing calculations on a `Series` or another `Indicator`, with optional result
caching. An `Indicator` will implement some formula/algorithm and provide the result of the calculation at a given
index. Some `Indicator` implementations and consumer access patterns may retrieve calculated results at
previously-calculated indices. So, in order to prevent redundantly recalculating the results at the same `index`, the
`Indicator` can cache its results in memory, with the cache size being the same as the `Series` maximum length. Result
caching is disabled by default since the typical access pattern is to continually calculate the `Indicator` value at the
end index of a `Series` and perform some action based on that result. The cache should be enabled when consumers of an
`Indicator` use the calculated values of non-ending indices, as opposed to only using the calculated value of the end
index. `Indicator` implementations that utilize recursion should extend
[`RecursiveIndicator`](src/main/java/trade/invision/indicators/indicator/RecursiveIndicator.java), which forces caching
and prevents `StackOverflowError` exceptions. Typically, an `Indicator` will be one of the following three types:
[`Num`](https://javadoc.io/doc/trade.invision/num/latest/trade/invision/num/Num.html) (decimal number),
[`Boolean`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Boolean.html) (true/false), or
[`Instant`](https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/time/Instant.html) (timestamp). Each
`Indicator` implementation provides a public static method for consumers to acquire an instance reference to the
`Indicator`, as the constructor for `Indicator` implementations is `protected` and cannot be instantiated directly. 

Check out the [Javadoc](https://javadoc.io/doc/trade.invision/indicators) for all classes and method signatures, but
here's a simple example:

<details>
  <summary>Imports</summary>

  ```java
  import trade.invision.indicators.indicator.Indicator;
  import trade.invision.indicators.indicator.barprice.Hlc3;
  import trade.invision.indicators.indicator.barprice.Ohlc4;
  import trade.invision.indicators.indicator.bullishbearish.global.GlobalBullishPercentage;
  import trade.invision.indicators.indicator.ma.sma.SimpleMovingAverage;
  import trade.invision.indicators.indicator.statistical.StandardDeviation;
  import trade.invision.indicators.series.bar.Bar;
  import trade.invision.indicators.series.bar.BarSeries;
  import trade.invision.num.Num;
  import trade.invision.num.NumFactory;
  
  import java.time.Instant;
  
  import static java.time.temporal.ChronoUnit.MINUTES;
  import static trade.invision.indicators.indicator.bar.Close.close;
  import static trade.invision.indicators.indicator.barprice.Hlc3.typicalPrice;
  import static trade.invision.indicators.indicator.barprice.Ohlc4.ohlc4;
  import static trade.invision.indicators.indicator.bullishbearish.global.GlobalBullishPercentage.globalBullishPercentage;
  import static trade.invision.indicators.indicator.ma.MovingAverageSupplier.emaSupplier;
  import static trade.invision.indicators.indicator.ma.sma.SimpleMovingAverage.simpleMovingAverage;
  import static trade.invision.indicators.indicator.rsi.RelativeStrengthIndex.rsi;
  import static trade.invision.indicators.indicator.statistical.StandardDeviation.stddev;
  import static trade.invision.num.DecimalNum.decimalNum64Factory;
```
</details>

```java
final NumFactory numFactory = decimalNum64Factory();
final BarSeries barSeries = new BarSeries(20, numFactory);
// Add some random 'Bar' data to 'barSeries'.
for (int index = 0; index < barSeries.getMaximumLength(); index++) {
    final Instant start = Instant.parse("2025-01-01T00:00:00Z").plus(index, MINUTES);
    final Instant end = start.plus(1, MINUTES);
    final Num random = numFactory.of(index + 10).add(numFactory.random().multiply(10));
    final Num open = random.add(numFactory.random().subtract(numFactory.half()).multiply(5));
    final Num close = random.add(numFactory.random().subtract(numFactory.half()).multiply(5));
    final Num high = random.add(5);
    final Num low = random.subtract(5);
    final Num volume = numFactory.hundred();
    final Num tradeCount = numFactory.hundred();
    final Bar bar = new Bar(start, end, open, high, low, close, volume, tradeCount);
    barSeries.add(bar);
    System.out.println("Added: " + bar);
}

// Calculate the Simple Moving Average (SMA) using the bar average price.
final Ohlc4 averagePrice = ohlc4(barSeries);
final SimpleMovingAverage sma = simpleMovingAverage(averagePrice, 20);
for (long index = barSeries.getStartIndex(); index <= barSeries.getEndIndex(); index++) {
    System.out.printf("OHLC4 20 SMA at %d: %s\n", index, sma.getValue(index));
}

// Calculate the Standard Deviation (stddev) using the bar typical price.
final Hlc3 typicalPrice = typicalPrice(barSeries);
final StandardDeviation stddev = stddev(typicalPrice, 5, true);
for (long index = barSeries.getStartIndex(); index <= barSeries.getEndIndex(); index++) {
    System.out.printf("HLC3 5 STDDEV at %d: %s\n", index, stddev.getValue(index));
}

// Calculate the Relative Strength Index (RSI) using the bar close price and an Exponential Moving Average (EMA).
final Indicator<Num> close = close(barSeries);
final Indicator<Num> rsi = rsi(close, 10, emaSupplier());
for (long index = barSeries.getStartIndex(); index <= barSeries.getEndIndex(); index++) {
    System.out.printf("C 10 RSI at %d: %s\n", index, rsi.getValue(index));
}

// Calculate the percentage of bullish bars.
final GlobalBullishPercentage globalBullishPercentage = globalBullishPercentage(barSeries);
for (long index = barSeries.getStartIndex(); index <= barSeries.getEndIndex(); index++) {
    System.out.printf("Bullish %% at %d: %s%%\n", index, globalBullishPercentage.getValue(index).multiply(100).round());
}
```

## Acknowledgement

This library was inspired by the excellent [ta4j](https://github.com/ta4j/ta4j) library. There are several improvements
and additions that this library provides:

- Usage of improved `Num` interface via the [Num](https://github.com/invision-trading/num) library.
- Configurable `Indicator` result caching.
- `Indicator` instance caching.
- Several optimizations to reduce memory consumption and improve execution time for common access pattern (e.g.
  consecutive indices, identical indices, reusing `Indicator` instances with identical configurations).
- Configurable moving average types for `Indicator` implementations that depend on a moving average.
- Configurable epsilon per `Series`.
- Generic `Series` interface, allowing for `NumSeries` and such.
- Documentation improvements.
- Improved package structure.
- Several more helper/utility `Indicator` implementations.

## Contributing

Contributions are welcome. Use existing `Indicator` class implementations as a reference for new implementations. A few
guidelines for creating `Indicator` class implementations are as follows:

- There should only be one constructor and it should use the `protected` access modifier.
- Create public static methods for consumers to use for instantiation or retrieval from a weak-valued instance cache.
  The method name should be the full indicator name. Add overloads for acronyms.
- All implementations should implement a weak-valued instance cache, except for implementations that are subclasses of
  `CachelessIndicator` or `Num`/`Boolean`/`Instant` operations (e.g. `UnaryOperation`, `BinaryOperation`, and
  `TernaryOperation`).
- The class Javadoc should include an `@see` website reference for the indicator formula/algorithm.
- Avoid declarative calculations via the `UnaryOperation`, `BinaryOperation`, or `TernaryOperation` indicators. Prefer
  to imperatively calculate results in the `calculate` method.
- Avoid creating anonymous `Indicator` instances.
- Prefer to use `Num`, `Boolean`, or `Instant` as the type for the `Indicator`. Use `Num` even in cases where an
  `Indicator` only uses integer values. This greatly improves interoperability between `Indicator` implementations and
  is more convenient.
