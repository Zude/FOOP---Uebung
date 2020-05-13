package expressions.binary;

import context.Context;
import expressions.Expression;
import expressions.exceptions.ContextIncompleteException;
import expressions.exceptions.DivByZeroException;
import values.Value;

/**
 * Ein Ausdruck für Multiplikation.
 * 
 * @author kar, mhe, Lars Sander, Alexander Loeffler
 * @param <V> Value
 */
public class MulExpression<V extends Value<V>> extends BinaryExpression<V> {

    /**
     * Konstruktor.
     * 
     * @param left linker Teilausdruck
     * @param right rechter Teilausdruck
     */
    public MulExpression(Expression<V> left, Expression<V> right) {
        super(left, right);
    }

    @Override
    public V evaluate(Context<V> c) throws ContextIncompleteException, DivByZeroException {
        // TODO Auto-generated method stub
        return left.evaluate(c).mul(right.evaluate(c));
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
        builder.append(" * ");
        builder.append(right.toString());
        builder.append(")");
        return builder;
    }

}
