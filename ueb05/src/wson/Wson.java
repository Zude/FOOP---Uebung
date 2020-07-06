package wson;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.StringJoiner;

/**
 * Eine Klasse zur Serialisierung und Deserialisierung von Java-Werten mittels JSON.
 * 
 * @author kar, mhe, TODO Namen ergänzen
 * 
 */
public class Wson {
    /**
     * Konstruktor
     */
    public Wson() {
    }

    /**
     * Deserialisiert einen JSON-String zu einem Java-Wert.
     * 
     * @param json Zu deserialisierendes JSON. Muss gültig sein. Darf an erlaubten Stellen
     *            Whitespace enthalten. Werte für nicht in den Java-Klassen enthaltene Felder werden
     *            ignoriert.
     * @param classOfT Klasse des deserialisierten (Wurzel-)Wertes
     * @param <T> Typ des deserialisierten (Wurzel-)Wertes
     * @pre json != null
     * @pre classOfT != null
     * @return Der deserialisierte (Wurzel-)Wert
     * @throws JSONSyntaxException Syntax-Fehler bei der JSON-Verarbeitung
     */
    public <T> T fromJson(String json, Class<T> classOfT) throws JSONSyntaxException {
        assert json != null;
        assert classOfT != null;

        try {
            JSONReader r = new JSONReader();
            PushbackReader pbr = new PushbackReader(new StringReader(json));

            // TODO JSONParser.readElement mit pbr aufrufen und Ergebnis mit JSONReader konvertieren
        } catch (IOException e) {
            throw new RuntimeException("not supposed to happen", e);
        }

        return null;
    }

    /**
     * Serialisiert einen Java-Wert zu einem JSON-String.
     * 
     * @param src Zu serialisierender Wert
     * @return JSON-String (ohne unnötige Whitespaces)
     */
    public String toJson(Object src) {
        JSONWriter w = new JSONWriter();

        // TODO
        // Map in Writer damit die Ausgabe in der Korrekten reihenfolge erfolgt?
        /*
         * 1. Alle Variablen lesen 2. Entsprechende Writer funktion nutzen 3. Strings des Writers
         * zusammenbauen
         */
        StringJoiner res = new StringJoiner(",");
        String result = "";

        System.out.println("|-------------|");

        if (src.getClass().isArray()) {

        } else if (src.getClass() == String.class) {
            System.out.println("---String---");
            System.out.println("Value: " + w.simpleStrToJson(src.toString()));
            return "\"" + w.simpleStrToJson(src.toString()) + "\"";
        }
        // Check für den Fall das src ein Primitve Wrapper ist
        else if (src.getClass() == Double.class || src.getClass() == Float.class
                || src.getClass() == Long.class || src.getClass() == Integer.class
                || src.getClass() == Short.class || src.getClass() == Character.class
                || src.getClass() == Byte.class || src.getClass() == Boolean.class) {

            System.out.println("---Wrapper---");
            System.out.println("Value: " + w.simpleWrapperToJson(src));
            result = w.simpleWrapperToJson(src);

        } else {
            System.out.println("---Object---");
            // Ablauf für nicht primitive Typen
            Class<?> cl = src.getClass();

            Field[] fields = cl.getFields();

            // TODO Null ignorieren
            for (Field cur : fields) {
                System.out.println("--Field--  " + cur);

                try {
                    if (cur.get(src) == null) {
                        // TODO bessere Lösung als ein break für null werte
                        break;
                    }
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                if (cur.getType() == Double.class || cur.getType() == Float.class
                        || cur.getType() == Long.class || cur.getType() == Integer.class
                        || cur.getType() == Short.class || cur.getType() == Character.class
                        || cur.getType() == Byte.class || cur.getType() == Boolean.class) {

                    System.out.println("  -Wrapper-");
                    System.out.println("    Value: " + w.wrapperToJson(cur, src));
                    res.add(w.wrapperToJson(cur, src));
                }
                // Primitive in PrimitiveWrapper
                if (cur.getType().isPrimitive()) {
                    System.out.println("  -Primitive-");
                    System.out.println("    Value: " + w.primToJson(cur, src));
                    res.add(w.primToJson(cur, src));
                }

                // Strings
                if (cur.getType() == String.class) {
                    System.out.println("  -String-");
                    System.out.println("    Value: " + w.strToJson(cur, src));
                    res.add(w.strToJson(cur, src));
                }

                // Obj als Obj
                if (cur.getType() == Object.class) {
                    System.out.println("  -Object-");
                    System.out.println("    Value: " + w.objToJson(cur, src));
                    res.add(w.objToJson(cur, src));
                }

                // Collections

                // Arrays
                if (cur.getClass().isArray()) {

                }

                // TODO Check format
                result = "{" + res.toString() + "}";
            }
        }

        System.out.println("Result: ");
        System.out.println(result);
        System.out.println("|-------------|");
        return result;
    }

}
