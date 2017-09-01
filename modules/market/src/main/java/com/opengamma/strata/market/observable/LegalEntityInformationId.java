/*
 * Copyright (C) 2016 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.market.observable;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.joda.beans.impl.direct.DirectPrivateBeanBuilder;

import com.opengamma.strata.basics.StandardId;
import com.opengamma.strata.data.MarketDataId;

/**
 * Identifies the market data for legal entity information.
 */
@BeanDefinition(builderScope = "private", cacheHashCode = true)
public final class LegalEntityInformationId
    implements MarketDataId<LegalEntityInformation>, ImmutableBean, Serializable {

  /**
  * The legal entity identifier.
  */
  @PropertyDefinition(validate = "notNull")
  private final StandardId legalEntityId;

  //-------------------------------------------------------------------------
  /**
  * Obtains an identifier used to find legal entity information.
  *
  * @param legalEntityId  the name
  * @return an identifier
  */
  public static LegalEntityInformationId of(String legalEntityId) {
    return new LegalEntityInformationId(StandardId.parse(legalEntityId));
  }

  /**
  * Obtains an identifier used to find legal entity information.
  *
  * @param legalEntityId  the name
  * @return the identifier
  */
  public static LegalEntityInformationId of(StandardId legalEntityId) {
    return new LegalEntityInformationId(legalEntityId);
  }

  //-------------------------------------------------------------------------
  @Override
  public Class<LegalEntityInformation> getMarketDataType() {
    return LegalEntityInformation.class;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code LegalEntityInformationId}.
   * @return the meta-bean, not null
   */
  public static LegalEntityInformationId.Meta meta() {
    return LegalEntityInformationId.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(LegalEntityInformationId.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The cached hash code, using the racy single-check idiom.
   */
  private int cachedHashCode;

  private LegalEntityInformationId(
      StandardId legalEntityId) {
    JodaBeanUtils.notNull(legalEntityId, "legalEntityId");
    this.legalEntityId = legalEntityId;
  }

  @Override
  public LegalEntityInformationId.Meta metaBean() {
    return LegalEntityInformationId.Meta.INSTANCE;
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
  /**
   * Gets the legal entity identifier.
   * @return the value of the property, not null
   */
  public StandardId getLegalEntityId() {
    return legalEntityId;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      LegalEntityInformationId other = (LegalEntityInformationId) obj;
      return JodaBeanUtils.equal(legalEntityId, other.legalEntityId);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = cachedHashCode;
    if (hash == 0) {
      hash = getClass().hashCode();
      hash = hash * 31 + JodaBeanUtils.hashCode(legalEntityId);
      cachedHashCode = hash;
    }
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("LegalEntityInformationId{");
    buf.append("legalEntityId").append('=').append(JodaBeanUtils.toString(legalEntityId));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code LegalEntityInformationId}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code legalEntityId} property.
     */
    private final MetaProperty<StandardId> legalEntityId = DirectMetaProperty.ofImmutable(
        this, "legalEntityId", LegalEntityInformationId.class, StandardId.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "legalEntityId");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 866287159:  // legalEntityId
          return legalEntityId;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends LegalEntityInformationId> builder() {
      return new LegalEntityInformationId.Builder();
    }

    @Override
    public Class<? extends LegalEntityInformationId> beanType() {
      return LegalEntityInformationId.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code legalEntityId} property.
     * @return the meta-property, not null
     */
    public MetaProperty<StandardId> legalEntityId() {
      return legalEntityId;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 866287159:  // legalEntityId
          return ((LegalEntityInformationId) bean).getLegalEntityId();
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
   * The bean-builder for {@code LegalEntityInformationId}.
   */
  private static final class Builder extends DirectPrivateBeanBuilder<LegalEntityInformationId> {

    private StandardId legalEntityId;

    /**
     * Restricted constructor.
     */
    private Builder() {
      super(meta());
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 866287159:  // legalEntityId
          return legalEntityId;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 866287159:  // legalEntityId
          this.legalEntityId = (StandardId) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public LegalEntityInformationId build() {
      return new LegalEntityInformationId(
          legalEntityId);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(64);
      buf.append("LegalEntityInformationId.Builder{");
      buf.append("legalEntityId").append('=').append(JodaBeanUtils.toString(legalEntityId));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------

}
