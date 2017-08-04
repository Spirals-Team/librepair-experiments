/*
 * Copyright (C) 2016 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.product.fxopt;

import java.io.Serializable;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.ImmutableBean;
import org.joda.beans.ImmutableValidator;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.joda.beans.impl.direct.DirectPrivateBeanBuilder;

import com.opengamma.strata.basics.currency.CurrencyAmount;
import com.opengamma.strata.basics.currency.CurrencyPair;
import com.opengamma.strata.collect.ArgChecker;
import com.opengamma.strata.product.ResolvedProduct;
import com.opengamma.strata.product.option.Barrier;

/**
 * Resolved FX (European) single barrier option.
 * <p>
 * An FX option is a financial instrument that provides an option to exchange two currencies at a specified future time 
 * only when barrier event occurs (knock-in option) or does not occur (knock-out option).
 * <p>
 * Depending on the barrier defined in {@link Barrier}, the options are classified into four types: up-and-in, 
 * down-and-in, up-and-out and down-and-out.
 * <p>
 * For example, an up-and-out call on a 'EUR 1.00 / USD -1.41' exchange with barrier of 1.5 is the option to
 * perform a foreign exchange on the expiry date, where USD 1.41 is paid to receive EUR 1.00, only when EUR/USD rate does 
 * not exceed 1.5 during the barrier event observation period.
 * <p>
 * In case of the occurrence (non-occurrence for knock-in options) of the barrier event, the option becomes worthless, 
 * or alternatively, a rebate is made.
 */
@BeanDefinition(builderScope = "private")
public final class ResolvedFxSingleBarrierOption
    implements ResolvedProduct, ImmutableBean, Serializable {

  /**
   * The underlying FX vanilla option.
   */
  @PropertyDefinition(validate = "notNull")
  private final ResolvedFxVanillaOption underlyingOption;
  /**
   * The barrier description.
   * <p>
   * The barrier level stored in this field must be represented based on the direction of the currency pair in the 
   * underlying FX transaction.
   * <p>
   * For example, if the underlying option is an option on EUR/GBP, the barrier should be a certain level of EUR/GBP rate.
   */
  @PropertyDefinition(validate = "notNull")
  private final Barrier barrier;
  /**
   * For a 'out' option, the amount is paid when the barrier is reached; 
   * for a 'in' option, the amount is paid at expiry if the barrier is not reached.
   * <p>
   * This is the notional amount represented in one of the currency pair.
   * The amount should be positive.
   */
  @PropertyDefinition(get = "optional")
  private final CurrencyAmount rebate;

  //-------------------------------------------------------------------------
  /**
   * Obtains FX single barrier option with rebate.
   * 
   * @param underlyingOption  the underlying FX vanilla option
   * @param barrier  the barrier
   * @param rebate  the rebate
   * @return the instance
   */
  public static ResolvedFxSingleBarrierOption of(
      ResolvedFxVanillaOption underlyingOption,
      Barrier barrier,
      CurrencyAmount rebate) {

    return new ResolvedFxSingleBarrierOption(underlyingOption, barrier, rebate);
  }

  /**
   * Obtains FX single barrier option without rebate.
   * 
   * @param underlyingOption  the underlying FX vanilla option
   * @param barrier  the barrier
   * @return the instance
   */
  public static ResolvedFxSingleBarrierOption of(ResolvedFxVanillaOption underlyingOption, Barrier barrier) {

    return new ResolvedFxSingleBarrierOption(underlyingOption, barrier, null);
  }

  //-------------------------------------------------------------------------
  @ImmutableValidator
  private void validate() {
    if (rebate != null) {
      ArgChecker.isTrue(rebate.getAmount() > 0d, "rebate must be positive");
      ArgChecker.isTrue(underlyingOption.getUnderlying().getCurrencyPair().contains(rebate.getCurrency()),
          "The rebate currency must be one of underlying currency pair");
    }
  }

  //-------------------------------------------------------------------------
  /**
   * Gets currency pair of the base currency and counter currency.
   * <p>
   * This currency pair is conventional, thus indifferent to the direction of FX.
   * 
   * @return the currency pair
   */
  public CurrencyPair getCurrencyPair() {
    return underlyingOption.getCurrencyPair();
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code ResolvedFxSingleBarrierOption}.
   * @return the meta-bean, not null
   */
  public static ResolvedFxSingleBarrierOption.Meta meta() {
    return ResolvedFxSingleBarrierOption.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(ResolvedFxSingleBarrierOption.Meta.INSTANCE);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private ResolvedFxSingleBarrierOption(
      ResolvedFxVanillaOption underlyingOption,
      Barrier barrier,
      CurrencyAmount rebate) {
    JodaBeanUtils.notNull(underlyingOption, "underlyingOption");
    JodaBeanUtils.notNull(barrier, "barrier");
    this.underlyingOption = underlyingOption;
    this.barrier = barrier;
    this.rebate = rebate;
    validate();
  }

  @Override
  public ResolvedFxSingleBarrierOption.Meta metaBean() {
    return ResolvedFxSingleBarrierOption.Meta.INSTANCE;
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
   * Gets the underlying FX vanilla option.
   * @return the value of the property, not null
   */
  public ResolvedFxVanillaOption getUnderlyingOption() {
    return underlyingOption;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the barrier description.
   * <p>
   * The barrier level stored in this field must be represented based on the direction of the currency pair in the
   * underlying FX transaction.
   * <p>
   * For example, if the underlying option is an option on EUR/GBP, the barrier should be a certain level of EUR/GBP rate.
   * @return the value of the property, not null
   */
  public Barrier getBarrier() {
    return barrier;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets for a 'out' option, the amount is paid when the barrier is reached;
   * for a 'in' option, the amount is paid at expiry if the barrier is not reached.
   * <p>
   * This is the notional amount represented in one of the currency pair.
   * The amount should be positive.
   * @return the optional value of the property, not null
   */
  public Optional<CurrencyAmount> getRebate() {
    return Optional.ofNullable(rebate);
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      ResolvedFxSingleBarrierOption other = (ResolvedFxSingleBarrierOption) obj;
      return JodaBeanUtils.equal(underlyingOption, other.underlyingOption) &&
          JodaBeanUtils.equal(barrier, other.barrier) &&
          JodaBeanUtils.equal(rebate, other.rebate);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(underlyingOption);
    hash = hash * 31 + JodaBeanUtils.hashCode(barrier);
    hash = hash * 31 + JodaBeanUtils.hashCode(rebate);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(128);
    buf.append("ResolvedFxSingleBarrierOption{");
    buf.append("underlyingOption").append('=').append(underlyingOption).append(',').append(' ');
    buf.append("barrier").append('=').append(barrier).append(',').append(' ');
    buf.append("rebate").append('=').append(JodaBeanUtils.toString(rebate));
    buf.append('}');
    return buf.toString();
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code ResolvedFxSingleBarrierOption}.
   */
  public static final class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code underlyingOption} property.
     */
    private final MetaProperty<ResolvedFxVanillaOption> underlyingOption = DirectMetaProperty.ofImmutable(
        this, "underlyingOption", ResolvedFxSingleBarrierOption.class, ResolvedFxVanillaOption.class);
    /**
     * The meta-property for the {@code barrier} property.
     */
    private final MetaProperty<Barrier> barrier = DirectMetaProperty.ofImmutable(
        this, "barrier", ResolvedFxSingleBarrierOption.class, Barrier.class);
    /**
     * The meta-property for the {@code rebate} property.
     */
    private final MetaProperty<CurrencyAmount> rebate = DirectMetaProperty.ofImmutable(
        this, "rebate", ResolvedFxSingleBarrierOption.class, CurrencyAmount.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "underlyingOption",
        "barrier",
        "rebate");

    /**
     * Restricted constructor.
     */
    private Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 87556658:  // underlyingOption
          return underlyingOption;
        case -333143113:  // barrier
          return barrier;
        case -934952029:  // rebate
          return rebate;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends ResolvedFxSingleBarrierOption> builder() {
      return new ResolvedFxSingleBarrierOption.Builder();
    }

    @Override
    public Class<? extends ResolvedFxSingleBarrierOption> beanType() {
      return ResolvedFxSingleBarrierOption.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code underlyingOption} property.
     * @return the meta-property, not null
     */
    public MetaProperty<ResolvedFxVanillaOption> underlyingOption() {
      return underlyingOption;
    }

    /**
     * The meta-property for the {@code barrier} property.
     * @return the meta-property, not null
     */
    public MetaProperty<Barrier> barrier() {
      return barrier;
    }

    /**
     * The meta-property for the {@code rebate} property.
     * @return the meta-property, not null
     */
    public MetaProperty<CurrencyAmount> rebate() {
      return rebate;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 87556658:  // underlyingOption
          return ((ResolvedFxSingleBarrierOption) bean).getUnderlyingOption();
        case -333143113:  // barrier
          return ((ResolvedFxSingleBarrierOption) bean).getBarrier();
        case -934952029:  // rebate
          return ((ResolvedFxSingleBarrierOption) bean).rebate;
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
   * The bean-builder for {@code ResolvedFxSingleBarrierOption}.
   */
  private static final class Builder extends DirectPrivateBeanBuilder<ResolvedFxSingleBarrierOption> {

    private ResolvedFxVanillaOption underlyingOption;
    private Barrier barrier;
    private CurrencyAmount rebate;

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
        case 87556658:  // underlyingOption
          return underlyingOption;
        case -333143113:  // barrier
          return barrier;
        case -934952029:  // rebate
          return rebate;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
    }

    @Override
    public Builder set(String propertyName, Object newValue) {
      switch (propertyName.hashCode()) {
        case 87556658:  // underlyingOption
          this.underlyingOption = (ResolvedFxVanillaOption) newValue;
          break;
        case -333143113:  // barrier
          this.barrier = (Barrier) newValue;
          break;
        case -934952029:  // rebate
          this.rebate = (CurrencyAmount) newValue;
          break;
        default:
          throw new NoSuchElementException("Unknown property: " + propertyName);
      }
      return this;
    }

    @Override
    public ResolvedFxSingleBarrierOption build() {
      return new ResolvedFxSingleBarrierOption(
          underlyingOption,
          barrier,
          rebate);
    }

    //-----------------------------------------------------------------------
    @Override
    public String toString() {
      StringBuilder buf = new StringBuilder(128);
      buf.append("ResolvedFxSingleBarrierOption.Builder{");
      buf.append("underlyingOption").append('=').append(JodaBeanUtils.toString(underlyingOption)).append(',').append(' ');
      buf.append("barrier").append('=').append(JodaBeanUtils.toString(barrier)).append(',').append(' ');
      buf.append("rebate").append('=').append(JodaBeanUtils.toString(rebate));
      buf.append('}');
      return buf.toString();
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
