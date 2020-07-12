package wson;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.StringReader;

import java.util.stream.Collectors;

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

            return r.convert2(JSONParser.readElement(pbr), classOfT);

            // TODO JSONParser.readElement mit pbr aufrufen und Ergebnis mit JSONReader konvertieren
        } catch (IOException e) {
            throw new RuntimeException("not supposed to happen", e);
    private String toJsonHelper(Object src, Set<ReferenceWrapper> above) {
        final JSONWriter w = new JSONWriter();
        final Map<Object, String> jsonMap = new HashMap<>();

        if (Iterable.class.isAssignableFrom(src.getClass())) {
            return w.iterableToJson(src);
        }

        if (src.getClass().isArray()) {
            return w.multiArrToJson(src);
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

        if (Map.class.isAssignableFrom(src.getClass())) {
            for (Object key : ((Map) src).keySet()) {
                // String muss über toJson gebaut weren, weil alle Objekte erlaubt sind
                jsonMap.put(key, toJson(((Map) src).get(key)));
            }
            // Problem: In einer RawMap können Strings als Keys gespeichert werden, die beim
            // umwandeln zu Strings die Natürlicheordnung brechen.
            // sorted() ist an dieser Stelle nicht möglich, weil entweder die Objekte nicht
            // verglichen werden können oder weil die String Form die Ordnung stört.
            return jsonMap.keySet().stream()
                    .map(k -> "\"" + w.strToJson(k.toString()) + "\":" + jsonMap.get(k))
                    .collect(Collectors.joining(",", "{", "}"));

        }

        above.add(new ReferenceWrapper(src));
        Set<Field> fieldsSet = w.getAllFields(src);

        /*
         * for (Field cur : fieldsSet) {
         * 
         * // Gesicherte Felder verfügbar machen, check auf 0 für default package-private if
         * (Modifier.isPrivate(cur.getModifiers()) || Modifier.isProtected(cur.getModifiers()) ||
         * cur.getModifiers() == 0) { cur.setAccessible(true); }
         * 
         * try { // Statische Felder,Anonymeklassen und cyclische referenzen ignorieren if
         * (!Modifier.isStatic(cur.getModifiers()) && !above.contains(new
         * ReferenceWrapper(cur.get(src)))) { if (cur.get(src) == null ||
         * !cur.get(src).getClass().isAnonymousClass()) { String fValue;
         * 
         * // Felder mit StoreAs Annotation überschreiben, wenn möglich StoreAs rep =
         * cur.getAnnotation(StoreAs.class); if (rep != null &&
         * cur.getType().isAssignableFrom(String.class)) { fValue = "\"" + rep.value() + "\"";
         * jsonMap.put(cur.getName(), fValue); } else if (cur.get(src) != null) { fValue =
         * toJsonHelper(cur.get(src), new HashSet<>(above)); jsonMap.put(cur.getName(), fValue); }
         * 
         * } }
         * 
         * } catch (IllegalArgumentException | IllegalAccessException e) { throw new
         * RuntimeException("Fehler beim erstellen der JSON Darstellung. Object: " + src.toString()
         * + " Feld: " + cur.toString()); }
         * 
         * }
         */
        fieldsSet.stream()
                .filter(cur -> Modifier.isPrivate(cur.getModifiers())
                        || Modifier.isProtected(cur.getModifiers()) || cur.getModifiers() == 0)
                .forEach(cur -> cur.setAccessible(true));

        // Für jedes Feld den Inhalt in einer Map speichern, damit in den Filtern und dem Stream
        // später keine try/catch Blöcke geben muss. Die IllegalAccessException sollte eh niemals
        // geworfen werden, weil wir vorher wenn nötig setAccessible(true) aufgerufen haben
        Map<Object, Object> getMap = new HashMap<>();

        fieldsSet.stream().filter(cur -> !Modifier.isStatic(cur.getModifiers())).forEach(o -> {
            try {
                getMap.put(o, o.get(src));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        fieldsSet.stream().filter(cur -> !Modifier.isStatic(cur.getModifiers()))
                .filter(cur -> !above.contains(new ReferenceWrapper(getMap.get(cur))))
                .filter(cur -> getMap.get(cur) == null
                        || !getMap.get(cur).getClass().isAnonymousClass())
                .forEach(cur -> {
                    String fValue;
                    StoreAs rep = cur.getAnnotation(StoreAs.class);
                    if (rep != null && cur.getType().isAssignableFrom(String.class)) {
                        fValue = "\"" + rep.value() + "\"";
                        jsonMap.put(cur.getName(), fValue);
                    } else if (getMap.get(cur) != null) {
                        fValue = toJsonHelper(getMap.get(cur), new HashSet<>(above));
                        jsonMap.put(cur.getName(), fValue);
                    }
                });

        return jsonMap.keySet().stream().map(k -> "\"" + k + "\":" + jsonMap.get(k)).sorted()
                .collect(Collectors.joining(",", "{", "}"));
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
