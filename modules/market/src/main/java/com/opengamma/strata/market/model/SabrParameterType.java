/*
 * Copyright (C) 2016 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.market.model;

import org.joda.convert.FromString;
import org.joda.convert.ToString;

import com.opengamma.strata.collect.named.EnumNames;
import com.opengamma.strata.collect.named.NamedEnum;

/**
 * The type of the SABR parameter - Alpha, Beta, Rho, Nu or shift.
 */
public enum SabrParameterType implements NamedEnum {

  /**
   * SABR alpha.
   */
  ALPHA,
  /**
   * SABR beta.
   */
  BETA,
  /**
   * SABR rho.
   */
  RHO,
  /**
   * SABR nu.
   */
  NU,
  /**
   * SABR shift.
   */
  SHIFT;

  // helper for name conversions
  private static final EnumNames<SabrParameterType> NAMES = EnumNames.of(SabrParameterType.class);

  //-------------------------------------------------------------------------
  /**
   * Obtains an instance from the specified name.
   * <p>
   * Parsing handles the mixed case form produced by {@link #toString()} and
   * the upper and lower case variants of the enum constant name.
   * 
   * @param name  the name to parse
   * @return the type
   * @throws IllegalArgumentException if the name is not known
   */
  @FromString
  public static SabrParameterType of(String name) {
    return NAMES.parse(name);
  }

  //-------------------------------------------------------------------------
  /**
   * Returns the formatted name of the type.
   * 
   * @return the formatted string representing the type
   */
  @ToString
  @Override
  public String toString() {
    return NAMES.format(this);
  }

}
