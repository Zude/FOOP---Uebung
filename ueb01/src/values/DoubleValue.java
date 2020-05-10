package values;

/**
 * Ein Wert für Gleitkommazahlen (double).
 * 
 * @author kar, mhe, ...
 */
public class DoubleValue extends Value implements DividableValue {
    /**
     * Wert der Gleitkommazahl.
     */
    private final double value;

    /**
     * Konstruktor.
     * 
     * @param value Der Wert der Gleitkommazahl
     */
    public DoubleValue(double value) {
        this.value = value;
    }

    /**
     * Gibt den Wert der Gleitkommazahl zurück.
     * 
     * @return Der Wert der Gleitkommazahl
     */
    public double getValue() {
        return value;
    }

    @Override
    public StringBuilder toString(StringBuilder builder) {
        return builder.append(this.value);
    }

}
