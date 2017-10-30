/*
 * Copyright (C) 2016 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.measure.rate;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.atomic.AtomicReferenceArray;

import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.TypedMetaBean;
import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.ImmutableConstructor;
import org.joda.beans.gen.PropertyDefinition;
import org.joda.beans.impl.light.LightMetaBean;

import com.opengamma.strata.collect.ArgChecker;
import com.opengamma.strata.data.scenario.ScenarioMarketData;

/**
 * The default market data for rates products, used for calculation across multiple scenarios.
 * <p>
 * This uses a {@link RatesMarketDataLookup} to provide a view on {@link ScenarioMarketData}.
 */
@BeanDefinition(style = "light")
final class DefaultRatesScenarioMarketData
    implements RatesScenarioMarketData, ImmutableBean, Serializable {

  /**
   * The lookup.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final RatesMarketDataLookup lookup;
  /**
   * The market data.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final ScenarioMarketData marketData;
  /**
   * The cache of single scenario instances.
   */
  private final transient AtomicReferenceArray<RatesMarketData> cache;  // derived

  //-------------------------------------------------------------------------
  /**
   * Obtains an instance based on a lookup and market data.
   * <p>
   * The lookup provides the mapping from currency to discount curve, and from
   * index to forward curve. The curves are in the market data.
   *
   * @param lookup  the lookup
   * @param marketData  the market data
   * @return the rates market view
   */
  public static DefaultRatesScenarioMarketData of(RatesMarketDataLookup lookup, ScenarioMarketData marketData) {
    return new DefaultRatesScenarioMarketData(lookup, marketData);
  }

  @ImmutableConstructor
  private DefaultRatesScenarioMarketData(RatesMarketDataLookup lookup, ScenarioMarketData marketData) {
    this.lookup = ArgChecker.notNull(lookup, "lookup");
    this.marketData = ArgChecker.notNull(marketData, "marketData");
    this.cache = new AtomicReferenceArray<>(marketData.getScenarioCount());
  }

  // ensure standard constructor is invoked
  private Object readResolve() {
    return new DefaultRatesScenarioMarketData(lookup, marketData);
  }

  //-------------------------------------------------------------------------
  @Override
  public RatesScenarioMarketData withMarketData(ScenarioMarketData marketData) {
    return DefaultRatesScenarioMarketData.of(lookup, marketData);
  }

  //-------------------------------------------------------------------------
  @Override
  public int getScenarioCount() {
    return marketData.getScenarioCount();
  }

  @Override
  public RatesMarketData scenario(int scenarioIndex) {
    RatesMarketData current = cache.get(scenarioIndex);
    if (current != null) {
      return current;
    }
    return cache.updateAndGet(
        scenarioIndex,
        v -> v != null ? v : lookup.marketDataView(marketData.scenario(scenarioIndex)));
  }

  //------------------------- AUTOGENERATED START -------------------------
  /**
   * The meta-bean for {@code DefaultRatesScenarioMarketData}.
   */
  private static final TypedMetaBean<DefaultRatesScenarioMarketData> META_BEAN =
      LightMetaBean.of(
          DefaultRatesScenarioMarketData.class,
          MethodHandles.lookup(),
          new String[] {
              "lookup",
              "marketData"},
          new Object[0]);

  /**
   * The meta-bean for {@code DefaultRatesScenarioMarketData}.
   * @return the meta-bean, not null
   */
  public static TypedMetaBean<DefaultRatesScenarioMarketData> meta() {
    return META_BEAN;
  }

  static {
    MetaBean.register(META_BEAN);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  @Override
  public TypedMetaBean<DefaultRatesScenarioMarketData> metaBean() {
    return META_BEAN;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the lookup.
   * @return the value of the property, not null
   */
  @Override
  public RatesMarketDataLookup getLookup() {
    return lookup;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the market data.
   * @return the value of the property, not null
   */
  @Override
  public ScenarioMarketData getMarketData() {
    return marketData;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      DefaultRatesScenarioMarketData other = (DefaultRatesScenarioMarketData) obj;
      return JodaBeanUtils.equal(lookup, other.lookup) &&
          JodaBeanUtils.equal(marketData, other.marketData);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(lookup);
    hash = hash * 31 + JodaBeanUtils.hashCode(marketData);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("DefaultRatesScenarioMarketData{");
    buf.append("lookup").append('=').append(lookup).append(',').append(' ');
    buf.append("marketData").append('=').append(JodaBeanUtils.toString(marketData));
    buf.append('}');
    return buf.toString();
  }

  //-------------------------- AUTOGENERATED END --------------------------
}
