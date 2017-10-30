/*
 * Copyright (C) 2016 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.product.dsf;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;
import java.util.NoSuchElementException;

import org.joda.beans.Bean;
import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.MetaProperty;
import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.DerivedProperty;
import org.joda.beans.gen.ImmutableValidator;
import org.joda.beans.gen.PropertyDefinition;
import org.joda.beans.impl.direct.DirectFieldsBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.ImmutableSet;
import com.opengamma.strata.basics.ReferenceData;
import com.opengamma.strata.basics.currency.Currency;
import com.opengamma.strata.collect.ArgChecker;
import com.opengamma.strata.product.Security;
import com.opengamma.strata.product.SecurityId;
import com.opengamma.strata.product.SecurityInfo;
import com.opengamma.strata.product.TradeInfo;
import com.opengamma.strata.product.swap.RateCalculationSwapLeg;
import com.opengamma.strata.product.swap.Swap;
import com.opengamma.strata.product.swap.SwapLeg;
import com.opengamma.strata.product.swap.SwapLegType;

/**
 * A security representing a deliverable swap futures security.
 * <p>
 * A deliverable swap future is a financial instrument that physically settles
 * an interest rate swap on a future date.
 * The delivered swap is cleared by a central counterparty.
 * The last future price before delivery is quoted in term of the underlying swap present value.
 * The futures product is margined on a daily basis.
 * 
 * <h4>Price</h4>
 * The price of a DSF is based on the present value (NPV) of the underlying swap on the delivery date.
 * For example, a price of 100.182 represents a present value of $100,182.00, if the notional is $100,000.
 * This price can also be viewed as a percentage present value - {@code (100 + percentPv)}, or 0.182% in this example.
 * <p>
 * Strata uses <i>decimal prices</i> for DSFs in the trade model, pricers and market data.
 * The decimal price is based on the decimal multiplier equivalent to the implied percentage.
 * Thus the market price of 100.182 is represented in Strata by 1.00182.
 */
@BeanDefinition
public final class DsfSecurity
    implements Security, ImmutableBean, Serializable {

  /**
   * The standard security information.
   * <p>
   * This includes the security identifier.
   */
  @PropertyDefinition(validate = "notNull", overrideGet = true)
  private final SecurityInfo info;
  /**
   * The notional.
   * <p>
   * This is also called face value or contract value.
   */
  @PropertyDefinition(validate = "ArgChecker.notNegativeOrZero")
  private final double notional;
  /**
   * The last date of trading.
   * <p>
   * This date must be before the delivery date of the underlying swap.
   */
  @PropertyDefinition(validate = "notNull")
  private final LocalDate lastTradeDate;
  /**
   * The underlying swap.
   * <p>
   * The delivery date of the future is the start date of the swap.
   * The swap must be a single currency swap with a notional of 1.
   * There must be two legs, the fixed leg must be received and the floating rate must be paid.
   */
  @PropertyDefinition(validate = "notNull")
  private final Swap underlyingSwap;

  //-------------------------------------------------------------------------
  @ImmutableValidator
  private void validate() {
    ArgChecker.isFalse(underlyingSwap.isCrossCurrency(), "Underlying swap must not be cross currency");
    for (SwapLeg swapLeg : underlyingSwap.getLegs()) {
      if (swapLeg.getType().equals(SwapLegType.FIXED)) {
        ArgChecker.isTrue(swapLeg.getPayReceive().isReceive(), "Underlying swap must receive the fixed leg");
      }
      if (swapLeg instanceof RateCalculationSwapLeg) {
        RateCalculationSwapLeg leg = (RateCalculationSwapLeg) swapLeg;
        ArgChecker.isTrue(Math.abs(leg.getNotionalSchedule().getAmount().getInitialValue()) == 1d,
            "Underlying swap must have a notional of 1");
      }
    }
  }

  //-------------------------------------------------------------------------
  @Override
  @DerivedProperty
  public Currency getCurrency() {
    return underlyingSwap.getLegs().get(0).getCurrency();
  }

  @Override
  public ImmutableSet<SecurityId> getUnderlyingIds() {
    return ImmutableSet.of();
  }

  //-------------------------------------------------------------------------
  @Override
  public Dsf createProduct(ReferenceData refData) {
    LocalDate deliveryDate = underlyingSwap.getStartDate().getUnadjusted();
    return new Dsf(getSecurityId(), notional, lastTradeDate, deliveryDate, underlyingSwap);
  }

  @Override
  public DsfTrade createTrade(
      TradeInfo info,
      double quantity,
      double tradePrice,
      ReferenceData refData) {

    return new DsfTrade(info, createProduct(refData), quantity, tradePrice);
  }

  //------------------------- AUTOGENERATED START -------------------------
  /**
   * The meta-bean for {@code DsfSecurity}.
   * @return the meta-bean, not null
   */
  public static DsfSecurity.Meta meta() {
    return DsfSecurity.Meta.INSTANCE;
  }

  static {
    MetaBean.register(DsfSecurity.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Returns a builder used to create an instance of the bean.
   * @return the builder, not null
   */
  public static DsfSecurity.Builder builder() {
    return new DsfSecurity.Builder();
  }

  private DsfSecurity(
      SecurityInfo info,
      double notional,
      LocalDate lastTradeDate,
      Swap underlyingSwap) {
    JodaBeanUtils.notNull(info, "info");
    ArgChecker.notNegativeOrZero(notional, "notional");
    JodaBeanUtils.notNull(lastTradeDate, "lastTradeDate");
    JodaBeanUtils.notNull(underlyingSwap, "underlyingSwap");
    this.info = info;
    this.notional = notional;
    this.lastTradeDate = lastTradeDate;
    this.underlyingSwap = underlyingSwap;
    validate();
  }

  @Override
  public DsfSecurity.Meta metaBean() {
    return DsfSecurity.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the standard security information.
   * <p>
   * This includes the security identifier.
   * @return the value of the property, not null
   */
  @Override
  public SecurityInfo getInfo() {
    return info;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the notional.
   * <p>
   * This is also called face value or contract value.
   * @return the value of the property
   */
  public double getNotional() {
    return notional;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the last date of trading.
   * <p>
   * This date must be before the delivery date of the underlying swap.
   * @return the value of the property, not null
   */
  public LocalDate getLastTradeDate() {
    return lastTradeDate;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the underlying swap.
   * <p>
   * The delivery date of the future is the start date of the swap.
   * The swap must be a single currency swap with a notional of 1.
   * There must be two legs, the fixed leg must be received and the floating rate must be paid.
   * @return the value of the property, not null
   */
  public Swap getUnderlyingSwap() {
    return underlyingSwap;
  }

  //-----------------------------------------------------------------------
  /**
   * Returns a builder that allows this bean to be mutated.
   * @return the mutable builder, not null
   */
  public Builder toBuilder() {
    return new Builder(this);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      DsfSecurity other = (DsfSecurity) obj;
      return JodaBeanUtils.equal(info, other.info) &&
          JodaBeanUtils.equal(notional, other.notional) &&
          JodaBeanUtils.equal(lastTradeDate, other.lastTradeDate) &&
          JodaBeanUtils.equal(underlyingSwap, other.underlyingSwap);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(info);
    hash = hash * 31 + JodaBeanUtils.hashCode(notional);
    hash = hash * 31 + JodaBeanUtils.hashCode(lastTradeDate);
    hash = hash * 31 + JodaBeanUtils.hashCode(underlyingSwap);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(160);
    buf.append("DsfSecurity{");
    buf.append("info").append('=').append(info).append(',').append(' ');
    buf.append("notional").append('=').append(notional).append(',').append(' ');
    buf.append("lastTradeDate").append('=').append(lastTradeDate).append(',').append(' ');
    buf.append("underlyingSwap").append('=').append(JodaBeanUtils.toString(underlyingSwap));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code DsfSecurity}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code info} property.
     */
    private final MetaProperty<SecurityInfo> info = DirectMetaProperty.ofImmutable(
        this, "info", DsfSecurity.class, SecurityInfo.class);
    /**
     * The meta-property for the {@code notional} property.
     */
    private final MetaProperty<Double> notional = DirectMetaProperty.ofImmutable(
        this, "notional", DsfSecurity.class, Double.TYPE);
    /**
     * The meta-property for the {@code lastTradeDate} property.
     */
    private final MetaProperty<LocalDate> lastTradeDate = DirectMetaProperty.ofImmutable(
        this, "lastTradeDate", DsfSecurity.class, LocalDate.class);
    /**
     * The meta-property for the {@code underlyingSwap} property.
     */
    private final MetaProperty<Swap> underlyingSwap = DirectMetaProperty.ofImmutable(
        this, "underlyingSwap", DsfSecurity.class, Swap.class);
    /**
     * The meta-property for the {@code currency} property.
     */
    private final MetaProperty<Currency> currency = DirectMetaProperty.ofDerived(
        this, "currency", DsfSecurity.class, Currency.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "info",
        "notional",
        "lastTradeDate",
        "underlyingSwap",
        "currency");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3237038:  // info
          return info;
        case 1585636160:  // notional
          return notional;
        case -1041950404:  // lastTradeDate
          return lastTradeDate;
        case 1497421456:  // underlyingSwap
          return underlyingSwap;
        case 575402001:  // currency
          return currency;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public DsfSecurity.Builder builder() {
      return new DsfSecurity.Builder();
    }

    @Override
    public Class<? extends DsfSecurity> beanType() {
      return DsfSecurity.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code info} property.
     * @return the meta-property, not null
     */
    public MetaProperty<SecurityInfo> info() {
      return info;
    }

    /**
     * The meta-property for the {@code notional} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Double> notional() {
      return notional;
    }

    /**
     * The meta-property for the {@code lastTradeDate} property.
     * @return the meta-property, not null
     */
    public MetaProperty<LocalDate> lastTradeDate() {
      return lastTradeDate;
    }

    /**
     * The meta-property for the {@code underlyingSwap} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Swap> underlyingSwap() {
      return underlyingSwap;
    }

    /**
     * The meta-property for the {@code currency} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Currency> currency() {
      return currency;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 3237038:  // info
          return ((DsfSecurity) bean).getInfo();
        case 1585636160:  // notional
          return ((DsfSecurity) bean).getNotional();
        case -1041950404:  // lastTradeDate
          return ((DsfSecurity) bean).getLastTradeDate();
        case 1497421456:  // underlyingSwap
          return ((DsfSecurity) bean).getUnderlyingSwap();
        case 575402001:  // currency
          return ((DsfSecurity) bean).getCurrency();
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
   * The bean-builder for {@code DsfSecurity}.
   */
  public static final class Builder extends DirectFieldsBeanBuilder<DsfSecurity> {

    private SecurityInfo info;
    private double notional;
    private LocalDate lastTradeDate;
    private Swap underlyingSwap;

    /**
     * Restricted constructor.
     */
    private Builder() {
    }

    /**
     * Restricted copy constructor.
     * @param beanToCopy  the bean to copy from, not null
     */
    private Builder(DsfSecurity beanToCopy) {
      this.info = beanToCopy.getInfo();
      this.notional = beanToCopy.getNotional();
      this.lastTradeDate = beanToCopy.getLastTradeDate();
      this.underlyingSwap = beanToCopy.getUnderlyingSwap();
    }

    //-----------------------------------------------------------------------
    @Override
    public Object get(String propertyName) {
      switch (propertyName.hashCode()) {
        case 3237038:  // info
          return info;
        case 1585636160:  // notional
          return notional;
        case -1041950404:  // lastTradeDate
          return lastTradeDate;
        case 1497421456:  // underlyingSwap
          return underlyingSwap;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 3237038:  // info
          this.info = (SecurityInfo) newValue;
          break;
        case 1585636160:  // notional
          this.notional = (Double) newValue;
          break;
        case -1041950404:  // lastTradeDate
          this.lastTradeDate = (LocalDate) newValue;
          break;
        case 1497421456:  // underlyingSwap
          this.underlyingSwap = (Swap) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public Builder set(MetaProperty<?> property, Object value) {
      super.set(property, value);
      return this;
    }

    @Override
    public DsfSecurity build() {
      return new DsfSecurity(
          info,
          notional,
          lastTradeDate,
          underlyingSwap);
    }

    //-----------------------------------------------------------------------
    /**
     * Sets the standard security information.
     * <p>
     * This includes the security identifier.
     * @param info  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder info(SecurityInfo info) {
      JodaBeanUtils.notNull(info, "info");
      this.info = info;
      return this;
    }

    /**
     * Sets the notional.
     * <p>
     * This is also called face value or contract value.
     * @param notional  the new value
     * @return this, for chaining, not null
     */
    public Builder notional(double notional) {
      ArgChecker.notNegativeOrZero(notional, "notional");
      this.notional = notional;
      return this;
    }

    /**
     * Sets the last date of trading.
     * <p>
     * This date must be before the delivery date of the underlying swap.
     * @param lastTradeDate  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder lastTradeDate(LocalDate lastTradeDate) {
      JodaBeanUtils.notNull(lastTradeDate, "lastTradeDate");
      this.lastTradeDate = lastTradeDate;
      return this;
    }

    /**
     * Sets the underlying swap.
     * <p>
     * The delivery date of the future is the start date of the swap.
     * The swap must be a single currency swap with a notional of 1.
     * There must be two legs, the fixed leg must be received and the floating rate must be paid.
     * @param underlyingSwap  the new value, not null
     * @return this, for chaining, not null
     */
    public Builder underlyingSwap(Swap underlyingSwap) {
      JodaBeanUtils.notNull(underlyingSwap, "underlyingSwap");
      this.underlyingSwap = underlyingSwap;
      return this;
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(160);
      buf.append("DsfSecurity.Builder{");
      buf.append("info").append('=').append(JodaBeanUtils.toString(info)).append(',').append(' ');
      buf.append("notional").append('=').append(JodaBeanUtils.toString(notional)).append(',').append(' ');
      buf.append("lastTradeDate").append('=').append(JodaBeanUtils.toString(lastTradeDate)).append(',').append(' ');
      buf.append("underlyingSwap").append('=').append(JodaBeanUtils.toString(underlyingSwap));
      buf.append('}');
      return buf.toString();
    }

  }

  //-------------------------- AUTOGENERATED END --------------------------
}
