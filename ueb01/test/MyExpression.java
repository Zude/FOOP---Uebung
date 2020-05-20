import java.util.Set;

import context.Context;
import expressions.AbstractExpression;
import expressions.ExpressionWrapper;
import expressions.exceptions.ContextIncompleteException;
import expressions.exceptions.DivByZeroException;
import values.Value;

public class MyExpression<V extends Value<V>> extends AbstractExpression<V> {

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

    @Override
    public boolean checkCycle(Set<ExpressionWrapper<?>> checked) {
        // TODO Auto-generated method stub
        return false;
    }

}
