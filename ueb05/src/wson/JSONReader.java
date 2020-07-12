package wson;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Enthält Hilfsmethoden für {@link Wson#fromJson} zur Konvertierung.
 * 
 * @author kar, mhe, TODO Namen ergänzen
 *
 */
class JSONReader {

    /**
     * Konvertiert das Übergebene Element in ein Element von Typ T
     * 
     * @param <T> Der Typ des resultierenen Elementes
     * @param value Das zu konvertierende Element
     * @param classOfT Die Klasse des resultierenden Elementes
     * @return Das konvertierte Element
     * @throws JSONSyntaxException
     */
    public <T> T convert(Object value, Class<T> classOfT) throws JSONSyntaxException {

        // Überprüft die Klasse und entscheidet über die art der konvertierung
        if (classOfT.isPrimitive()) {
            if (classOfT == boolean.class) {

                return PrimitiveWrapper.wrap(classOfT).cast(value);
            } else if (classOfT == double.class) {

                return PrimitiveWrapper.wrap(classOfT).cast(convertNumber(classOfT, value));
            }
        } else if (classOfT == String.class) {
            return classOfT.cast(value);
        } else if (value instanceof ArrayList) {
            return classOfT.cast(convertArrayList(value, classOfT));
        } else {
            try {

                Map<?, ?> newEntrys = (HashMap<?, ?>) value;

                // Konstruiere ein das result Objekt
                Constructor<T> constructor = classOfT.getConstructor();
                Object result = constructor.newInstance();

                // Hole alle Felder des result Objektes
                Set<Field> fields = new HashSet<Field>();
                fields.addAll(getAllFieldsFiltered(result));

                // Wandle für jedes Feld den Eintrag in den gewünschten Typen
                for (Field field : fields) {

                    if (newEntrys.containsKey(field.getName())) {

                        Object newEntry = newEntrys.get(field.getName());
                        Object newEntryConverted;

                        newEntryConverted = convertEntry(field.getType(), newEntry, field);
                        field.set(result, newEntryConverted);
                    }
                }

                return classOfT.cast(result);
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException
                    | NoSuchMethodException | InstantiationException
                    | InvocationTargetException e) {

                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Gibt eine Menge von Felder für das Übergebene Objekt zurück
     * 
     * @param src Das Objekt, welche Felder ausgelesen werden sollen
     * @return Ein Set von Feldern
     */
    private Set<Field> getAllFieldsFiltered(Object src) {

        Class<?> cl = src.getClass();

        Set<Field> fieldsSet = new HashSet<Field>();
        Set<Field> result = new HashSet<Field>();

        fieldsSet.addAll(Arrays.asList(cl.getFields()));
        fieldsSet.addAll(Arrays.asList(cl.getDeclaredFields()));

        while (cl.getSuperclass() != null) {
            cl = cl.getSuperclass();
            fieldsSet.addAll(Arrays.asList(cl.getDeclaredFields()));
        }

        // Geht alle Felder durch und markiert sie entsprechend und fügt nur die gewünschten Hinzu
        for (Field field : fieldsSet) {
            if (!Modifier.isFinal(field.getModifiers())) {
                if (Modifier.isPrivate(field.getModifiers())
                        || Modifier.isProtected(field.getModifiers())
                        || field.getModifiers() == 0) {
                    field.setAccessible(true);
                }
                result.add(field);
            }
        }

        return result;
    }

    /**
     * Konvertiert einen Eintrag in das gewünschte Objekt
     * 
     * @param desiredType Ziel Typ
     * @param entry der zu konvertierende Eintrag
     * @param field das Feld in dem das Resultat geschrieben werden soll
     * @return Das Konvertierte Objekt
     * @throws JSONSyntaxException
     */
    private Object convertEntry(Class<?> desiredType, Object entry, Field field)
            throws JSONSyntaxException {

        if (desiredType.isArray()) {

            // Wenn der Array mehr als eine Dimension hat
            if ((1 + desiredType.getName().lastIndexOf('[')) > 1) {
                return convertArrayListMult(entry, desiredType);
            } else {
                return convertArrayList(entry, desiredType);
            }
        } else if (List.class.isAssignableFrom(desiredType)) {
            return convertList(desiredType, entry);
        } else if (desiredType == boolean.class || desiredType == Boolean.class
                || desiredType == Object.class || desiredType == String.class) {
            return entry;
        } else if (Map.class.isAssignableFrom(desiredType)) {
            return convertMap(desiredType, entry, field);
        } else if (desiredType == char.class || desiredType == Character.class) {
            return convertString(desiredType, entry);
        } else if (Number.class.isAssignableFrom(desiredType) || desiredType.isPrimitive()) {
            return convertNumber(desiredType, entry);
        } else if (Object.class.isAssignableFrom(desiredType)) {

            return convert(entry, (Class<?>) desiredType);
        } else {
            return null;
        }
    }

    /**
     * @param desiredType
     * @param entry
     * @return
     */
    private Object convertString(Class<?> desiredType, Object entry) {

        if (desiredType == char.class || desiredType == Character.class) {
            return entry.toString().charAt(0);
        } else {
            return entry;
        }

    }

    /**
     * Konvertiert das übergebene Map Objekt in das gewünschte Map Objekt
     * 
     * @param desiredType Der Typ des resultierenden Objektes
     * @param entry Der zu konvertierende Eintrag
     * @param field Das Feld in das das Objekt später geschrieben werden soll
     * @return Das konvertierte Objekt
     * @throws JSONSyntaxException
     */
    private Object convertMap(Class<?> desiredType, Object entry, Field field)
            throws JSONSyntaxException {

        Map<?, ?> newEntrys = (HashMap<?, ?>) entry;
        Map<String, Object> result = new HashMap<String, Object>();

        Type valueType = null;

        if (ParameterizedType.class.isAssignableFrom(field.getGenericType().getClass())) {
            ParameterizedType type = (ParameterizedType) field.getGenericType();
            valueType = type.getActualTypeArguments()[1];
        }

        if (valueType != null) {

            for (Entry<?, ?> kvPair : newEntrys.entrySet()) {
                result.put(kvPair.getKey().toString(),
                        convert(kvPair.getValue(), (Class<?>) valueType));
            }

            return result;
        } else {
            return entry;
        }

    }

    /**
     * Konvertiert das übergebene Listen Objekt in das gewünschte Listen Objekt
     * 
     * @param desiredType Der Typ des resultierenden Objektes
     * @param entry Der zu konvertierende Eintrag
     * @return Das konvertierte Objekt
     */
    private Object convertList(Class<?> desiredType, Object entry) {

        ArrayList<?> entryArrayList = (ArrayList<?>) entry;

        ArrayList<Integer> result = new ArrayList<Integer>();

        for (int i = 0; i < entryArrayList.size(); i++) {

            Object convertedValue = null;

            if (entryArrayList.get(i).getClass() == Double.class) {
                convertedValue = convertNumber(Integer.class, entryArrayList.get(i));
            }

            result.add(i, (Integer) convertedValue);
        }

        return result;
    }

    /**
     * Konvertiert die übergebene Liste in ein Array mit mehr als einer Dimension
     * 
     * @param entry Der zu konvertierende Eintrag
     * @return
     */
    private Object convertArrayListMult(Object entry, Class<?> desiredType) {

        ArrayList<?> entryArrayList = (ArrayList<?>) entry;

        if ((1 + desiredType.getName().lastIndexOf('[')) > 1) {

            Object[] result2 = new Object[entryArrayList.size()];

            for (int i = 0; i < entryArrayList.size(); i++) {

                Object ele =
                        convertArrayListMult(entryArrayList.get(i), desiredType.getComponentType());

                result2[i] = ele;
            }
            return desiredType.cast(result2);

        } else {

            return convertArrayList(entryArrayList, desiredType);
        }

    }

    /**
     * @param entry
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private Object convertArrayList(Object entry, Class<?> desiredType) {

        ArrayList<?> entryArrayList = (ArrayList<?>) entry;

        Class<?> arrayType = desiredType.getComponentType();

        Constructor<?> constructor;
        try {

            if (arrayType == float.class) {

                float[] result = new float[entryArrayList.size()];
                for (int i = 0; i < entryArrayList.size(); i++) {
                    result[i] = (float) convertNumber(float.class, entryArrayList.get(i));
                }

                return result;
            } else if (arrayType == short.class) {

                short[] result = new short[entryArrayList.size()];
                for (int i = 0; i < entryArrayList.size(); i++) {
                    result[i] = (short) convertNumber(short.class, entryArrayList.get(i));
                }

                return result;
            } else if (arrayType == byte.class) {

                byte[] result = new byte[entryArrayList.size()];
                for (int i = 0; i < entryArrayList.size(); i++) {
                    result[i] = (byte) convertNumber(byte.class, entryArrayList.get(i));
                }

                return result;
            } else if (arrayType == long.class) {

                long[] result = new long[entryArrayList.size()];
                for (int i = 0; i < entryArrayList.size(); i++) {
                    result[i] = (long) convertNumber(long.class, entryArrayList.get(i));
                }

                return result;
            } else if (arrayType == int.class) {

                int[] result = new int[entryArrayList.size()];
                for (int i = 0; i < entryArrayList.size(); i++) {
                    result[i] = (int) convertNumber(int.class, entryArrayList.get(i));
                }

                return result;
            } else if (!arrayType.isPrimitive()) {
                constructor = desiredType.getConstructor();
                Object result[] = (Object[]) constructor.newInstance();

                for (int i = 0; i < entryArrayList.size(); i++) {
                    result[i] = arrayType.cast(convertNumber(arrayType, entryArrayList.get(i)));
                }
                return result;
            } else {

                int[] result = new int[entryArrayList.size()];
                for (int i = 0; i < entryArrayList.size(); i++) {
                    result[i] = (int) convertNumber(int.class, entryArrayList.get(i));
                }

                return result;
            }

        } catch (SecurityException | IllegalArgumentException | IllegalAccessException
                | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Konvertiert die übergebene Zahl in den gewünschten Typen
     * 
     * @param desiredType Der Typ des resultierenden Objektes
     * @param doubleNumber Die übergebene Zahl
     * @return Die Konvertierte Zahl
     */
    private Number convertNumber(Class<?> desiredType, Object doubleNumber) {

        double number = (double) doubleNumber;

        if (desiredType == float.class || desiredType == Float.class) {
            return (float) number;
        } else if (desiredType == int.class || desiredType == Integer.class) {
            return (int) number;
        } else if (desiredType == short.class || desiredType == Short.class) {
            return (short) number;
        } else if (desiredType == byte.class || desiredType == Byte.class) {
            return (byte) number;
        } else if (desiredType == long.class || desiredType == Long.class) {
            return (long) number;
        }
        return number;
    }

}
