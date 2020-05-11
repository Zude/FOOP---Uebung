package expressions.binary;

import context.Context;
import expressions.Expression;
import expressions.exceptions.ContextIncompleteException;
import expressions.exceptions.DivByZeroException;
import values.Value;

/**
 * Ein Ausdruck für Addition.
 * 
 * @author kar, mhe, Lars Sander, Alexander Loeffler
 */
public class AddExpression<V extends Value<V>> extends BinaryExpression<V> {

    /**
     * Konstruktor.
     * 
     * @param left linker Teilausdruck
     * @param right rechter Teilausdruck
     */
    public AddExpression(Expression<V> left, Expression<V> right) {
        super(left, right);
    }

	@Override
	public V evaluate(Context<V> c) throws ContextIncompleteException, DivByZeroException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isConst() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasCycles() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public StringBuilder toString(StringBuilder builder) {
		// TODO Auto-generated method stub
		return null;
	}

    
    
}
