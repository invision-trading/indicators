package trade.invision.indicators.series.bar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import trade.invision.num.Num;
import trade.invision.num.NumFactory;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;

import static java.time.Duration.between;

/**
 * {@link Bar} represents the price data of an instrument within a span of time. A bar may also be referred to as a
 * "candlestick" or "OHLC" (Open, High, Low, Close).
 *
 * @see <a href="https://www.investopedia.com/terms/c/candlestick.asp">Investopedia</a>
 */
@Value @AllArgsConstructor @Builder(toBuilder = true)
public class Bar {

    /**
     * The start timestamp of this {@link Bar} (inclusive).
     */
    Instant start;
    /**
     * The end timestamp of this {@link Bar} (exclusive).
     */
    Instant end;
    Num open;
    Num high;
    Num low;
    Num close;
    Num volume;
    long tradeCount;

    /**
     * Instantiates a new {@link Bar}.
     *
     * @param start       the start timestamp {@link Instant}
     * @param timespan    the timespan {@link Duration}
     * @param tradePrice  the trade price {@link Num}
     * @param tradeVolume the trade volume {@link Num}
     */
    public Bar(Instant start, Duration timespan, Num tradePrice, Num tradeVolume) {
        this(start, timespan, tradePrice, tradePrice, tradePrice, tradePrice, tradeVolume, 1);
    }

    /**
     * Instantiates a new {@link Bar}.
     *
     * @param start      the start timestamp {@link Instant}
     * @param timespan   the timespan {@link Duration}
     * @param open       the open {@link Num}
     * @param high       the high {@link Num}
     * @param low        the low {@link Num}
     * @param close      the close {@link Num}
     * @param volume     the volume {@link Num}
     * @param tradeCount the trade count
     */
    public Bar(Instant start, Duration timespan, Num open, Num high, Num low, Num close, Num volume, long tradeCount) {
        this.start = start;
        end = start.plus(timespan);
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.tradeCount = tradeCount;
    }

    /**
     * Gets the timespan (period) of this {@link Bar}.
     *
     * @return the {@link Duration}
     *
     * @see Duration#between(Temporal, Temporal)
     * @see #getStart()
     * @see #getEnd()
     */
    public Duration getTimespan() {
        return between(start, end);
    }

    /**
     * Calls {@link #addTrade(Num, Num)} with <code>volume</code> set to {@link Num#factory()}
     * {@link NumFactory#zero()}.
     */
    public Bar addPrice(Num price) {
        return addTrade(price, price.factory().zero());
    }

    /**
     * Creates a new {@link Bar} from this {@link Bar} with the new trade data.
     *
     * @param price  the price {@link Num}
     * @param volume the volume {@link Num}
     *
     * @return the new {@link Bar}
     */
    public Bar addTrade(Num price, Num volume) {
        return new Bar(start, end,
                open, high.maximum(price), low.minimum(price), price,
                this.volume.add(volume), tradeCount + 1);
    }

    /**
     * Creates a new {@link Bar} by aggregating this {@link Bar} and the given {@link Bar}.
     *
     * @param bar the {@link Bar}
     *
     * @return the new {@link Bar}
     */
    public Bar aggregate(Bar bar) {
        final boolean thisStart = !start.isAfter(bar.start);
        final boolean otherEnd = !bar.end.isBefore(end);
        return new Bar(thisStart ? start : bar.start, otherEnd ? bar.end : end,
                thisStart ? open : bar.open, high.maximum(bar.high), low.minimum(bar.low), otherEnd ? bar.close : close,
                this.volume.add(bar.volume), tradeCount + bar.tradeCount);
    }

    /**
     * Checks if the given {@link Instant} is within the {@link #getStart()} (inclusive) and {@link #getEnd()}
     * (exclusive).
     *
     * @param timestamp a timestamp {@link Instant}
     *
     * @return <code>true</code> if within, <code>false</code> otherwise
     */
    public boolean isWithinTimespan(Instant timestamp) {
        return !timestamp.isBefore(start) && timestamp.isBefore(end);
    }

    /**
     * Checks if the given {@link Bar} {@link #getStart()} (inclusive) is {@link #isWithinTimespan(Instant)} of this
     * {@link Bar} or if the given {@link Bar} {@link #getEnd()} (exclusive) is {@link #isWithinTimespan(Instant)} of
     * this {@link Bar}.
     *
     * @param bar a {@link Bar}
     *
     * @return <code>true</code> if within, <code>false</code> otherwise
     */
    public boolean isWithinTimespan(Bar bar) {
        return isWithinTimespan(bar.start) || isWithinTimespan(bar.end);
    }

    /**
     * Checks if this {@link Bar} is bullish ({@link #getClose()} is greater than {@link #getOpen()}).
     *
     * @return <code>true</code> if bullish, <code>false</code> otherwise
     */
    public boolean isBullish() {
        return close.isGreaterThan(open);
    }

    /**
     * Checks if this {@link Bar} is bearish ({@link #getClose()} is less than {@link #getOpen()}).
     *
     * @return <code>true</code> if bearish, <code>false</code> otherwise
     */
    public boolean isBearish() {
        return close.isLessThan(open);
    }
}
