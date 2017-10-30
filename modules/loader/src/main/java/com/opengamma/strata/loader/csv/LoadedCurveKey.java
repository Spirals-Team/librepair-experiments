/*
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.loader.csv;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;

import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.TypedMetaBean;
import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.PropertyDefinition;
import org.joda.beans.impl.light.LightMetaBean;

import com.opengamma.strata.market.curve.CurveName;

/**
 * Identifies an instance of a named curve on a specific date.
 */
@BeanDefinition(style = "light")
final class LoadedCurveKey
    implements ImmutableBean {

  /**
   * The curve date.
   */
  @PropertyDefinition(validate = "notNull")
  private final LocalDate curveDate;
  /**
   * The curve name.
   */
  @PropertyDefinition(validate = "notNull")
  private final CurveName curveName;

  //-------------------------------------------------------------------------
  /**
   * Obtains an instance from typed strings where applicable.
   * 
   * @param curveDate  the curve date
   * @param curveName  the curve name
   * @return the curve key
   */
  static LoadedCurveKey of(LocalDate curveDate, CurveName curveName) {
    return new LoadedCurveKey(curveDate, curveName);
  }

  //------------------------- AUTOGENERATED START -------------------------
  /**
   * The meta-bean for {@code LoadedCurveKey}.
   */
  private static final TypedMetaBean<LoadedCurveKey> META_BEAN =
      LightMetaBean.of(
          LoadedCurveKey.class,
          MethodHandles.lookup(),
          new String[] {
              "curveDate",
              "curveName"},
          new Object[0]);

  /**
   * The meta-bean for {@code LoadedCurveKey}.
   * @return the meta-bean, not null
   */
  public static TypedMetaBean<LoadedCurveKey> meta() {
    return META_BEAN;
  }

  static {
    MetaBean.register(META_BEAN);
  }

  private LoadedCurveKey(
      LocalDate curveDate,
      CurveName curveName) {
    JodaBeanUtils.notNull(curveDate, "curveDate");
    JodaBeanUtils.notNull(curveName, "curveName");
    this.curveDate = curveDate;
    this.curveName = curveName;
  }

  @Override
  public TypedMetaBean<LoadedCurveKey> metaBean() {
    return META_BEAN;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the curve date.
   * @return the value of the property, not null
   */
  public LocalDate getCurveDate() {
    return curveDate;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the curve name.
   * @return the value of the property, not null
   */
  public CurveName getCurveName() {
    return curveName;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      LoadedCurveKey other = (LoadedCurveKey) obj;
      return JodaBeanUtils.equal(curveDate, other.curveDate) &&
          JodaBeanUtils.equal(curveName, other.curveName);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(curveDate);
    hash = hash * 31 + JodaBeanUtils.hashCode(curveName);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(96);
    buf.append("LoadedCurveKey{");
    buf.append("curveDate").append('=').append(curveDate).append(',').append(' ');
    buf.append("curveName").append('=').append(JodaBeanUtils.toString(curveName));
    buf.append('}');
    return buf.toString();
  }

  //-------------------------- AUTOGENERATED END --------------------------
}
