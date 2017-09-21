/*
 * Copyright (C) 2017 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.measure;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

import com.opengamma.strata.collect.ArgChecker;
import com.opengamma.strata.data.scenario.MarketDataBox;
import com.opengamma.strata.data.scenario.ScenarioArray;

/**
 * Definition of valuation zone and time. 
 * <p>
 * This contains {@code ZoneId} and a set of {@code LocalTime} to create {@code ZonedDateTime} from {@code LocalDate}.
 */
@BeanDefinition(builderScope = "private")
public final class ValuationZoneTimeDefinition
    implements ImmutableBean, Serializable {

  /**
   * The local time.
   * <p>
   * The local time in {@code zoneId}. 
   * The number of the scenarios must be coherent to that of {@code ScenarioMarketData}.
   */
  @PropertyDefinition(validate = "notNull")
  private final ScenarioArray<LocalTime> localTimes;
  /**
   * The zone ID.
   */
  @PropertyDefinition(validate = "notNull", get = "private")
  private final ZoneId zoneId;

  /**
   * Obtains an instance.
   * 
   * @param localTimes  the local time
   * @param zoneId  the zone ID
   * @return the instance
   */
  public static ValuationZoneTimeDefinition of(ScenarioArray<LocalTime> localTimes, ZoneId zoneId) {
    return new ValuationZoneTimeDefinition(localTimes, zoneId);
  }

  /**
   * Creates zoned date time. 
   * 
   * @param dates  the local date
   * @return the zoned date time
   */
  public MarketDataBox<ZonedDateTime> toZonedDateTime(MarketDataBox<LocalDate> dates) {
    ArgChecker.isTrue(dates.getScenarioCount() == localTimes.getScenarioCount(),
        "the number of scenarios must be the same");
    if (dates.isScenarioValue()) {
      int nScenarios = dates.getScenarioCount();
      List<ZonedDateTime> zonedDateTimes = IntStream.range(0, nScenarios)
          .mapToObj(i -> dates.getValue(i).atTime(localTimes.get(i)).atZone(zoneId))
          .collect(Collectors.toList());
      return MarketDataBox.ofScenarioValues(zonedDateTimes);
    }
    ZonedDateTime zonedDateTime = dates.getSingleValue().atTime(localTimes.get(0)).atZone(zoneId);
    return MarketDataBox.ofSingleValue(zonedDateTime);
  }

  //------------------------- AUTOGENERATED START -------------------------
  /**
   * The meta-bean for {@code ValuationZoneTimeDefinition}.
   * @return the meta-bean, not null
   */
  public static ValuationZoneTimeDefinition.Meta meta() {
    return ValuationZoneTimeDefinition.Meta.INSTANCE;
  }

  static {
    MetaBean.register(ValuationZoneTimeDefinition.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private ValuationZoneTimeDefinition(
      ScenarioArray<LocalTime> localTimes,
      ZoneId zoneId) {
    JodaBeanUtils.notNull(localTimes, "localTimes");
    JodaBeanUtils.notNull(zoneId, "zoneId");
    this.localTimes = localTimes;
    this.zoneId = zoneId;
  }

  @Override
  public ValuationZoneTimeDefinition.Meta metaBean() {
    return ValuationZoneTimeDefinition.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the local time.
   * <p>
   * The local time in {@code zoneId}.
   * The number of the scenarios must be coherent to that of {@code ScenarioMarketData}.
   * @return the value of the property, not null
   */
  public ScenarioArray<LocalTime> getLocalTimes() {
    return localTimes;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the zone ID.
   * @return the value of the property, not null
   */
  private ZoneId getZoneId() {
    return zoneId;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      ValuationZoneTimeDefinition other = (ValuationZoneTimeDefinition) obj;
      return JodaBeanUtils.equal(localTimes, other.localTimes) &&
          JodaBeanUtils.equal(zoneId, other.zoneId);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(localTimes);
    hash = hash * 31 + JodaBeanUtils.hashCode(zoneId);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("ValuationZoneTimeDefinition{");
    buf.append("localTimes").append('=').append(localTimes).append(',').append(' ');
    buf.append("zoneId").append('=').append(JodaBeanUtils.toString(zoneId));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ValuationZoneTimeDefinition}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code localTimes} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<ScenarioArray<LocalTime>> localTimes = DirectMetaProperty.ofImmutable(
        this, "localTimes", ValuationZoneTimeDefinition.class, (Class) ScenarioArray.class);
    /**
     * The meta-property for the {@code zoneId} property.
     */
    private final MetaProperty<ZoneId> zoneId = DirectMetaProperty.ofImmutable(
        this, "zoneId", ValuationZoneTimeDefinition.class, ZoneId.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "localTimes",
        "zoneId");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1293230747:  // localTimes
          return localTimes;
        case -696323609:  // zoneId
          return zoneId;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends ValuationZoneTimeDefinition> builder() {
      return new ValuationZoneTimeDefinition.Builder();
    }

    @Override
    public Class<? extends ValuationZoneTimeDefinition> beanType() {
      return ValuationZoneTimeDefinition.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code localTimes} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ScenarioArray<LocalTime>> localTimes() {
      return localTimes;
    }

    /**
     * The meta-property for the {@code zoneId} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ZoneId> zoneId() {
      return zoneId;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 1293230747:  // localTimes
          return ((ValuationZoneTimeDefinition) bean).getLocalTimes();
        case -696323609:  // zoneId
          return ((ValuationZoneTimeDefinition) bean).getZoneId();
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
   * The bean-builder for {@code ValuationZoneTimeDefinition}.
   */
  private static final class Builder extends DirectPrivateBeanBuilder<ValuationZoneTimeDefinition> {

    private ScenarioArray<LocalTime> localTimes;
    private ZoneId zoneId;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1293230747:  // localTimes
          return localTimes;
        case -696323609:  // zoneId
          return zoneId;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 1293230747:  // localTimes
          this.localTimes = (ScenarioArray<LocalTime>) newValue;
          break;
        case -696323609:  // zoneId
          this.zoneId = (ZoneId) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public ValuationZoneTimeDefinition build() {
      return new ValuationZoneTimeDefinition(
          localTimes,
          zoneId);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(96);
      buf.append("ValuationZoneTimeDefinition.Builder{");
      buf.append("localTimes").append('=').append(JodaBeanUtils.toString(localTimes)).append(',').append(' ');
      buf.append("zoneId").append('=').append(JodaBeanUtils.toString(zoneId));
      buf.append('}');
      return buf.toString();
    }

  }

  //-------------------------- AUTOGENERATED END --------------------------
}
