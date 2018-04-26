package com.mitchellbosecke.pebble.attributes;

import com.mitchellbosecke.pebble.error.PebbleException;

import java.util.Map;

public class MapResolver implements AttributeResolver {

  @Override
  public ResolvedAttribute resolve(Object instance,
                                   Object attribute,
                                   Object[] argumentValues,
                                   boolean isStrictVariables,
                                   String filename,
                                   int lineNumber) {
    if (argumentValues == null && instance instanceof Map) {
      return new MapResolvedAttribute((Map<?, ?>) instance, attribute, filename, lineNumber);
    }
    return null;
  }

  public static class MapResolvedAttribute implements ResolvedAttribute {
    private final Map<?, ?> object;
    private final Object attributeNameValue;
    private final String filename;
    private final int lineNumber;

    private MapResolvedAttribute(Map<?, ?> object, Object attributeNameValue, String filename, int lineNumber) {
      this.object = object;
      this.attributeNameValue = attributeNameValue;
      this.filename = filename;
      this.lineNumber = lineNumber;
    }

    @Override
    public Object evaluate() {
      if (object.isEmpty()) {
        return null;
      }
      if (attributeNameValue != null && Number.class.isAssignableFrom(attributeNameValue.getClass())) {
        Number keyAsNumber = (Number) attributeNameValue;

        Class<?> keyClass = object.keySet().iterator().next().getClass();
        Object key = this.cast(keyAsNumber, keyClass, filename, lineNumber);
        return object.get(key);
      }
      return object.get(attributeNameValue);
    }

    private Object cast(Number number,
                        Class<?> desiredType,
                        String filename,
                        int lineNumber) {
      if (desiredType == Long.class) {
        return number.longValue();
      } else if (desiredType == Integer.class) {
        return number.intValue();
      } else if (desiredType == Double.class) {
        return number.doubleValue();
      } else if (desiredType == Float.class) {
        return number.floatValue();
      } else if (desiredType == Short.class) {
        return number.shortValue();
      } else if (desiredType == Byte.class) {
        return number.byteValue();
      }
      throw new PebbleException(null, String.format("type %s not supported for key %s", desiredType, number), lineNumber, filename);
    }
  }
}
