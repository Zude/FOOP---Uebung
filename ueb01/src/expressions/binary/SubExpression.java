package expressions.binary;

import expressions.Expression;

/**
 * Ein Ausdruck für Subtraktion.
 * 
 * @author kar, mhe, ...
 */
public class SubExpression extends BinaryExpression {

    /**
     * Konstruktor.
     * 
     * @param left linker Teilausdruck
     * @param right rechter Teilausdruck
     */
    public SubExpression(Expression left, Expression right) {
        super(left, right);
    }


}
