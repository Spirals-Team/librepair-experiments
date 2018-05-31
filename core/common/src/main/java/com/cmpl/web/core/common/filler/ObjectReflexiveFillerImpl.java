package com.cmpl.web.core.common.filler;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.cmpl.web.core.common.reflexion.CommonReflexion;
import com.cmpl.web.core.common.reflexion.METHOD;

/**
 * Implementation de l'interface permettant de remplir un objet destination avec un objet d'origine
 * 
 * @author Louis
 *
 */
public class ObjectReflexiveFillerImpl extends CommonReflexion implements ObjectReflexiveFiller {

  private Object origin;
  private Object destination;

  private static final Logger LOGGER = LoggerFactory.getLogger(ObjectReflexiveFillerImpl.class);

  private ObjectReflexiveFillerImpl(Object origin, Object destination) {
    this.origin = origin;
    this.destination = destination;
  }

  /**
   * Construteur definissant l'ogirine et la destination du transfert de valeurs
   * 
   * @param origin
   * @param destination
   * @return
   */
  public static ObjectReflexiveFillerImpl fromOriginAndDestination(Object origin, Object destination) {
    return new ObjectReflexiveFillerImpl(origin, destination);
  }

  @Override
  public void fillDestination() {
    invokeDestinationFieldsSetters();
  }

  private void invokeDestinationFieldsSetters() {
    List<Field> destinationFields = getFields(destination.getClass());
    destinationFields.forEach(destinationField -> {
      List<Field> originFields = getFields(origin.getClass());
      try {
        Method setterDestination = computeSetterDestination(destination, destinationField);
        invokeDestinationSetterIfPossible(origin, destination, originFields, destinationField, setterDestination);
      } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
        LOGGER.error("Impossible de remplir l'objet " + destination + " avec l'origine " + origin, e);
      }
    });
  }

  private Method computeSetterDestination(Object destination, Field destinationField) throws NoSuchMethodException {
    return destination.getClass().getMethod(getSetterName(destinationField), destinationField.getType());
  }

  private void invokeDestinationSetterIfPossible(Object origin, Object destination, List<Field> originFields,
      Field destinationField, Method setterDestination) throws NoSuchMethodException, IllegalAccessException,
      InvocationTargetException {

    Field originField = getCorrespondingOriginFieldForDestinationField(originFields, destinationField);
    if (canInvokeSetter(originField)) {
      Method getterOrigin = computeGetterOrigin(origin, originField);
      setterDestination.invoke(destination, getterOrigin.invoke(origin));
    }
  }

  private Field getCorrespondingOriginFieldForDestinationField(List<Field> originFields, Field destinationField) {
    List<Field> correspondingFields = originFields.stream()
        .filter(originField -> originField.getName().equals(destinationField.getName())).collect(Collectors.toList());
    if (CollectionUtils.isEmpty(correspondingFields)) {
      return null;
    }
    return correspondingFields.get(0);
  }

  private boolean canInvokeSetter(Field field) {
    return field != null;
  }

  private Method computeGetterOrigin(Object origin, Field originField) throws NoSuchMethodException {
    return origin.getClass().getMethod(getGetterName(originField));
  }

  private String getSetterName(Field field) {
    return getMethodName(METHOD.SETTER, field);
  }

  private String getGetterName(Field field) { 
    if (boolean.class.equals(field.getType())) {
      return getMethodName(METHOD.BOOLEAN_GETTER, field);
    } else {
      return getMethodName(METHOD.GETTER, field);
    }

  }

  private String getMethodName(METHOD method, Field field) {
    StringBuilder sb = new StringBuilder();
    sb.append(method.getPrefix());

    if (!field.isSynthetic()) {
      String fieldName = field.getName();
      sb.append(fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase()));
    }

    return sb.toString();
  }

}
