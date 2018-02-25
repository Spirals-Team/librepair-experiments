package io.ebeaninternal.server.properties;

import io.ebean.bean.EntityBean;

/**
 * Returns Getter and Setter methods based on EntityBean enhancement and field position.
 */
public final class EnhanceBeanPropertyAccess implements BeanPropertyAccess {

  private static final int CACHE_SIZE = 30;

  private final BeanPropertyGetter[] getters = initGetters(CACHE_SIZE);
  private final BeanPropertySetter[] setters = initSetters(CACHE_SIZE);

  public EnhanceBeanPropertyAccess() {
  }

  private BeanPropertyGetter[] initGetters(int count) {
    BeanPropertyGetter[] getters = new BeanPropertyGetter[count];
    for (int i = 0; i < count; i++) {
      getters[i] = new Getter(i);
    }
    return getters;
  }

  private BeanPropertySetter[] initSetters(int count) {
    BeanPropertySetter[] setters = new BeanPropertySetter[count];
    for (int i = 0; i < count; i++) {
      setters[i] = new Setter(i);
    }
    return setters;
  }

  @Override
  public BeanPropertyGetter getGetter(int position) {
    if (position < CACHE_SIZE) {
      return getters[position];
    }
    return new Getter(position);
  }

  @Override
  public BeanPropertySetter getSetter(int position) {
    if (position < CACHE_SIZE) {
      return setters[position];
    }
    return new Setter(position);
  }

  private static final class Getter implements BeanPropertyGetter {

    private final int fieldIndex;

    Getter(int fieldIndex) {
      this.fieldIndex = fieldIndex;
    }

    @Override
    public Object get(EntityBean bean) {
      return bean._ebean_getField(fieldIndex);
    }

    @Override
    public Object getIntercept(EntityBean bean) {
      return bean._ebean_getFieldIntercept(fieldIndex);
    }
  }

  private static final class Setter implements BeanPropertySetter {

    private final int fieldIndex;

    Setter(int fieldIndex) {
      this.fieldIndex = fieldIndex;
    }

    @Override
    public void set(EntityBean bean, Object value) {
      bean._ebean_setField(fieldIndex, value);
    }

    @Override
    public void setIntercept(EntityBean bean, Object value) {
      bean._ebean_setFieldIntercept(fieldIndex, value);
    }

  }
}
