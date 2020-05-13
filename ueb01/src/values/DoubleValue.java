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

        return builder.append(String.format("%.1f", this.value));
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

        double res = this.value / other.getValue();

        if (Double.isNaN(res) || Double.isInfinite(res)) {
            throw new DivByZeroException();
        }

        return new DoubleValue(res);
    }

}
