package expressions;

/**
 * Ein variabler Ausdruck.
 * 
 * @author kar, mhe, ...
 */
public class VarExpression extends AbstractExpression {
    /**
     * Name der Variablen.
     */
    private final String varName;

    /**
     * Konstruktor.
     * 
     * @param varName Name der Variablen
     */
    public VarExpression(String varName) {
        this.varName = varName;
    }

    @Override
    public StringBuilder toString(StringBuilder builder) {
        return builder.append(this.varName);
    }

}
