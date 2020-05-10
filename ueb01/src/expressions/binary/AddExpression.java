package expressions.binary;

import expressions.Expression;

/**
 * Ein Ausdruck für Addition.
 * 
 * @author kar, mhe, ...
 */
public class AddExpression extends BinaryExpression {

    /**
     * Konstruktor.
     * 
     * @param left linker Teilausdruck
     * @param right rechter Teilausdruck
     */
    public AddExpression(Expression left, Expression right) {
        super(left, right);
    }

}
