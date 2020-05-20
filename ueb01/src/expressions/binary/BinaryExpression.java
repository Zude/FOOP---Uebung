package expressions.binary;

import java.util.HashSet;
import java.util.Set;

import expressions.AbstractExpression;
import expressions.Expression;
import expressions.ExpressionWrapper;
import values.Value;

/**
 * Abstrakte Klasse für mathematische Ausdrücke mit zwei Teilausdrücken.
 * 
 * @author kar, mhe, Lars Sander, Alexander Loeffler
 * @param <V> extends Value Der Ausdrucksbaum arbeitet mit eigens definierten Typen, daher werden
 *            nur Unterarten von Value erlaubt
 */
public abstract class BinaryExpression<V extends Value<V>> extends AbstractExpression<V> {

    /**
     * Linker Teilausdruck.
     */
    protected Expression<V> left;

    /**
     * Rechter Teilausdruck.
     */
    protected Expression<V> right;

    /**
     * Konstruktor.
     * 
     * @param left linker Teilausdruck
     * @param right rechter Teilausdruck
     */
    public BinaryExpression(Expression<V> left, Expression<V> right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Gibt den linken Teilausdruck zurück.
     * 
     * @return left Der linke Teilausdruck
     */
    public Expression<V> getLeftExpression() {
        return this.left;
    }

    /**
     * Gibt den rechten Teilausdruck zurück.
     * 
     * @return left Der rechte Teilausdruck
     */
    public Expression<V> getRightExpression() {
        return this.right;
    }

    /**
     * Setzt den linken Teilausdruck.
     * 
     * @param left Der zu setzene linke Teilausdruck.
     */
    public void setLeftExpression(Expression<V> left) {
        this.left = left;
    }

    /**
     * Setzt den rechten Teilausdruck.
     * 
     * @param right Der zu setzene rechte Teilausdruck.
     */
    public void setRightExpression(Expression<V> right) {
        this.right = right;
    }

    @Override
    public boolean isConst() {
        return left.isConst() && right.isConst();
    }

    @Override
    public boolean hasCycles() {
        return checkCycle(null);
    }

    public boolean checkCycle(Set<ExpressionWrapper<?>> checked) {

        // Eigene Instanz in Wrapper packen
        ExpressionWrapper<V> myself = new ExpressionWrapper<V>(this);

        // Wurde ein Set uebergeben?
        if (checked == null) {
            checked = new HashSet<ExpressionWrapper<?>>();
        }

        // Sich selbst in die Sets einfuegen, wenn false war die Instanz bereits vorhanden
        if (!checked.add(myself)) {
            return true;
        }

        return right.checkCycle(checked) || left.checkCycle(checked);
    }

}
