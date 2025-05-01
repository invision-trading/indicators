package trade.invision.indicators.indicator.operation.ternary;

import trade.invision.indicators.indicator.Indicator;

import static java.lang.Math.max;

/**
 * {@link TernaryOperation} is an {@link Indicator} that performs a ternary operation on three input
 * {@link Indicator}s.
 *
 * @param <A> the first input type
 * @param <B> the second input type
 * @param <C> the third input type
 * @param <R> the result type
 *
 * @see <a href="https://en.wikipedia.org/wiki/Ternary_operation">Wikipedia</a>
 */
public class TernaryOperation<A, B, C, R> extends Indicator<R> {

    /**
     * Gets a {@link TernaryOperation}.
     *
     * @param operator      the {@link TriFunction}
     * @param firstOperand  the first {@link Indicator} operand
     * @param secondOperand the second {@link Indicator} operand
     * @param thirdOperand  the third {@link Indicator} operand
     */
    public static <A, B, C, R> TernaryOperation<A, B, C, R> ternaryOperation(TriFunction<A, B, C, R> operator,
            Indicator<A> firstOperand, Indicator<B> secondOperand, Indicator<C> thirdOperand) {
        return new TernaryOperation<>(operator, firstOperand, secondOperand, thirdOperand);
    }

    private final TriFunction<A, B, C, R> operator;
    private final Indicator<A> firstOperand;
    private final Indicator<B> secondOperand;
    private final Indicator<C> thirdOperand;

    protected TernaryOperation(TriFunction<A, B, C, R> operator,
            Indicator<A> firstOperand, Indicator<B> secondOperand, Indicator<C> thirdOperand) {
        super(firstOperand.getSeries(), max(firstOperand.getMinimumStableIndex(),
                max(secondOperand.getMinimumStableIndex(), thirdOperand.getMinimumStableIndex())));
        this.operator = operator;
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
        this.thirdOperand = thirdOperand;
    }

    @Override
    protected R calculate(long index) {
        return operator.apply(firstOperand.getValue(index),
                secondOperand.getValue(index), thirdOperand.getValue(index));
    }
}
