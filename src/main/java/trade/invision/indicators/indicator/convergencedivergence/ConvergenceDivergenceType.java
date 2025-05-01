package trade.invision.indicators.indicator.convergencedivergence;

/**
 * {@link ConvergenceDivergenceType} defines the types of convergence and divergence.
 */
public enum ConvergenceDivergenceType {

    /**
     * Positive convergence occurs when the <code>first</code> signal and <code>second</code> signal both increase
     * within a timeframe. In other words, <code>first</code> and <code>second</code> make higher highs.
     */
    POSITIVE_CONVERGENCE,

    /**
     * Negative convergence occurs when the <code>first</code> signal and <code>second</code> signal both decrease
     * within a timeframe. In other words, <code>first</code> and <code>second</code> make lower lows.
     */
    NEGATIVE_CONVERGENCE,

    /**
     * Positive divergence occurs when the <code>first</code> signal increases and the <code>second</code> signal
     * decreases within a timeframe. In other words, <code>first</code> makes higher highs while <code>second</code>
     * makes lower lows.
     */
    POSITIVE_DIVERGENCE,

    /**
     * Negative divergence occurs when the <code>first</code> signal decreases and the <code>second</code> signal
     * increases within a timeframe. In other words, <code>first</code> makes lower lows while <code>second</code> makes
     * higher highs.
     */
    NEGATIVE_DIVERGENCE
}
