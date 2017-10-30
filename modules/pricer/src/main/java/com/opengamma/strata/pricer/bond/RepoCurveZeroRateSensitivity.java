/*
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.pricer.bond;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.DoubleUnaryOperator;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.PropertyDefinition;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.joda.beans.impl.direct.DirectPrivateBeanBuilder;

import com.google.common.collect.ComparisonChain;
import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.basics.currency.FxRateProvider;
import com.opengamma.strata.market.curve.RepoGroup;
import com.opengamma.strata.market.sensitivity.MutablePointSensitivities;
import com.opengamma.strata.market.sensitivity.PointSensitivity;
import com.opengamma.strata.market.sensitivity.PointSensitivityBuilder;
import com.opengamma.strata.pricer.ZeroRateSensitivity;

/**
 * Point sensitivity to the repo curve.
 * <p>
 * Holds the sensitivity to the repo curve at a specific date.
 */
@BeanDefinition(builderScope = "private")
public final class RepoCurveZeroRateSensitivity
    implements PointSensitivity, PointSensitivityBuilder, ImmutableBean, Serializable {

  /**
   * The currency of the curve for which the sensitivity is computed.
   */
  @PropertyDefinition(validate = "notNull")
  private final Currency curveCurrency;
  /**
   * The time that was queried, expressed as a year fraction.
   */
  @PropertyDefinition
  private final double yearFraction;
  /**
   * The currency of the sensitivity.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final Currency currency;
  /**
   * The repo group.
   * <p>
   * This defines the group that the discount factors are for.
   */
  @PropertyDefinition(validate = "notNull")
  private final RepoGroup repoGroup;
  /**
   * The value of the sensitivity.
   */
  @PropertyDefinition(overrideGet = true)
  private final double sensitivity;

  //-------------------------------------------------------------------------
  /**
   * Obtains an instance from the curve currency, date, group and value.
   * <p>
   * The currency representing the curve is used also for the sensitivity currency.
   * 
   * @param currency  the currency of the curve and sensitivity
   * @param yearFraction  the year fraction that was looked up on the curve
   * @param repoGroup  the group
   * @param sensitivity  the value of the sensitivity
   * @return the point sensitivity object
   */
  public static RepoCurveZeroRateSensitivity of(
      Currency currency,
      double yearFraction,
      RepoGroup repoGroup,
      double sensitivity) {

    return of(currency, yearFraction, currency, repoGroup, sensitivity);
  }

  /**
   * Obtains an instance from zero rate sensitivity and group.
   * 
   * @param zeroRateSensitivity  the zero rate sensitivity
   * @param repoGroup  the group
   * @return the point sensitivity object
   */
  public static RepoCurveZeroRateSensitivity of(ZeroRateSensitivity zeroRateSensitivity, RepoGroup repoGroup) {
    return of(
        zeroRateSensitivity.getCurveCurrency(),
        zeroRateSensitivity.getYearFraction(),
        zeroRateSensitivity.getCurrency(),
        repoGroup,
        zeroRateSensitivity.getSensitivity());
  }

  /**
   * Obtains an instance from the curve currency, date, sensitivity currency,
   * group and value.
   * 
   * @param curveCurrency  the currency of the curve
   * @param yearFraction  the year fraction that was looked up on the curve
   * @param sensitivityCurrency  the currency of the sensitivity
   * @param repoGroup  the group
   * @param sensitivity  the value of the sensitivity
   * @return the point sensitivity object
   */
  public static RepoCurveZeroRateSensitivity of(
      Currency curveCurrency,
      double yearFraction,
      Currency sensitivityCurrency,
      RepoGroup repoGroup,
      double sensitivity) {

    return new RepoCurveZeroRateSensitivity(curveCurrency, yearFraction, sensitivityCurrency, repoGroup, sensitivity);
  }

  //-------------------------------------------------------------------------
  @Override
  public RepoCurveZeroRateSensitivity withCurrency(Currency currency) {
    if (this.currency.equals(currency)) {
      return this;
    }
    return new RepoCurveZeroRateSensitivity(curveCurrency, yearFraction, currency, repoGroup, sensitivity);
  }

  @Override
  public RepoCurveZeroRateSensitivity withSensitivity(double sensitivity) {
    return new RepoCurveZeroRateSensitivity(curveCurrency, yearFraction, currency, repoGroup, sensitivity);
  }

  @Override
  public int compareKey(PointSensitivity other) {
    if (other instanceof RepoCurveZeroRateSensitivity) {
      RepoCurveZeroRateSensitivity otherZero = (RepoCurveZeroRateSensitivity) other;
      return ComparisonChain.start()
          .compare(curveCurrency, otherZero.curveCurrency)
          .compare(currency, otherZero.currency)
          .compare(yearFraction, otherZero.yearFraction)
          .compare(repoGroup, otherZero.repoGroup)
          .result();
    }
    return getClass().getSimpleName().compareTo(other.getClass().getSimpleName());
  }

  @Override
  public RepoCurveZeroRateSensitivity convertedTo(Currency resultCurrency, FxRateProvider rateProvider) {
    return (RepoCurveZeroRateSensitivity) PointSensitivity.super.convertedTo(resultCurrency, rateProvider);
  }

  //-------------------------------------------------------------------------
  @Override
  public RepoCurveZeroRateSensitivity multipliedBy(double factor) {
    return new RepoCurveZeroRateSensitivity(curveCurrency, yearFraction, currency, repoGroup, sensitivity * factor);
  }

  @Override
  public RepoCurveZeroRateSensitivity mapSensitivity(DoubleUnaryOperator operator) {
    return new RepoCurveZeroRateSensitivity(
        curveCurrency, yearFraction, currency, repoGroup, operator.applyAsDouble(sensitivity));
  }

  @Override
  public RepoCurveZeroRateSensitivity normalize() {
    return this;
  }

  @Override
  public MutablePointSensitivities buildInto(MutablePointSensitivities combination) {
    return combination.add(this);
  }

  @Override
  public RepoCurveZeroRateSensitivity cloned() {
    return this;
  }

  /**
   * Obtains the underlying {@code ZeroRateSensitivity}. 
   * <p>
   * This creates the zero rate sensitivity object by omitting the repo group.
   * 
   * @return the point sensitivity object
   */
  public ZeroRateSensitivity createZeroRateSensitivity() {
    return ZeroRateSensitivity.of(curveCurrency, yearFraction, currency, sensitivity);
  }

  //------------------------- AUTOGENERATED START -------------------------
  /**
   * The meta-bean for {@code RepoCurveZeroRateSensitivity}.
   * @return the meta-bean, not null
   */
  public static RepoCurveZeroRateSensitivity.Meta meta() {
    return RepoCurveZeroRateSensitivity.Meta.INSTANCE;
  }

  static {
    MetaBean.register(RepoCurveZeroRateSensitivity.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private RepoCurveZeroRateSensitivity(
      Currency curveCurrency,
      double yearFraction,
      Currency currency,
      RepoGroup repoGroup,
      double sensitivity) {
    JodaBeanUtils.notNull(curveCurrency, "curveCurrency");
    JodaBeanUtils.notNull(currency, "currency");
    JodaBeanUtils.notNull(repoGroup, "repoGroup");
    this.curveCurrency = curveCurrency;
    this.yearFraction = yearFraction;
    this.currency = currency;
    this.repoGroup = repoGroup;
    this.sensitivity = sensitivity;
  }

  @Override
  public RepoCurveZeroRateSensitivity.Meta metaBean() {
    return RepoCurveZeroRateSensitivity.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the currency of the curve for which the sensitivity is computed.
   * @return the value of the property, not null
   */
  public Currency getCurveCurrency() {
    return curveCurrency;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the time that was queried, expressed as a year fraction.
   * @return the value of the property
   */
  public double getYearFraction() {
    return yearFraction;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the currency of the sensitivity.
   * @return the value of the property, not null
   */
  @Override
  public Currency getCurrency() {
    return currency;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the repo group.
   * <p>
   * This defines the group that the discount factors are for.
   * @return the value of the property, not null
   */
  public RepoGroup getRepoGroup() {
    return repoGroup;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the value of the sensitivity.
   * @return the value of the property
   */
  @Override
  public double getSensitivity() {
    return sensitivity;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      RepoCurveZeroRateSensitivity other = (RepoCurveZeroRateSensitivity) obj;
      return JodaBeanUtils.equal(curveCurrency, other.curveCurrency) &&
          JodaBeanUtils.equal(yearFraction, other.yearFraction) &&
          JodaBeanUtils.equal(currency, other.currency) &&
          JodaBeanUtils.equal(repoGroup, other.repoGroup) &&
          JodaBeanUtils.equal(sensitivity, other.sensitivity);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(curveCurrency);
    hash = hash * 31 + JodaBeanUtils.hashCode(yearFraction);
    hash = hash * 31 + JodaBeanUtils.hashCode(currency);
    hash = hash * 31 + JodaBeanUtils.hashCode(repoGroup);
    hash = hash * 31 + JodaBeanUtils.hashCode(sensitivity);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(192);
    buf.append("RepoCurveZeroRateSensitivity{");
    buf.append("curveCurrency").append('=').append(curveCurrency).append(',').append(' ');
    buf.append("yearFraction").append('=').append(yearFraction).append(',').append(' ');
    buf.append("currency").append('=').append(currency).append(',').append(' ');
    buf.append("repoGroup").append('=').append(repoGroup).append(',').append(' ');
    buf.append("sensitivity").append('=').append(JodaBeanUtils.toString(sensitivity));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code RepoCurveZeroRateSensitivity}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code curveCurrency} property.
     */
    private final MetaProperty<Currency> curveCurrency = DirectMetaProperty.ofImmutable(
        this, "curveCurrency", RepoCurveZeroRateSensitivity.class, Currency.class);
    /**
     * The meta-property for the {@code yearFraction} property.
     */
    private final MetaProperty<Double> yearFraction = DirectMetaProperty.ofImmutable(
        this, "yearFraction", RepoCurveZeroRateSensitivity.class, Double.TYPE);
    /**
     * The meta-property for the {@code currency} property.
     */
    private final MetaProperty<Currency> currency = DirectMetaProperty.ofImmutable(
        this, "currency", RepoCurveZeroRateSensitivity.class, Currency.class);
    /**
     * The meta-property for the {@code repoGroup} property.
     */
    private final MetaProperty<RepoGroup> repoGroup = DirectMetaProperty.ofImmutable(
        this, "repoGroup", RepoCurveZeroRateSensitivity.class, RepoGroup.class);
    /**
     * The meta-property for the {@code sensitivity} property.
     */
    private final MetaProperty<Double> sensitivity = DirectMetaProperty.ofImmutable(
        this, "sensitivity", RepoCurveZeroRateSensitivity.class, Double.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "curveCurrency",
        "yearFraction",
        "currency",
        "repoGroup",
        "sensitivity");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1303639584:  // curveCurrency
          return curveCurrency;
        case -1731780257:  // yearFraction
          return yearFraction;
        case 575402001:  // currency
          return currency;
        case -393084371:  // repoGroup
          return repoGroup;
        case 564403871:  // sensitivity
          return sensitivity;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends RepoCurveZeroRateSensitivity> builder() {
      return new RepoCurveZeroRateSensitivity.Builder();
    }

    @Override
    public Class<? extends RepoCurveZeroRateSensitivity> beanType() {
      return RepoCurveZeroRateSensitivity.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code curveCurrency} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Currency> curveCurrency() {
      return curveCurrency;
    }

    /**
     * The meta-property for the {@code yearFraction} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Double> yearFraction() {
      return yearFraction;
    }

    /**
     * The meta-property for the {@code currency} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Currency> currency() {
      return currency;
    }

    /**
     * The meta-property for the {@code repoGroup} property.
     * @return the meta-property, not null
     */
    public MetaProperty<RepoGroup> repoGroup() {
      return repoGroup;
    }

    /**
     * The meta-property for the {@code sensitivity} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Double> sensitivity() {
      return sensitivity;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 1303639584:  // curveCurrency
          return ((RepoCurveZeroRateSensitivity) bean).getCurveCurrency();
        case -1731780257:  // yearFraction
          return ((RepoCurveZeroRateSensitivity) bean).getYearFraction();
        case 575402001:  // currency
          return ((RepoCurveZeroRateSensitivity) bean).getCurrency();
        case -393084371:  // repoGroup
          return ((RepoCurveZeroRateSensitivity) bean).getRepoGroup();
        case 564403871:  // sensitivity
          return ((RepoCurveZeroRateSensitivity) bean).getSensitivity();
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
   * The bean-builder for {@code RepoCurveZeroRateSensitivity}.
   */
  private static final class Builder extends DirectPrivateBeanBuilder<RepoCurveZeroRateSensitivity> {

    private Currency curveCurrency;
    private double yearFraction;
    private Currency currency;
    private RepoGroup repoGroup;
    private double sensitivity;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1303639584:  // curveCurrency
          return curveCurrency;
        case -1731780257:  // yearFraction
          return yearFraction;
        case 575402001:  // currency
          return currency;
        case -393084371:  // repoGroup
          return repoGroup;
        case 564403871:  // sensitivity
          return sensitivity;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 1303639584:  // curveCurrency
          this.curveCurrency = (Currency) newValue;
          break;
        case -1731780257:  // yearFraction
          this.yearFraction = (Double) newValue;
          break;
        case 575402001:  // currency
          this.currency = (Currency) newValue;
          break;
        case -393084371:  // repoGroup
          this.repoGroup = (RepoGroup) newValue;
          break;
        case 564403871:  // sensitivity
          this.sensitivity = (Double) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public RepoCurveZeroRateSensitivity build() {
      return new RepoCurveZeroRateSensitivity(
          curveCurrency,
          yearFraction,
          currency,
          repoGroup,
          sensitivity);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(192);
      buf.append("RepoCurveZeroRateSensitivity.Builder{");
      buf.append("curveCurrency").append('=').append(JodaBeanUtils.toString(curveCurrency)).append(',').append(' ');
      buf.append("yearFraction").append('=').append(JodaBeanUtils.toString(yearFraction)).append(',').append(' ');
      buf.append("currency").append('=').append(JodaBeanUtils.toString(currency)).append(',').append(' ');
      buf.append("repoGroup").append('=').append(JodaBeanUtils.toString(repoGroup)).append(',').append(' ');
      buf.append("sensitivity").append('=').append(JodaBeanUtils.toString(sensitivity));
      buf.append('}');
      return buf.toString();
    }

  }

  //-------------------------- AUTOGENERATED END --------------------------
}
