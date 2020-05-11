package expressions;

import context.Context;
import expressions.exceptions.ContextIncompleteException;
import expressions.exceptions.DivByZeroException;
import values.Value;

/**
 * Ein konstanter Ausdruck (ein Literal).
 * 
 * @author kar, mhe, Lars Sander, Alexander Loeffler
 * @param <V> Value
 */
public class ConstExpression<V extends Value<V>> extends AbstractExpression<V> {

    /**
     * Wert der Konstanten.
     */
    private final V value;

    /**
     * Konstruktor.
     * 
     * @param value Wert des Ausdrucks
     */
    public ConstExpression(V value) {
        this.value = value;
    }

    @Override
    public StringBuilder toString(StringBuilder builder) {
        return builder.append(this.value);
    }

    @Override
    public V evaluate(Context<V> c) throws ContextIncompleteException, DivByZeroException {
        return value;
    }

    @Override
    public boolean isConst() {
        return true;
    }

    @Override
    public boolean hasCycles() {
        return false;
    }

}
