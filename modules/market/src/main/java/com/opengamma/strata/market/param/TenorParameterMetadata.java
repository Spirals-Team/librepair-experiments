/*
 * Copyright (C) 2016 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.market.param;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.ImmutablePreBuild;
import org.joda.beans.gen.PropertyDefinition;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.joda.beans.impl.direct.DirectPrivateBeanBuilder;

import com.opengamma.strata.basics.date.Tenor;
import com.opengamma.strata.collect.ArgChecker;

/**
 * Parameter metadata based on a tenor.
 */
@BeanDefinition(builderScope = "private")
public final class TenorParameterMetadata
    implements ParameterMetadata, ImmutableBean, Serializable {

  /**
   * The tenor associated with the parameter.
   */
  @PropertyDefinition(validate = "notNull")
  private final Tenor tenor;
  /**
   * The label that describes the parameter, defaulted to the tenor.
   */
  @PropertyDefinition(validate = "notEmpty", overrideGet = true)
  private final String label;

  //-------------------------------------------------------------------------
  /**
   * Obtains an instance using the tenor.
   * 
   * @param tenor  the tenor associated with the parameter
   * @return the parameter metadata based on the tenor
   */
  public static TenorParameterMetadata of(Tenor tenor) {
    ArgChecker.notNull(tenor, "tenor");
    return new TenorParameterMetadata(tenor, tenor.toString());
  }

  /**
   * Obtains an instance using the tenor, specifying the label.
   * 
   * @param tenor  the tenor associated with the parameter
   * @param label  the label to use
   * @return the parameter metadata based on the tenor
   */
  public static TenorParameterMetadata of(Tenor tenor, String label) {
    return new TenorParameterMetadata(tenor, label);
  }

  @ImmutablePreBuild
  private static void preBuild(Builder builder) {
    if (builder.label == null && builder.tenor != null) {
      builder.label = builder.tenor.toString();
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the identifier, which is the tenor.
   *
   * @return the tenor
   */
  @Override
  public Tenor getIdentifier() {
    return tenor;
  }

  //------------------------- AUTOGENERATED START -------------------------
  /**
   * The meta-bean for {@code TenorParameterMetadata}.
   * @return the meta-bean, not null
   */
  public static TenorParameterMetadata.Meta meta() {
    return TenorParameterMetadata.Meta.INSTANCE;
  }

  static {
    MetaBean.register(TenorParameterMetadata.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private TenorParameterMetadata(
      Tenor tenor,
      String label) {
    JodaBeanUtils.notNull(tenor, "tenor");
    JodaBeanUtils.notEmpty(label, "label");
    this.tenor = tenor;
    this.label = label;
  }

  @Override
  public TenorParameterMetadata.Meta metaBean() {
    return TenorParameterMetadata.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the tenor associated with the parameter.
   * @return the value of the property, not null
   */
  public Tenor getTenor() {
    return tenor;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the label that describes the parameter, defaulted to the tenor.
   * @return the value of the property, not empty
   */
  @Override
  public String getLabel() {
    return label;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      TenorParameterMetadata other = (TenorParameterMetadata) obj;
      return JodaBeanUtils.equal(tenor, other.tenor) &&
          JodaBeanUtils.equal(label, other.label);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(tenor);
    hash = hash * 31 + JodaBeanUtils.hashCode(label);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("TenorParameterMetadata{");
    buf.append("tenor").append('=').append(tenor).append(',').append(' ');
    buf.append("label").append('=').append(JodaBeanUtils.toString(label));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code TenorParameterMetadata}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code tenor} property.
     */
    private final MetaProperty<Tenor> tenor = DirectMetaProperty.ofImmutable(
        this, "tenor", TenorParameterMetadata.class, Tenor.class);
    /**
     * The meta-property for the {@code label} property.
     */
    private final MetaProperty<String> label = DirectMetaProperty.ofImmutable(
        this, "label", TenorParameterMetadata.class, String.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "tenor",
        "label");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 110246592:  // tenor
          return tenor;
        case 102727412:  // label
          return label;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends TenorParameterMetadata> builder() {
      return new TenorParameterMetadata.Builder();
    }

    @Override
    public Class<? extends TenorParameterMetadata> beanType() {
      return TenorParameterMetadata.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code tenor} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Tenor> tenor() {
      return tenor;
    }

    /**
     * The meta-property for the {@code label} property.
     * @return the meta-property, not null
     */
    public MetaProperty<String> label() {
      return label;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 110246592:  // tenor
          return ((TenorParameterMetadata) bean).getTenor();
        case 102727412:  // label
          return ((TenorParameterMetadata) bean).getLabel();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      metaProperty(propertyName);
      if (quiet) {
        return;
      }
      throw new UnsupportedOperationException("Property cannot be written: " + propertyName);
    }

  }

  //-----------------------------------------------------------------------
  /**
   * The bean-builder for {@code TenorParameterMetadata}.
   */
  private static final class Builder extends DirectPrivateBeanBuilder<TenorParameterMetadata> {

    private Tenor tenor;
    private String label;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 110246592:  // tenor
          return tenor;
        case 102727412:  // label
          return label;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 110246592:  // tenor
          this.tenor = (Tenor) newValue;
          break;
        case 102727412:  // label
          this.label = (String) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public TenorParameterMetadata build() {
      preBuild(this);
      return new TenorParameterMetadata(
          tenor,
          label);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("TenorParameterMetadata.Builder{");
      buf.append("tenor").append('=').append(JodaBeanUtils.toString(tenor)).append(',').append(' ');
      buf.append("label").append('=').append(JodaBeanUtils.toString(label));
      buf.append('}');
      return buf.toString();
    }

  }

  //-------------------------- AUTOGENERATED END --------------------------
}
