package wson;

/**
 * Enthält Hilfsmethoden für {@link Wson#fromJson} zur Konvertierung.
 * 
 * @author kar, mhe, TODO Namen ergänzen
 *
 */
class JSONReader {

    public <T> T convert(Object value, Class<T> classOfT) {
        // assert (value.getClass() == classOfT);

        if (value.getClass() == boolean.class) {
            if (value instanceof Boolean) {
                return classOfT.cast(new Boolean((boolean) value));
            }

        }
        return null;
    }

}
