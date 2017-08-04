/*
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.product.swap.type;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.joda.convert.FromString;
import org.joda.convert.ToString;

import com.opengamma.strata.basics.ReferenceData;
import com.opengamma.strata.basics.ReferenceDataNotFoundException;
import com.opengamma.strata.basics.date.DaysAdjustment;
import com.opengamma.strata.basics.date.Tenor;
import com.opengamma.strata.collect.ArgChecker;
import com.opengamma.strata.collect.named.Named;
import com.opengamma.strata.product.TradeConvention;
import com.opengamma.strata.product.TradeInfo;
import com.opengamma.strata.product.common.BuySell;
import com.opengamma.strata.product.swap.SwapTrade;

/**
 * A market convention for swap trades.
 * <p>
 * This defines the market convention for a a swap.
 * Each different type of swap has its own convention - this interface provides an abstraction.
 */
public interface SingleCurrencySwapConvention
    extends TradeConvention, Named {

  /**
   * Obtains an instance from the specified unique name.
   *
   * @param uniqueName  the unique name
   * @return the convention
   * @throws IllegalArgumentException if the name is not known
   */
  @FromString
  public static SingleCurrencySwapConvention of(String uniqueName) {
    ArgChecker.notNull(uniqueName, "uniqueName");
    Optional<? extends SingleCurrencySwapConvention> conv = FixedIborSwapConvention.extendedEnum().find(uniqueName);
    if (!conv.isPresent()) {
      conv = IborIborSwapConvention.extendedEnum().find(uniqueName);
    }
    if (!conv.isPresent()) {
      conv = FixedOvernightSwapConvention.extendedEnum().find(uniqueName);
    }
    if (!conv.isPresent()) {
      conv = OvernightIborSwapConvention.extendedEnum().find(uniqueName);
    }
    if (!conv.isPresent()) {
      conv = FixedInflationSwapConvention.extendedEnum().find(uniqueName);
    }
    if (!conv.isPresent()) {
      conv = ThreeLegBasisSwapConvention.extendedEnum().find(uniqueName);
    }
    return conv.orElseThrow(() -> new IllegalArgumentException("SingleCurrencySwapConvention not found: " + uniqueName));
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the offset of the spot value date from the trade date.
   * <p>
   * The offset is applied to the trade date to find the start date.
   * A typical value is "plus 2 business days".
   *
   * @return the spot date offset, not null
   */
  public abstract DaysAdjustment getSpotDateOffset();

  //-------------------------------------------------------------------------
  /**
   * Creates a spot-starting trade based on this convention.
   * <p>
   * This returns a trade based on the specified tenor. For example, a tenor
   * of 5 years creates a swap starting on the spot date and maturing 5 years later.
   *
   * @param tradeDate  the date of the trade
   * @param tenor  the tenor of the swap
   * @param buySell  the buy/sell flag
   * @param notional  the notional amount
   * @param fixedRateOrSpread  the fixed rate or spread, typically derived from the market
   * @param refData  the reference data, used to resolve the trade dates
   * @return the trade
   * @throws ReferenceDataNotFoundException if an identifier cannot be resolved in the reference data
   */
  public abstract SwapTrade createTrade(
      LocalDate tradeDate,
      Tenor tenor,
      BuySell buySell,
      double notional,
      double fixedRateOrSpread,
      ReferenceData refData);

  /**
   * Creates a forward-starting trade based on this convention.
   * <p>
   * This returns a trade based on the specified period and tenor. For example, a period of
   * 3 months and a tenor of 5 years creates a swap starting three months after the spot date
   * and maturing 5 years later.
   *
   * @param tradeDate  the date of the trade
   * @param periodToStart  the period between the spot date and the start date
   * @param tenor  the tenor of the swap
   * @param buySell  the buy/sell flag
   * @param notional  the notional amount
   * @param fixedRateOrSpread  the fixed rate or spread, typically derived from the market
   * @param refData  the reference data, used to resolve the trade dates
   * @return the trade
   * @throws ReferenceDataNotFoundException if an identifier cannot be resolved in the reference data
   */
  public abstract SwapTrade createTrade(
      LocalDate tradeDate,
      Period periodToStart,
      Tenor tenor,
      BuySell buySell,
      double notional,
      double fixedRateOrSpread,
      ReferenceData refData);

  /**
   * Creates a trade based on this convention.
   * <p>
   * This returns a trade based on the specified dates.
   *
   * @param tradeDate  the date of the trade
   * @param startDate  the start date
   * @param endDate  the end date
   * @param buySell  the buy/sell flag
   * @param notional  the notional amount
   * @param fixedRateOrSpread  the fixed rate or spread, typically derived from the market
   * @return the trade
   */
  public abstract SwapTrade toTrade(
      LocalDate tradeDate,
      LocalDate startDate,
      LocalDate endDate,
      BuySell buySell,
      double notional,
      double fixedRateOrSpread);

  /**
   * Creates a trade based on this convention.
   * <p>
   * This returns a trade based on the specified dates.
   *
   * @param tradeInfo  additional information about the trade
   * @param startDate  the start date
   * @param endDate  the end date
   * @param buySell  the buy/sell flag
   * @param notional  the notional amount
   * @param fixedRateOrSpread  the fixed rate or spread, typically derived from the market
   * @return the trade
   */
  public abstract SwapTrade toTrade(
      TradeInfo tradeInfo,
      LocalDate startDate,
      LocalDate endDate,
      BuySell buySell,
      double notional,
      double fixedRateOrSpread);

  //-------------------------------------------------------------------------
  /**
   * Calculates the spot date from the trade date.
   *
   * @param tradeDate  the trade date
   * @param refData  the reference data, used to resolve the date
   * @return the spot date
   * @throws ReferenceDataNotFoundException if an identifier cannot be resolved in the reference data
   */
  public default LocalDate calculateSpotDateFromTradeDate(LocalDate tradeDate, ReferenceData refData) {
    return getSpotDateOffset().adjust(tradeDate, refData);
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the name that uniquely identifies this convention.
   * <p>
   * This name is used in serialization and can be parsed using {@link #of(String)}.
   *
   * @return the unique name
   */
  @ToString
  @Override
  public abstract String getName();

}
