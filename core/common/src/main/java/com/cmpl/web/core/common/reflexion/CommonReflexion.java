package com.cmpl.web.core.common.reflexion;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommonReflexion {

  public List<Field> getFields(Class<?> classObject) {

    List<Field> fields = new ArrayList<>();

    List<Field> classFields = Arrays.asList(classObject.getDeclaredFields());
    fields.addAll(classFields);

    Class<?> superclass = classObject.getSuperclass();
    if (superclass != null) {
      fields.addAll(getFields(superclass));
    }

    return fields;

  }

}
