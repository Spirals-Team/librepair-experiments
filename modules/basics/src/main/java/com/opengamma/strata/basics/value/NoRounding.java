/*
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.basics.value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.joda.beans.impl.direct.DirectPrivateBeanBuilder;

/**
 * Standard implementation of {@code Rounding} that makes no changes.
 * <p>
 * This class implements {@link Rounding} to provide an instance that does not perform rounding.
 * See {@link Rounding#none()}.
 */
@BeanDefinition(builderScope = "private")
final class NoRounding
    implements Rounding, ImmutableBean, Serializable {

  /**
   * The 'None' rounding convention, which applies no rounding.
   */
  static final NoRounding INSTANCE = new NoRounding();

  //-------------------------------------------------------------------------
  @Override
  public double round(double value) {
    return value;
  }

  @Override
  public BigDecimal round(BigDecimal value) {
    return value;
  }

  //-------------------------------------------------------------------------
  @Override
  public String toString() {
    return "No rounding";
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code NoRounding}.
   * @return the meta-bean, not null
   */
  public static NoRounding.Meta meta() {
    return NoRounding.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(NoRounding.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private NoRounding() {
  }

  @Override
  public NoRounding.Meta metaBean() {
    return NoRounding.Meta.INSTANCE;
  }

  @Override
  public <R> Property<R> property(String propertyName) {
    return metaBean().<R>metaProperty(propertyName).createProperty(this);
  }

  @Override
  public Set<String> propertyNames() {
    return metaBean().metaPropertyMap().keySet();
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      return true;
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    return hash;
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code NoRounding}.
   */
  static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null);

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    public BeanBuilder<? extends NoRounding> builder() {
      return new NoRounding.Builder();
    }

    @Override
    public Class<? extends NoRounding> beanType() {
      return NoRounding.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code NoRounding}.
   */
  private static final class Builder extends DirectPrivateBeanBuilder<NoRounding> {

    /**
     * Restricted constructor.
     */
    private Builder() {
      super(meta());
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      throw new NoSuchElementException("Unknown property: " + propertyName);
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      throw new NoSuchElementException("Unknown property: " + propertyName);
    }

    @Override
    public NoRounding build() {
      return new NoRounding();
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      return "NoRounding.Builder{}";
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
