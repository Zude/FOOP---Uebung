package values;

import expressions.exceptions.DivByZeroException;

/**
 * Ein Wert für Gleitkommazahlen (double).
 * 
 * @author kar, mhe, Lars Sander, Alexander Loeffler
 */
public class DoubleValue extends Value<DoubleValue> implements DividableValue<DoubleValue> {
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

    @Override
    public DoubleValue div(DoubleValue other) throws DivByZeroException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DoubleValue add(DoubleValue other) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DoubleValue mul(DoubleValue other) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DoubleValue sub(DoubleValue other) {
        // TODO Auto-generated method stub
        return null;
    }

}
