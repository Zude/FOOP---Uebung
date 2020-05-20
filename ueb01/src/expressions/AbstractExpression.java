package expressions;

import values.Value;

/**
 * Abstrakte Oberklasse zur Vermeidung von Codeverdoppelung.
 * 
 * @author kar, mhe, Lars Sander, Alexander Loeffler
 * @param <V> extends Value Der Ausdrucksbaum arbeitet mit eigens definierten Typen, daher werden
 *            nur Unterarten von Value erlaubt
 */
public abstract class AbstractExpression<V extends Value<V>> implements Expression<V> {

    @Override
    public String toString() {
        return toString(new StringBuilder()).toString();
    }

    // Folgende Methoden nutzen toString(), sind daher ineffizient und dürfen somit nur in Tests
    // verwendet werden:

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AbstractExpression)) {
            return false;
        }
        AbstractExpression<?> other = (AbstractExpression<?>) obj;
        return this.toString().equals(other.toString());
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

}
