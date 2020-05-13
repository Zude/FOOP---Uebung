package expressions;

import context.Context;
import context.ElementNotFoundException;
import expressions.exceptions.ContextIncompleteException;
import expressions.exceptions.DivByZeroException;
import values.Value;

/**
 * Ein variabler Ausdruck.
 * 
 * @author kar, mhe, Lars Sander, Alexander Loeffler
 * @param <V> Value
 */
public class VarExpression<V extends Value<V>> extends AbstractExpression<V> {
    /**
     * Name der Variablen.
     */
    private final String varName;

    /**
     * Konstruktor.
     * 
     * @param varName Name der Variablen
     */
    public VarExpression(String varName) {
        this.varName = varName;
    }

    @Override
    public StringBuilder toString(StringBuilder builder) {
        return builder.append(this.varName);
    }

    @Override
    public V evaluate(Context<V> c) throws ContextIncompleteException, DivByZeroException {
        assert (c != null);

        // Ueberprueft of das Element zum Namen vorhanden ist
        try {
            return c.getValue(varName);
        } catch (ElementNotFoundException e) {
            throw new ContextIncompleteException("varName");
        }

    }

    @Override
    public boolean isConst() {
        return false;
    }

    @Override
    public boolean hasCycles() {
        return false;
    }

}
