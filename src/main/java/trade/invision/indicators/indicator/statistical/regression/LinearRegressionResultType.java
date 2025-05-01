package trade.invision.indicators.indicator.statistical.regression;

/**
 * {@link LinearRegressionResultType} defines {@link LinearRegression} result types.
 */
public enum LinearRegressionResultType {

    /**
     * The slope.
     */
    SLOPE,

    /**
     * The <i>y</i>-intercept.
     */
    INTERCEPT,

    /**
     * The <i>y</i> value at <code>index</code>.
     */
    Y,

    /**
     * The <i>y</i> value at <code>index + 1</code>.
     */
    NEXT_Y,

    /**
     * The residual sum of squares (RSS).
     */
    RSS,

    /**
     * The total sum of squares (TSS).
     */
    TSS,

    /**
     * The coefficient of determination (R^2).
     */
    R2
}
