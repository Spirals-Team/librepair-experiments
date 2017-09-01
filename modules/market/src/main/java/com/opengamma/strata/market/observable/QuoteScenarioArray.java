/*
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.market.observable;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Stream;

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

import com.opengamma.strata.collect.array.DoubleArray;
import com.opengamma.strata.data.scenario.ScenarioArray;

/**
 * Container for values for an item of quoted market data in multiple scenarios.
 * <p>
 * This class is a more efficient alternative to storing quotes using {@code MarketDataBox.ofScenarioValues}
 * or {@code ScenarioValuesList}.
 * <p>
 * It stores the quote values in a primitive double array which reduces memory
 * footprint and avoids the overhead of boxing.
 * <p>
 * For maximum performance functions can access the array of quotes without boxing or copying via
 * the {@code quotes} property. Functions should use a {@link QuoteScenarioArrayId} to request
 * a {@code QuotesArray} from the market data container if they need direct access to the array of quotes.
 */
@BeanDefinition(builderScope = "private")
public final class QuoteScenarioArray
    implements ScenarioArray<Double>, ImmutableBean, Serializable {

  /**
   * The values of the quotes.
   */
  @PropertyDefinition(validate = "notNull")
  private final DoubleArray quotes;

  //-------------------------------------------------------------------------
  /**
   * Obtains an instance wrapping a set of quotes.
   *
   * @param quotes  the quotes
   * @return an instance wrapping a set of quotes
   */
  public static QuoteScenarioArray of(DoubleArray quotes) {
    return new QuoteScenarioArray(quotes);
  }

  //-------------------------------------------------------------------------
  @Override
  public int getScenarioCount() {
    return quotes.size();
  }

  @Override
  public Double get(int scenarioIndex) {
    return quotes.get(scenarioIndex);
  }

  @Override
  public Stream<Double> stream() {
    return quotes.stream().boxed();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code QuoteScenarioArray}.
   * @return the meta-bean, not null
   */
  public static QuoteScenarioArray.Meta meta() {
    return QuoteScenarioArray.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(QuoteScenarioArray.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private QuoteScenarioArray(
      DoubleArray quotes) {
    JodaBeanUtils.notNull(quotes, "quotes");
    this.quotes = quotes;
  }

  @Override
  public QuoteScenarioArray.Meta metaBean() {
    return QuoteScenarioArray.Meta.INSTANCE;
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
   * Gets the values of the quotes.
   * @return the value of the property, not null
   */
  public DoubleArray getQuotes() {
    return quotes;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      QuoteScenarioArray other = (QuoteScenarioArray) obj;
      return JodaBeanUtils.equal(quotes, other.quotes);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(quotes);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("QuoteScenarioArray{");
    buf.append("quotes").append('=').append(JodaBeanUtils.toString(quotes));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code QuoteScenarioArray}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code quotes} property.
     */
    private final MetaProperty<DoubleArray> quotes = DirectMetaProperty.ofImmutable(
        this, "quotes", QuoteScenarioArray.class, DoubleArray.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "quotes");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -948399753:  // quotes
          return quotes;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends QuoteScenarioArray> builder() {
      return new QuoteScenarioArray.Builder();
    }

    @Override
    public Class<? extends QuoteScenarioArray> beanType() {
      return QuoteScenarioArray.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code quotes} property.
     * @return the meta-property, not null
     */
    public MetaProperty<DoubleArray> quotes() {
      return quotes;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -948399753:  // quotes
          return ((QuoteScenarioArray) bean).getQuotes();
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
   * The bean-builder for {@code QuoteScenarioArray}.
   */
  private static final class Builder extends DirectPrivateBeanBuilder<QuoteScenarioArray> {

    private DoubleArray quotes;

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
        case -948399753:  // quotes
          return quotes;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case -948399753:  // quotes
          this.quotes = (DoubleArray) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public QuoteScenarioArray build() {
      return new QuoteScenarioArray(
          quotes);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(64);
      buf.append("QuoteScenarioArray.Builder{");
      buf.append("quotes").append('=').append(JodaBeanUtils.toString(quotes));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
