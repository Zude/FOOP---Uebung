package expressions.binary;

import expressions.Expression;

/**
 * Ein Ausdruck für Division.
 * 
 * @author kar, mhe, ...
 */
public class DivExpression extends BinaryExpression {

    /**
     * Konstruktor.
     * 
     * @param left linker Teilausdruck
     * @param right rechter Teilausdruck
     */
    public DivExpression(Expression left, Expression right) {
        super(left, right);
    }

}
