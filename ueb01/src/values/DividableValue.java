package values;

import expressions.exceptions.DivByZeroException;

/**
 * Schnittstelle für teilbare Werte.
 * 
 * @author kar, mhe, Lars Sander, Alexander Loeffler
 * @param <V> Value
 */
public interface DividableValue<V extends DividableValue<V>> {
    /**
     * Gibt einen neuen Wert zurück, der durch die Division des aktuellen Wertes durch den
     * übergebenen Wert entsteht.
     * 
     * @param other Der Wert, durch den der aktuelle Wert dividiert werden soll
     * @return Das Ergebnis der Division
     * @throws DivByZeroException wenn durch null geteilt werden soll
     */
    V div(V other) throws DivByZeroException;
}
