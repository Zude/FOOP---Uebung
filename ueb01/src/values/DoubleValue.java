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
        assert (builder != null);

        return builder.append(this.value);
    }

    @Override
    public DoubleValue add(DoubleValue other) {
        assert (other != null);

        return new DoubleValue(this.value + other.getValue());
    }

    @Override
    public DoubleValue sub(DoubleValue other) {
        assert (other != null);

        return new DoubleValue(this.value - other.getValue());
    }

    @Override
    public DoubleValue mul(DoubleValue other) {
        assert (other != null);

        return new DoubleValue(this.value * other.getValue());
    }

    @Override
    public DoubleValue div(DoubleValue other) throws DivByZeroException {
        assert (other != null);

        // TODO: Geteilt durch 0 bei double
        if (other.getValue() == 0) {
            // System.out.print("Div bei 0 Test");
            throw new DivByZeroException();
        }

        return new DoubleValue(this.value / other.getValue());
    }

}
