package expressions.binary;

import context.Context;
import expressions.Expression;
import expressions.exceptions.ContextIncompleteException;
import expressions.exceptions.DivByZeroException;
import values.DividableValue;
import values.Value;

/**
 * Ein Ausdruck für Division.
 * 
 * @author kar, mhe, Lars Sander, Alexander Loeffler
 * @param <V> Value
 */
public class DivExpression<V extends Value<V> & DividableValue<V>> extends BinaryExpression<V> {

    /**
     * Konstruktor.
     * 
     * @param left linker Teilausdruck
     * @param right rechter Teilausdruck
     */
    public DivExpression(Expression<V> left, Expression<V> right) {
        super(left, right);
    }

    @Override
    public V evaluate(Context<V> c) throws ContextIncompleteException, DivByZeroException {
        // V test = right.evaluate(c);

        // TODO Wann auf Null testen und exception werfen?

        // throw new DivByZeroException();

        return left.evaluate(c).div(right.evaluate(c));
    }

    @Override
    public boolean hasCycles() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public StringBuilder toString(StringBuilder builder) {
        assert (builder != null);
        builder.append("(");
        builder.append(left.toString());
        builder.append(" / ");
        builder.append(right.toString());
        builder.append(")");
        return builder;
    }

}
