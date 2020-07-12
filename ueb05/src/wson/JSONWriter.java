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

    /**
     * Sorgt für korrektes JSON bei Strings
     * 
     * @param str Quelle
     * @return String in JSON
     */
    public String strToJson(String str) {
        return escapeString(str);
    }

    /**
     * Parst den Inhalt von mehrdimensionalen Arrays zur einem JSON String
     * 
     * @param src Array
     * @return JSON-String
     */
    public String multiArrToJson(Object src) {
        String arrName = src.getClass().getName();
        // Mehrdimensionale Arrays rekursiv aufrufen
        if (arrName.contains("[[")) {
            String[] arrRes = new String[Array.getLength(src)];
            String res = "";
            for (int i = 0; i < Array.getLength(src); i++) {
                arrRes[i] = arrToJson(Array.get(src, i));
            }
            // Mehrere Arrays werden mit ", " getrennt
            return Arrays.toString(arrRes).replace(" ", "");
        } else {
            return arrToJson(src);
        }

    }

    /**
     * Parst den Inhalt von eindimensionalen Arrays zu JSON
     * 
     * @param obj Array
     * @return JSON-String
     */
    public String arrToJson(Object obj) {
        StringJoiner sj = new StringJoiner(",");

        for (int i = 0; i < Array.getLength(obj); i++) {
            sj.add(Array.get(obj, i).toString());
        }

        return "[" + sj.toString() + "]";
    }

    /**
     * Parst Listen und Sets als JSON String
     * 
     * @param obj Liste oder Set
     * @return JSON-String
     */
    public String iterableToJson(Object obj) {
        StringJoiner sj = new StringJoiner(",");

        for (Object o : Iterable.class.cast(obj)) {
            sj.add(o.toString());
        }
        return "[" + sj.toString() + "]";
    }

    /**
     * Überprüft ob das Object ein Wrapper für ein primitiv ist
     * 
     * @param src Wrapper
     * @return True wenn Wrapper
     */
    public boolean isPrimWrapper(Object src) {
        return (src.getClass() == Double.class || src.getClass() == Float.class
                || src.getClass() == Long.class || src.getClass() == Integer.class
                || src.getClass() == Short.class || src.getClass() == Byte.class
                || src.getClass() == Boolean.class || src.getClass() == Character.class);
    }

    /**
     * Findet alle Felder des übergebenen Objektes und seiner Superklassen
     * 
     * @param src Objekt das Felder enthält
     * @return Alle Felder
     */
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
