package trade.invision.indicators.indicators.operation.binary;

import trade.invision.indicators.indicators.Indicator;

import java.util.function.BiFunction;

import static java.lang.Math.max;

/**
 * {@link BinaryOperation} is an {@link Indicator} that performs a binary operation on two input {@link Indicator}s.
 *
 * @param <A> the first input type
 * @param <B> the second input type
 * @param <R> the result type
 *
 * @see <a href="https://en.wikipedia.org/wiki/Binary_operation">Reference</a>
 */
public class BinaryOperation<A, B, R> extends Indicator<R> {

    private final BiFunction<A, B, R> operator;
    private final Indicator<A> firstOperand;
    private final Indicator<B> secondOperand;

    /**
     * Instantiates a new {@link BinaryOperation}.
     *
     * @param operator      the {@link BiFunction}
     * @param firstOperand  the first {@link Indicator} operand
     * @param secondOperand the second {@link Indicator} operand
     */
    public BinaryOperation(BiFunction<A, B, R> operator, Indicator<A> firstOperand, Indicator<B> secondOperand) {
        super(firstOperand.getSeries(),
                max(firstOperand.getMinimumStableIndex(), secondOperand.getMinimumStableIndex()));
        this.operator = operator;
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }

    @Override
    protected R calculate(long index) {
        return operator.apply(firstOperand.getValue(index), secondOperand.getValue(index));
    }
}
