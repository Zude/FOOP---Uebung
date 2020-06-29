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
        return null;
    }

    @Override
    public boolean isConst() {
        return false;
    }

    @Override
    public boolean hasCycles() {

        return false;
    }

    @Override
    public StringBuilder toString(StringBuilder builder) {

        return null;
    }

    @Override
    public boolean checkCycle(Set<ExpressionWrapper<?>> checked) {

        return false;
    }

}
