package wson;

import java.lang.reflect.Field;

/**
 * Enthält Hilfsmethoden für {@link Wson#toJson}.
 * 
 * @author kar, mhe, TODO Namen ergänzen
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

    // TODO: Methoden zur Serialisierung hinzufügen

    // TODO
    // Writer sollte Strings in folgendem Format liefern:
    // "<name>":<value>
    // <value> kann O

    public String arrToJson() {

        return null;
    }

    public String objToJson(Field field, Object src) {
        System.out.println("...objToJson: " + field.getName());
        return "err in objToJson";
    }

    public String primToJson(Field field, Object src) {

        try {
            // TODO restliche Primitive Klassen
            if (PrimitiveWrapper.wrap(field.getType()) == Boolean.class) {
                return String.valueOf(field.getBoolean(src));
            }

        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "err in primToJson";
    }

    public String wrapToJson(Object obj) {

        return obj.toString();
    }

}
