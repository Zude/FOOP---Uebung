package expressions;

/**
 * Wrapper für Ausdrücke, dessen equals auf Referenzgleichheit testet. Wird genutzt, um einen
 * Ausdrucksbaum auf Zyklenfreiheit zu überprüfen.
 * 
 * @author kar, mhe, ...
 */
public class ExpressionWrapper extends AbstractExpression {

    /**
     * Der eingewickelte Ausdruck.
     */
    protected final Expression subExpression;

    /**
     * Konstruktor.
     * 
     * @param subExpression Der einzuwickelnde Ausdruck
     */
    public ExpressionWrapper(Expression subExpression) {
        this.subExpression = subExpression;
    }


    @Override
    public StringBuilder toString(StringBuilder builder) {
        return this.subExpression.toString(builder);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof ExpressionWrapper) {
            return this.subExpression == ((ExpressionWrapper) other).subExpression;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this.subExpression);
    }

}
