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

    /**
     * Ueberprueft ob wir im Baum Circles haben
     * 
     * @param checks Das Set wo wir die Werte zwischenspeichern
     * @return Die Antwort ob wir cycles im Baum haben
     */
    protected boolean checkCycle(Set<ExpressionWrapper<?>> checks) {

        // Eigene Instanz in Wrapper packen
        ExpressionWrapper<V> myself = new ExpressionWrapper<V>(this);
        boolean r = false;
        boolean l = false;

        // Wurde ein Set uebergeben?
        if (checks == null) {
            checks = new HashSet<ExpressionWrapper<?>>();
        }

        // Sich selbst ins Set einfuegen, wenn false war die Instanz bereits vorhanden
        if (!checks.add(myself)) {
            return true;
        }

        // Links und Rechts werden separat getrennt, falls einer von beiden bereits ein const/var
        // ist
        if (right instanceof BinaryExpression<?>) {
            r = ((BinaryExpression<?>) right).checkCycle(checks);

        }

        if (left instanceof BinaryExpression<?>) {
            l = ((BinaryExpression<?>) left).checkCycle(checks);
        }

        return r || l;
    }

}
