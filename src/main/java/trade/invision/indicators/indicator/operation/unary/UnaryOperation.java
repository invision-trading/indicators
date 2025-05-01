package trade.invision.indicators.indicator.operation.unary;

import trade.invision.indicators.indicator.Indicator;

import java.util.function.Function;

/**
 * {@link UnaryOperation} is an {@link Indicator} that performs a unary operation on one input {@link Indicator}.
 *
 * @param <I> the input type
 * @param <R> the result type
 *
 * @see <a href="https://en.wikipedia.org/wiki/Unary_operation">Wikipedia</a>
 */
public class UnaryOperation<I, R> extends Indicator<R> {

    /**
     * Gets a {@link UnaryOperation}.
     *
     * @param operator the {@link Function}
     * @param operand  the {@link Indicator} operand
     */
    public static <I, R> UnaryOperation<I, R> unaryOperation(Function<I, R> operator, Indicator<I> operand) {
        return new UnaryOperation<>(operator, operand);
    }

    private final Function<I, R> operator;
    private final Indicator<I> operand;

    protected UnaryOperation(Function<I, R> operator, Indicator<I> operand) {
        super(operand.getSeries(), operand.getMinimumStableIndex());
        this.operator = operator;
        this.operand = operand;
    }

    @Override
    protected R calculate(long index) {
        return operator.apply(operand.getValue(index));
    }
}
