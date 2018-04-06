package ch.ntb.jass.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class TestDataGenerator {
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = UPPER.toLowerCase(Locale.ROOT);
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "(){}[]\"'._-";
    private static final String ALPHANUM = UPPER + LOWER + DIGITS;
    private static final String ASCII;

    static {
        char[] ascii = new char[126];
        for (int i = 0; i < ascii.length; ++i)
            ascii[i] = (char) (i + 1);

        ASCII = new String(ascii);
    }

    private Set<String> entityPackages;
    private final int maxDepth;

    public TestDataGenerator(Set<String> entityPackages, int maxDepth) {
        this.entityPackages = entityPackages;
        this.maxDepth = maxDepth;
        if (maxDepth <= 0)
            throw new IllegalArgumentException();
    }

    public <T> T generatePOJO(Random random, Class<? extends T> type) throws Exception {
        return generatePOJO(random, type, null, 0);
    }

    private Object generateValue(Random random, Class<?> type, boolean nullable, int depth) throws Exception {
        return generateValue(random, type, null, nullable, depth);
    }

    private <T> T generatePOJO(Random random, Class<? extends T> type, Type genericType, int depth) throws Exception {
        if (depth >= maxDepth)
            return null;

        T object = type.newInstance();
        // generate random property values (public fields only)
        for (Field field : type.getFields()) {
            Object value = generateValue(random, field.getType(), field.getGenericType(), false, depth + 1);
            field.set(object, value);
//            System.out.println(field.getName() + "=" + value);
        }
        return object;
    }

    private Object generateValue(Random random, Class<?> type, Type genericType, boolean nullable, int depth) throws Exception {
        if (depth >= maxDepth)
            return null;

        //TODO config params
        int maxCollectionSize = 10;

        boolean yieldNull = nullable && random.nextBoolean();

        // primitive types (cannot be null):
        if (byte.class.isAssignableFrom(type))
            return (byte) random.nextInt(Byte.MAX_VALUE);
        else if (char.class.isAssignableFrom(type))
            return (char) random.nextInt(Character.MAX_VALUE);
        else if (short.class.isAssignableFrom(type))
            return (short) random.nextInt(Short.MAX_VALUE);
        else if (int.class.isAssignableFrom(type))
            return random.nextInt();
        else if (long.class.isAssignableFrom(type))
            return random.nextLong();
        else if (float.class.isAssignableFrom(type))
            return random.nextFloat();
        else if (double.class.isAssignableFrom(type))
            return random.nextDouble();
        else if (boolean.class.isAssignableFrom(type))
            return random.nextBoolean();

        if (yieldNull)
            return null;

        // primitive wrapper types (nullable):
        if (Byte.class.isAssignableFrom(type))
            return (byte) random.nextInt(Byte.MAX_VALUE);
        else if (Character.class.isAssignableFrom(type))
            return (char) random.nextInt(Character.MAX_VALUE);
        else if (Short.class.isAssignableFrom(type))
            return (short) random.nextInt(Short.MAX_VALUE);
        else if (Integer.class.isAssignableFrom(type))
            return random.nextInt();
        else if (Long.class.isAssignableFrom(type))
            return random.nextLong();
        else if (Float.class.isAssignableFrom(type))
            return random.nextFloat();
        else if (Double.class.isAssignableFrom(type))
            return random.nextDouble();
        else if (Boolean.class.isAssignableFrom(type))
            return random.nextBoolean();

        if (CharSequence.class.isAssignableFrom(type))
            return TestDataGenerator.nextString(random, 0, 100, TestDataGenerator.ASCII);
        else if (Enum.class.isAssignableFrom(type)) {
            Object[] values = type.getEnumConstants();
            return values[random.nextInt(values.length)];
        } else {
            if (type.isArray()) {
                Object array = Array.newInstance(type.getComponentType(), random.nextInt(maxCollectionSize));
                for (int i = 0; i < Array.getLength(array); ++i)
                    Array.set(array, i, generateValue(random, type.getComponentType(), nullable, depth + 1));
                return array;
            } else if (List.class.isAssignableFrom(type)) {
                List<Object> list = new ArrayList<>();
                int size = random.nextInt(maxCollectionSize);
                Type[] typeParameters = ((ParameterizedType) genericType).getActualTypeArguments();
                for (int i = 0; i < size; ++i) {
                    Object value = generateValue(random, (Class<?>) typeParameters[0], nullable, depth + 1);
                    list.add(value);
                }
                return list;
            } else if (Set.class.isAssignableFrom(type)) {
                Set<Object> set = new HashSet<>();
                int size = random.nextInt(maxCollectionSize);
                Type[] typeParameters = ((ParameterizedType) genericType).getActualTypeArguments();
                for (int i = 0; i < size; ++i) {
                    Object value = generateValue(random, (Class<?>) typeParameters[0], nullable, depth + 1);
                    set.add(value);
                }
                return set;
            } else if (Map.class.isAssignableFrom(type)) {
                Map<Object, Object> map = new HashMap<>();
                int size = random.nextInt(maxCollectionSize);
                Type[] typeParameters = ((ParameterizedType) genericType).getActualTypeArguments();
                for (int i = 0; i < size; ++i) {
                    Object key = generateValue(random, (Class<?>) typeParameters[0], nullable, depth + 1);
                    Object value = generateValue(random, (Class<?>) typeParameters[1], nullable, depth + 1);
                    map.put(key, value);
                }
                return map;
            } else if (Dictionary.class.isAssignableFrom(type)) {
                Dictionary<Object, Object> map = new Hashtable<>();
                int size = random.nextInt(maxCollectionSize);
                Type[] typeParameters = ((ParameterizedType) genericType).getActualTypeArguments();
                for (int i = 0; i < size; ++i) {
                    Object key = generateValue(random, (Class<?>) typeParameters[0], nullable, depth + 1);
                    Object value = generateValue(random, (Class<?>) typeParameters[1], nullable, depth + 1);
                    map.put(key, value);
                }
                return map;
            } else if (isInAnyPackage(type, entityPackages, true)) {
                return generatePOJO(random, type);
            }
        }

        throw new IllegalArgumentException("Unsupported type: " + type);
    }

    private static boolean isInAnyPackage(Class<?> type, String rootPackage, boolean includeNestedPackages) {
        // type.getPackage() may return null at this point
        String typeName = type.getCanonicalName();
        String typePackage = typeName.substring(0, typeName.lastIndexOf('.'));

        boolean inPackage = typePackage.equals(rootPackage) || (includeNestedPackages && typePackage.startsWith(rootPackage));
        return inPackage;
    }

    private static boolean isInAnyPackage(Class<?> type, Set<String> rootPackages, boolean includeNestedPackages) {
        for (String rootPacakge : rootPackages)
            if (isInAnyPackage(type, rootPacakge, includeNestedPackages))
                return true;
        return false;
    }

    public static String nextString(Random random, int minLength, int maxLength, char[] symbols) {
        if (maxLength < minLength)
            throw new IllegalArgumentException();
        int lengthRange = maxLength - minLength;

        int length = minLength + lengthRange > 0 ? random.nextInt(lengthRange) : 0;
        final char[] buf = new char[length];
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    }

    public static String nextString(Random random, int minLength, int maxLength, String symbols) {
        if (maxLength < minLength)
            throw new IllegalArgumentException();
        int lengthRange = maxLength - minLength;

        int length = minLength + lengthRange > 0 ? random.nextInt(lengthRange) : 0;
        final char[] buf = new char[length];
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = symbols.charAt(random.nextInt(symbols.length()));
        return new String(buf);
    }
}
