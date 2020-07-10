package wson;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import wson.annotations.StoreAs;

/**
 * Eine Klasse zur Serialisierung und Deserialisierung von Java-Werten mittels JSON.
 *
 * @author kar, mhe, Lars Sander, Alexander Löffler
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
     * @return Der deserialisierte (Wurzel-)Wert
     * @throws JSONSyntaxException Syntax-Fehler bei der JSON-Verarbeitung
     * @pre json != null
     * @pre classOfT != null
     */
    public <T> T fromJson(String json, Class<T> classOfT) throws JSONSyntaxException {
        assert json != null;
        assert classOfT != null;

        return null;
    }

    private String toJsonHelper(Object src, Set<ReferenceWrapper> above) {
        above.add(new ReferenceWrapper(src));

        // TreeMap damit die String-Keys die natürliche Ordnung behalten
        final Map<String, String> jsonMap = new TreeMap<>();
        final JSONWriter w = new JSONWriter();

        // Listen
        if (Iterable.class.isAssignableFrom(src.getClass())) {
            return w.iterableToJson(src);
        }

        if (Map.class.isAssignableFrom(src.getClass())) {

            // TODO Auslagern nicht möglich, wegen dem toJsonHelper
            for (Object key : ((Map) src).keySet()) {
                jsonMap.put("\"" + w.strToJson(key.toString()) + "\":",
                        toJsonHelper(((Map) src).get(key), new HashSet<>(above)));

            }
            return jsonMap.toString();
        }

        // Arrays
        if (src.getClass().isArray()) {
            String arrName = src.getClass().getName();

            // TODO Auslagern nicht möglich, wegen dem toJsonHelper
            // Mehrdimensionale Arrays rekursiv aufrufen
            if (arrName.contains("[[")) {
                String[] arrRes = new String[Array.getLength(src)];
                for (int i = 0; i < Array.getLength(src); i++) {
                    arrRes[i] = toJsonHelper(Array.get(src, i), new HashSet<>(above));
                }
                return Arrays.toString(arrRes);
            } else {
                return "[" + w.arrToJson(src) + "]";
            }
        }

        if (src.getClass() == String.class) {
            return "\"" + w.strToJson(src.toString()) + "\"";
        }

        if (src.getClass() == Character.class) {
            return "\"" + src.toString() + "\"";
        }

        if (w.isPrimWrapper(src)) {
            return src.toString();
        }

        Set<Field> fieldsSet = w.getAllFields(src);

        // TODO irgendwie zu einem Stream machen...
        System.out.println("++++++++++++++" + src.getClass().toString());
        System.out.println("-------Felder: " + fieldsSet.size());

        for (Field cur : fieldsSet) {
            try {

                // TODO Streams -> ForEach
                // Gesicherte Felder verfügbar machen, check auf 0 für default package-private
                if (Modifier.isPrivate(cur.getModifiers())
                        || Modifier.isProtected(cur.getModifiers()) || cur.getModifiers() == 0) {
                    cur.setAccessible(true);
                }

                // TODO Streams -> Filter
                // Statische Felder und Anonymeklassen ignorieren und cyclische referenzen checken
                // TODO richtiger null check...
                if (Modifier.isStatic(cur.getModifiers())
                        || cur.get(src) != null && cur.get(src).getClass().isAnonymousClass()
                        || above.contains(new ReferenceWrapper(cur.get(src)))) {
                    System.out.println("Break for: " + cur);
                } else {
                    // Namen des aktuellen Feldes merken
                    String fName = "\"" + cur.getName() + "\":";
                    String fValues = null;

                    // TODO Streams -> Filter|ForEach
                    // Felder mit StoreAs Annotation überschreiben, wenn möglich
                    if (cur.isAnnotationPresent(StoreAs.class)) {
                        StoreAs rep = cur.getAnnotation(StoreAs.class);
                        if (rep != null) {
                            if (cur.getType().isAssignableFrom(String.class)) {
                                fValues = "\"" + rep.value() + "\"";
                            }
                        }
                    }

                    // Funktion rekursiv aufrufen
                    // Überprüfe das die Annotation den Wert nicht überschreibt und das der Wert
                    // initialisert wurde
                    // TODO besseres if...
                    // TODO Streams -> Filter
                    if (cur.get(src) != null || fValues != null) {
                        if (fValues == null) {
                            fValues = toJsonHelper(cur.get(src), new HashSet<>(above));
                        }
                        // Finale Map zusammenbauen
                        jsonMap.put(fName, fValues);
                    }

                }

            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException("Fehler beim erstellen der JSON Darstellung. Object: "
                        + src.toString() + " Feld: " + cur.toString());
            }

        }

        String result = jsonMap.toString();
        result = result.replace("=", "");
        result = result.replace(" ", "");

        System.out.println("Result: ");
        System.out.println(result);

        return result;
    }

    /**
     * Serialisiert einen Java-Wert zu einem JSON-String.
     *
     * @param src Zu serialisierender Wert
     * @return JSON-String (ohne unnötige Whitespaces)
     */
    public String toJson(Object src) {

        Set<ReferenceWrapper> cycle = new HashSet<>();

        return toJsonHelper(src, cycle);

    }
}
