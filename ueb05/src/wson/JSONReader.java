package wson;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import examples.EAccessibility;
import examples.EAnnotation;
import examples.EArray;
import examples.EBooleanNull;
import examples.ECharString;
import examples.ECycle;
import examples.EInheritance;
import examples.EList;
import examples.EMapObject;
import examples.ENumber;

/**
 * Enthält Hilfsmethoden für {@link Wson#fromJson} zur Konvertierung.
 * 
 * @author kar, mhe, TODO Namen ergänzen
 *
 */
class JSONReader {

    public <T> T convert(Object value, Class<T> classOfT) {

        // Primitiv
        if (classOfT.isPrimitive()) {

            // Boolean
            if (classOfT == boolean.class) {

                return PrimitiveWrapper.wrap(classOfT).cast(value);
            }
            // Zahl
            else if (classOfT == double.class) {

                return PrimitiveWrapper.wrap(classOfT).cast(convertDoubleToType(classOfT, value));
            }
        }
        // String
        else if (classOfT == String.class) {
            return classOfT.cast(value);
        }
        // Map
        else {

            try {

                if (value instanceof Map<?, ?>) {
                    Map<?, ?> jsonObject;
                    jsonObject = (HashMap<?, ?>) value;

                    Constructor<T> constructor = classOfT.getConstructor();
                    Object result = constructor.newInstance();

                    for (Map.Entry<?, ?> entry : jsonObject.entrySet()) {

                        Field field = classOfT.getField(entry.getKey().toString());

                        Object convertedValue = null;

                        if (entry.getValue().getClass() == Double.class) {
                            convertedValue = convertDoubleToType(field.getType(), entry.getValue());
                        } else if (entry.getValue().getClass() == String.class) {

                            if (field.getType() == char.class
                                    || field.getType() == Character.class) {
                                convertedValue = entry.getValue().toString().charAt(0);
                            } else {
                                convertedValue = entry.getValue();
                            }

                        } else if (entry.getValue().getClass() == ArrayList.class) {

                            ArrayList<?> jsonObject2;
                            jsonObject2 = (ArrayList<?>) entry.getValue();

                            int[] res = new int[jsonObject2.size()];

                            for (int i = 0; i < jsonObject2.size(); i++) {

                                Object convertedValue2 = null;

                                if (jsonObject2.get(i).getClass() == ArrayList.class) {
                                    convertedValue2 =
                                            convertDoubleToType(int.class, jsonObject2.get(i));
                                }

                                res[i] = (int) convertedValue;
                            }
                            return classOfT.cast(result);
                        }

                        if (field.getType().isPrimitive()) {

                            field.set(result,
                                    PrimitiveWrapper.wrap(field.getType()).cast(convertedValue));
                        } else {
                            field.set(result, convertedValue);
                        }
                    }

                    return classOfT.cast(result);
                }
                // ArrayList
                else if (value instanceof ArrayList) {
                    ArrayList<?> list;
                    list = (ArrayList<?>) value;

                    int[] result = new int[list.size()];

                    for (int i = 0; i < list.size(); i++) {

                        Object convertedValue = null;

                        if (list.get(i).getClass() == Double.class) {
                            convertedValue = convertDoubleToType(int.class, list.get(i));
                        }

                        result[i] = (int) convertedValue;
                    }
                    return classOfT.cast(result);
                }

            } catch (NoSuchFieldException | SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        return null;
    }

    public <T> T convert2(Object value, Class<T> classOfT) {

        // Primitiv
        if (classOfT.isPrimitive()) {

            // Boolean
            if (classOfT == boolean.class) {

                return PrimitiveWrapper.wrap(classOfT).cast(value);
            }
            // Zahl
            else if (classOfT == double.class) {

                return PrimitiveWrapper.wrap(classOfT).cast(convertDoubleToType(classOfT, value));
            }
        }
        // String
        else if (classOfT == String.class) {
            return classOfT.cast(value);
        }
        // Primitives Array
        else if (value instanceof ArrayList) {

            return classOfT.cast(convertArrayListToIntArray(value));
        }
        // Komplexe Typen
        else {
            try {

                Map<?, ?> newEntrys = (HashMap<?, ?>) value;

                Constructor<T> constructor = classOfT.getConstructor();
                Object result = constructor.newInstance();

                Field[] fields = result.getClass().getFields();

                for (Field field : fields) {

                    Object newEntry = newEntrys.get(field.getName());

                    Object newEntryConverted = convertEntry(field.getType(), newEntry);

                    field.set(result, newEntryConverted);
                }

                return classOfT.cast(result);

            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchMethodException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }

    Object convertEntry(Class desiredType, Object entry) {

        if (desiredType == int[].class) {
            return convertArrayListToIntArray(entry);
        } else if (desiredType == int[][].class) {
            return convertArrayListToIntArrayArray(entry);
        } else if (desiredType == List.class) {
            return convertList(desiredType, entry);
        } else if (desiredType == boolean.class || desiredType == Boolean.class
                || desiredType == Object.class || desiredType == String.class
                || desiredType == String.class || Map.class.isAssignableFrom(desiredType)) {
            return entry;
        } else if (desiredType == char.class || desiredType == Character.class) {
            return convertStringToStringAndChars(desiredType, entry);
        } else if (desiredType == EAccessibility.class || desiredType == EAnnotation.class
                || desiredType == EArray.class || desiredType == EBooleanNull.class
                || desiredType == ECharString.class || desiredType == ECycle.class
                || desiredType == EInheritance.class || desiredType == EList.class
                || desiredType == EMapObject.class || desiredType == ENumber.class) {
            return convert2(entry, desiredType);
        } else {
            return convertDoubleToType(desiredType, entry);
        }

    }

    Object convertStringToStringAndChars(Class desiredType, Object entry) {

        if (desiredType == char.class || desiredType == Character.class) {
            return entry.toString().charAt(0);
        } else {
            return entry;
        }

    }

    Object convertList(Class desiredType, Object entry) {

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

    Object convertArrayListToIntArrayArray(Object entry) {

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

    Object convertArrayListToIntArray(Object entry) {

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

    Number convertDoubleToType(Class desiredType, Object doubleNumber) {

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
