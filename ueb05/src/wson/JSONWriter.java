package wson;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Enthält Hilfsmethoden für {@link Wson#toJson}.
 * 
 * @author kar, mhe, Lars Sander, Alexander Löffler
 *
 */
class JSONWriter {
    /**
     * Maskiert Zeichen im übergebenen String gemäß der Vorgaben von JSON.
     * 
     * @param s Quellstring
     * @return String mit maskierten Zeichen
     */
    private String escapeString(String s) {
        s = s.replace("\\", "\\\\");
        s = s.replace("\"", "\\\"");
        // s = s.replace("/", "\\/"); // nur bei Rückübersetzung relevant, hier nicht nötig
        s = s.replace("\b", "\\b");
        s = s.replace("\f", "\\f");
        s = s.replace("\n", "\\n");
        s = s.replace("\r", "\\r");
        s = s.replace("\t", "\\t");
        // nicht-druckbare Zeichen werden ignoriert

        return s;
    }

    public String strToJson(String str) {
        return escapeString(str);
    }

    public String arrToJson(Object obj) {

        StringJoiner sj = new StringJoiner(",");

        for (int i = 0; i < Array.getLength(obj); i++) {
            sj.add(Array.get(obj, i).toString());
        }

        return sj.toString();
    }

    public String iterableToJson(Object obj) {
        StringJoiner sj = new StringJoiner(",");

        for (Object o : Iterable.class.cast(obj)) {
            sj.add(o.toString());
        }
        return "[" + sj.toString() + "]";
    }

    public boolean isPrimWrapper(Object src) {
        return (src.getClass() == Double.class || src.getClass() == Float.class
                || src.getClass() == Long.class || src.getClass() == Integer.class
                || src.getClass() == Short.class || src.getClass() == Byte.class
                || src.getClass() == Boolean.class);
    }

    public Set<Field> getAllFields(Object src) {
        Class<?> cl = src.getClass();

        Set<Field> fieldsSet = new HashSet<Field>();
        fieldsSet.addAll(Arrays.asList(cl.getFields()));
        fieldsSet.addAll(Arrays.asList(cl.getDeclaredFields()));

        // Private Felder aus Superklassen lesen
        while (cl.getSuperclass() != null) {
            cl = cl.getSuperclass();
            fieldsSet.addAll(Arrays.asList(cl.getDeclaredFields()));
        }

        return fieldsSet;
    }

}
