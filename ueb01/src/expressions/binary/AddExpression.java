package expressions.binary;

import context.Context;
import expressions.Expression;
import expressions.exceptions.ContextIncompleteException;
import expressions.exceptions.DivByZeroException;
import values.Value;

/**
 * Ein Ausdruck für Addition.
 * 
 * @author kar, mhe, Lars Sander, Alexander Loeffler
 * @param <V> extends Value Der Ausdrucksbaum arbeitet mit eigens definierten Typen, daher werden
 *            nur Unterarten von Value erlaubt
 */
public class AddExpression<V extends Value<V>> extends BinaryExpression<V> {

    /**
     * Konstruktor.
     * 
     * @param left linker Teilausdruck
     * @param right rechter Teilausdruck
     */
    public AddExpression(Expression<V> left, Expression<V> right) {
        super(left, right);
    }

    @Override
    public V evaluate(Context<V> c) throws ContextIncompleteException, DivByZeroException {

        return left.evaluate(c).add(right.evaluate(c));
    }

    @Override
    public StringBuilder toString(StringBuilder builder) {
        assert (builder != null);

        builder.append("(");
        builder.append(left.toString());
        builder.append(" + ");
        builder.append(right.toString());
        builder.append(")");
        return builder;
    }

}
