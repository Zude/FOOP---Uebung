package expressions;

/**
 * Ein konstanter Ausdruck (ein Literal).
 * 
 * @author kar, mhe, ...
 */
public class ConstExpression extends AbstractExpression {

    /**
     * Wert der Konstanten.
     */
    private final ... value;

    /**
     * Konstruktor.
     * 
     * @param value Wert des Ausdrucks
     */
    public ConstExpression(... value) {
        this.value = value;
    }


    @Override
    public StringBuilder toString(StringBuilder builder) {
        return builder.append(this.value);
    }

}
