package wson;

import examples.EBooleanNull;

/**
 * Enthält Hilfsmethoden für {@link Wson#fromJson} zur Konvertierung.
 * 
 * @author kar, mhe, TODO Namen ergänzen
 *
 */
class JSONReader {

    public <T> T convert(Object value, Class<T> classOfT) {

        if (Boolean.class.isInstance(value)) {

            return classOfT.cast(new Boolean((boolean) value));
        } else if (EBooleanNull.class.isInstance(value)) {

        }
        return null;
    }

}
