/*
 * Copyright (C) 2016 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.measure.swaption;

import com.opengamma.strata.data.scenario.ScenarioMarketData;

/**
 * Market data for swaptions, used for calculation across multiple scenarios.
 * <p>
 * This interface exposes the market data necessary for pricing a swaption.
 * <p>
 * Implementations of this interface must be immutable.
 */
public interface SwaptionScenarioMarketData {

  /**
   * Gets the lookup that provides access to swaption volatilities.
   * 
   * @return the swaption lookup
   */
  public abstract SwaptionMarketDataLookup getLookup();

  /**
   * Gets the market data.
   * 
   * @return the market data
   */
  public abstract ScenarioMarketData getMarketData();

  /**
   * Returns a copy of this instance with the specified market data.
   * 
   * @param marketData  the market data to use
   * @return a market view based on the specified data
   */
  public abstract SwaptionScenarioMarketData withMarketData(ScenarioMarketData marketData);

  //-------------------------------------------------------------------------
  /**
   * Gets the number of scenarios.
   * 
   * @return the number of scenarios
   */
  public abstract int getScenarioCount();

  /**
   * Returns market data for a single scenario.
   * <p>
   * This returns a view of the market data for the specified scenario.
   * 
   * @param scenarioIndex  the scenario index
   * @return the market data for the specified scenario
   * @throws IndexOutOfBoundsException if the scenario index is invalid
   */
  public abstract SwaptionMarketData scenario(int scenarioIndex);

}
