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

    public <T> T convert(Object value, Class<T> classOfT) throws JSONSyntaxException {

        System.out.println("Start convert to: " + classOfT + " from: " + value);

        // Primitiv
        if (classOfT.isPrimitive()) {

            // Boolean
            if (classOfT == boolean.class) {

                return PrimitiveWrapper.wrap(classOfT).cast(value);
            } else if (classOfT == double.class) {

                return PrimitiveWrapper.wrap(classOfT).cast(convertDoubleToType(classOfT, value));
            }
        } else if (classOfT == String.class) {
            return classOfT.cast(value);
        } else if (value instanceof ArrayList) {

            return classOfT.cast(convertArrayListToIntArray(value));
        } else {
            try {

                Map<?, ?> newEntrys = (HashMap<?, ?>) value;

                Constructor<T> constructor = classOfT.getConstructor();
                Object result = constructor.newInstance();

                Set<Field> fields = new HashSet<Field>();
                fields.addAll(getAllFieldsFiltered(result));

                for (Field field : fields) {

                    if (newEntrys.containsKey(field.getName())) {

                        Object newEntry = newEntrys.get(field.getName());
                        Object newEntryConverted;

                        newEntryConverted = convertEntry(field.getType(), newEntry, field);
                        field.set(result, newEntryConverted);
                    }
                }

                System.out.println(
                        "End convert to: " + classOfT + " Result: " + classOfT.cast(result));
                return classOfT.cast(result);
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException
                    | NoSuchMethodException | InstantiationException
                    | InvocationTargetException e) {

                e.printStackTrace();
            }
        }
        return null;
    }

    private Object convertEntry(Class<?> desiredType, Object entry, Field field)
            throws JSONSyntaxException {

        if (desiredType.isArray()) {

            // Wenn der Array mehr als eine Dimension hat
            if ((1 + desiredType.getName().lastIndexOf('[')) > 1) {
                return convertArrayListToIntArrayArray(entry);
            } else {
                return convertArrayListToIntArray(entry);
            }
        } else if (List.class.isAssignableFrom(desiredType)) {

            return convertList(desiredType, entry);
        } else if (desiredType == boolean.class || desiredType == Boolean.class
                || desiredType == Object.class || desiredType == String.class) {

            return entry;
        } else if (Map.class.isAssignableFrom(desiredType)) {

            return convertMap(desiredType, entry, field);
        } else if (desiredType == char.class || desiredType == Character.class) {

            return convertStringToStringAndChars(desiredType, entry);
        } else if (Number.class.isAssignableFrom(desiredType) || desiredType.isPrimitive()) {

            return convertDoubleToType(desiredType, entry);
        } else if (Object.class.isAssignableFrom(desiredType)) {

            return convert(entry, (Class<?>) desiredType);
        } else {

            return null;
        }
    }

    private Object convertStringToStringAndChars(Class<?> desiredType, Object entry) {

        if (desiredType == char.class || desiredType == Character.class) {
            return entry.toString().charAt(0);
        } else {
            return entry;
        }

    }

    private Set<Field> getAllFieldsFiltered(Object src) {
        Class<?> cl = src.getClass();

        Set<Field> fieldsSet = new HashSet<Field>();
        Set<Field> result = new HashSet<Field>();
        fieldsSet.addAll(Arrays.asList(cl.getFields()));
        fieldsSet.addAll(Arrays.asList(cl.getDeclaredFields()));

        // Private Felder aus Superklassen lesen
        // TODO: Final check hier
        while (cl.getSuperclass() != null) {
            cl = cl.getSuperclass();
            fieldsSet.addAll(Arrays.asList(cl.getDeclaredFields()));
        }

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

    private Object convertList(Class<?> desiredType, Object entry) {

        ArrayList<?> entryArrayList = (ArrayList<?>) entry;

        ArrayList<Integer> result = new ArrayList<Integer>();

        for (int i = 0; i < entryArrayList.size(); i++) {

            Object convertedValue = null;

            if (entryArrayList.get(i).getClass() == Double.class) {
                convertedValue = convertDoubleToType(Integer.class, entryArrayList.get(i));
            }

            result.add(i, (Integer) convertedValue);
        }

        return result;
    }

    private Object convertArrayListToIntArrayArray(Object entry) {

        ArrayList<?> entryArrayList = (ArrayList<?>) entry;

        int[][] result = new int[entryArrayList.size()][];

        for (int i = 0; i < entryArrayList.size(); i++) {

            Object convertedValue = null;

            if (entryArrayList.get(i).getClass() == ArrayList.class) {
                convertedValue = convertArrayListToIntArray(entryArrayList.get(i));
            }

            result[i] = (int[]) convertedValue;
        }

        return result;
    }

    private Object convertArrayListToIntArray(Object entry) {

        ArrayList<?> entryArrayList = (ArrayList<?>) entry;

        int[] result = new int[entryArrayList.size()];

        for (int i = 0; i < entryArrayList.size(); i++) {

            Object convertedValue = null;

            if (entryArrayList.get(i).getClass() == Double.class) {
                convertedValue = convertDoubleToType(int.class, entryArrayList.get(i));
            }

            result[i] = (int) convertedValue;
        }

        return result;
    }

    private Number convertDoubleToType(Class<?> desiredType, Object doubleNumber) {

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
