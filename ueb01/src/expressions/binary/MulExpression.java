package expressions.binary;

import expressions.Expression;

/**
 * Ein Ausdruck für Multiplikation.
 * 
 * @author kar, mhe, ...
 */
public class MulExpression extends BinaryExpression {

    /**
     * Konstruktor.
     * 
     * @param left linker Teilausdruck
     * @param right rechter Teilausdruck
     */
    public MulExpression(Expression left, Expression right) {
        super(left, right);
    }

}
