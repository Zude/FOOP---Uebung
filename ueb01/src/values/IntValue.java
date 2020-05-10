package values;


/**
 * Ein Wert für Ganzzahlen (int).
 * 
 * @author kar, mhe, ...
 */
public class IntValue extends Value {
    /**
     * Wert der Ganzzahl.
     */
    private final int value;

    /**
     * Konstruktor.
     * 
     * @param value Der Wert der Ganzzahlen
     */
    public IntValue(int value) {
        this.value = value;
    }

    /**
     * Gibt den Wert der Ganzzahl zurück.
     * 
     * @return Der Wert der Ganzzahl
     */
    public int getValue() {
        return value;
    }


    @Override
    public StringBuilder toString(StringBuilder builder) {
        return builder.append(this.value);
    }
    
}
