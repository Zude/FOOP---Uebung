package expressions.binary;

import java.util.HashSet;
import java.util.Set;

import expressions.AbstractExpression;
import expressions.Expression;
import expressions.ExpressionWrapper;
import values.Value;

/**
 * Abstrakte Klasse f√ºr mathematische Ausdr√ºcke mit zwei Teilausdr√ºcken.
 * 
 * @author kar, mhe, Lars Sander, Alexander Loeffler
 * @param <V> Value
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
     * Gibt den linken Teilausdruck zur√ºck.
     * 
     * @return left Der linke Teilausdruck
     */
    public Expression<V> getLeftExpression() {
        return this.left;
    }

    /**
     * Gibt den rechten Teilausdruck zur√ºck.
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

    protected boolean checkCycle(Set<ExpressionWrapper<?>> checks) {

        /*
         * 1. Gibt es ein Set?
         * 
         * 2. Bin ich im Set?
         * 
         * 3. Packe mich als Wrapper ins Set
         * 
         * 4. ‹bergib das Set an Left und Right
         */

        // Eigene Instanz in Wrapper packen
        ExpressionWrapper<V> myself = new ExpressionWrapper<V>(this);

        // Wurde ein Set ¸bergeben?
        if (checks == null) {
            checks = new HashSet<ExpressionWrapper<?>>();
        }

        // Sich selbst ins Set einf¸gen, wenn false war die Instanz bereits vorhanden
        if (!checks.add(myself)) {
            return true;
        }

        if (right instanceof BinaryExpression<?> && left instanceof BinaryExpression<?>) {
            return ((BinaryExpression<?>) right).checkCycle(checks)
                    && ((BinaryExpression<?>) left).checkCycle(checks);
        }

        return false;
    }

}
