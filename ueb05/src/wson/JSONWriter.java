package wson;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
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

    // TODO: Methoden zur Serialisierung hinzufügen

    // TODO
    // Writer sollte Strings in folgendem Format liefern:
    // "<name>":<value>
    // <value> kann O

    public String strToJson(Field field, Object src) {
        String result = "err in strToJson";

        try {
            String esc = escapeString(field.get(src).toString());
            result = "\"" + field.getName() + "\"" + ": " + "\"" + esc + "\"";

        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    public String arrToJson(Field field, Object src) {
        String result = "err in arrToJson";

        // for (int i = 0; i < Array.getLength(obj); i++) {}

        int length = Array.getLength(field);

        if (length > 0) {
            if (Array.get(field, 0).getClass().isArray()) {
                for (int i = 0; i < Array.getLength(field); i++) {
                    System.out.println("Rek: " + simpleArrToJson((Array.get(field, 0))));
                }

            } else {
                for (int i = 0; i < Array.getLength(field); i++) {
                    System.out.println(Array.get(field, i).toString());
                }
            }

        }

        return result;

    }

    public String objToJson(Field field, Object src) {
        // TODO genau das gleiche wie wrapperToJson??
        String result = "err in objToJson";

        try {
            result = "\"" + field.getName() + "\"" + ": ";

            // TODO Sonderfälle für Character und String?
            if (field.get(src).getClass() == String.class) {
                result = result + "\"" + String.valueOf(field.get(src)) + "\"";
            } else {
                result = result + String.valueOf(field.get(src));
            }
            // result = "\"" + field.getName() + "\"" + ": " + field.get(src).toString();
        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    public String primToJson(Field field, Object src) {
        String result = "err in primToJson";
        try {

            result = "\"" + field.getName() + "\"" + ": ";

            // TODO restliche Primitive Klassen
            if (PrimitiveWrapper.wrap(field.getType()) == Boolean.class) {
                result = result + String.valueOf(field.getBoolean(src));
            }
            if (PrimitiveWrapper.wrap(field.getType()) == Integer.class) {
                result = result + String.valueOf(field.getInt(src));
            }
            if (PrimitiveWrapper.wrap(field.getType()) == Long.class) {
                result = result + String.valueOf(field.getLong(src));
            }
            if (PrimitiveWrapper.wrap(field.getType()) == Float.class) {
                result = result + String.valueOf(field.getFloat(src));
            }
            if (PrimitiveWrapper.wrap(field.getType()) == Double.class) {
                result = result + String.valueOf(field.getDouble(src));
            }
            if (PrimitiveWrapper.wrap(field.getType()) == Byte.class) {
                result = result + String.valueOf(field.getByte(src));
            }
            if (PrimitiveWrapper.wrap(field.getType()) == Character.class) {
                result = result + "\"" + String.valueOf(field.getChar(src)) + "\"";
            }
            if (PrimitiveWrapper.wrap(field.getType()) == Short.class) {
                result = result + String.valueOf(field.getShort(src));
            }
            // TODO macht Void hier wirklich Sinn?
            if (PrimitiveWrapper.wrap(field.getType()) == Void.class) {
                result = result + String.valueOf(field.get(src));
            }

        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public String wrapperToJson(Field field, Object src) {
        String result = "err in wrapperToJson";
        try {
            // TODO restliche Wrapper Klassen
            // if (field.get(src) == Boolean.class)
            // Boolean bo = (Boolean) field.get(src);

            result = "\"" + field.getName() + "\"" + ": ";

            if (field.get(src).getClass() == Character.class) {
                result = result + "\"" + String.valueOf(field.get(src)) + "\"";
            } else {
                result = result + String.valueOf(field.get(src));
            }

        } catch (IllegalArgumentException | IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    public String simpleWrapperToJson(Object obj) {
        return obj.toString();
    }

    public String simpleStrToJson(String str) {

        return escapeString(str);

    }

    public String simpleArrToJson(Object obj) {
        System.out.println("array");

        StringJoiner sj = new StringJoiner(",");

        // TODO check für [][]... und rufe rekursiv auf
        for (int i = 0; i < Array.getLength(obj); i++) {
            System.out.println(Array.get(obj, i).toString());
            sj.add(Array.get(obj, i).toString());
        }

        return sj.toString();
    }

}
