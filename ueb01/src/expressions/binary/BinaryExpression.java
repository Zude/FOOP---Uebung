package expressions.binary;

import expressions.AbstractExpression;
import expressions.Expression;

/**
 * Abstrakte Klasse für mathematische Ausdrücke mit zwei Teilausdrücken.
 * 
 * @author kar, mhe, ...
 */
public abstract class BinaryExpression extends AbstractExpression {

    /**
     * Linker Teilausdruck.
     */
    protected Expression left;

    /**
     * Rechter Teilausdruck.
     */
    protected Expression right;

    /**
     * Konstruktor.
     * 
     * @param left linker Teilausdruck
     * @param right rechter Teilausdruck
     */
    public BinaryExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Gibt den linken Teilausdruck zurück.
     * 
     * @return left Der linke Teilausdruck
     */
    public Expression getLeftExpression() {
        return this.left;
    }

    /**
     * Gibt den rechten Teilausdruck zurück.
     * 
     * @return left Der rechte Teilausdruck
     */
    public Expression getRightExpression() {
        return this.right;
    }

    /**
     * Setzt den linken Teilausdruck.
     * 
     * @param left Der zu setzene linke Teilausdruck.
     */
    public void setLeftExpression(Expression left) {
        this.left = left;
    }

    /**
     * Setzt den rechten Teilausdruck.
     * 
     * @param right Der zu setzene rechte Teilausdruck.
     */
    public void setRightExpression(Expression right) {
        this.right = right;
    }


}
