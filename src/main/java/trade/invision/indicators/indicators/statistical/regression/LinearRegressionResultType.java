package trade.invision.indicators.indicators.statistical.regression;

/**
 * {@link LinearRegressionResultType} defines {@link LinearRegression} result types.
 */
public enum LinearRegressionResultType {

    /**
     * The slope value.
     */
    SLOPE,

    /**
     * The intercept value.
     */
    INTERCEPT,

    /**
     * The Y value (at the <code>index</code>).
     */
    Y,

    /**
     * The next Y value (at <code>index + 1</code>).
     */
    NEXT_Y,

    /**
     * Residual sum of squares (RSS).
     */
    RSS,

    /**
     * Total sum of squares (TSS).
     */
    TSS,

    /**
     * Coefficient of determination (R^2).
     */
    R2
}
