/*
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.calc.runner;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;

import org.joda.beans.ImmutableBean;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaBean;
import org.joda.beans.TypedMetaBean;
import org.joda.beans.gen.BeanDefinition;
import org.joda.beans.gen.PropertyDefinition;
import org.joda.beans.impl.light.LightMetaBean;

import com.opengamma.strata.collect.Messages;
import com.opengamma.strata.collect.result.Result;
import com.opengamma.strata.data.scenario.ScenarioArray;

/**
 * The result of a single calculation.
 * <p>
 * This stores the calculated result for a single cell in the output grid.
 * A set of related results for a single target can be stored in a {@link CalculationResults} instance.
 */
@BeanDefinition(style = "light")
public final class CalculationResult
    implements ImmutableBean, Serializable {

  /**
   * The row index of the value in the results grid.
   */
  @PropertyDefinition
  private final int rowIndex;
  /**
   * The column index of the value in the results grid.
   */
  @PropertyDefinition
  private final int columnIndex;
  /**
   * The result of the calculation.
   * <p>
   * The result may be a single value or a multi-scenario value.
   * A multi-scenario value will implement {@link ScenarioArray} unless it has been aggregated.
   * <p>
   * If the calculation did not complete successfully, a failure result will be returned
   * explaining the problem. Callers must check whether the result is a success or failure
   * before examining the result value.
   */
  @PropertyDefinition(validate = "notNull")
  private final Result<?> result;

  //-------------------------------------------------------------------------
  /**
   * Obtains an instance for the specified row and column index in the output grid.
   * <p>
   * The {@link Result} object captures the result value, or the failure that
   * prevented the result from being calculated.
   *
   * @param rowIndex  the row index of the value in the results grid
   * @param columnIndex  the column index of the value in the results grid
   * @param result  the result of the calculation
   * @return a calculation result containing the row index, column index and result object
   */
  public static CalculationResult of(int rowIndex, int columnIndex, Result<?> result) {
    return new CalculationResult(rowIndex, columnIndex, result);
  }

  //-------------------------------------------------------------------------
  /**
   * Gets the result of the calculation, casting the result to a known type.
   * <p>
   * The result may be a single value or a multi-scenario value.
   * A multi-scenario value will implement {@link ScenarioArray} unless it has been aggregated.
   * <p>
   * If the calculation did not complete successfully, a failure result will be returned
   * explaining the problem. Callers must check whether the result is a success or failure
   * before examining the result value.
   *
   * @param <T>  the result type
   * @param type  the result type
   * @return the result, cast to the specified type
   * @throws ClassCastException if the result is not of the specified type
   */
  @SuppressWarnings("unchecked")
  public <T> Result<T> getResult(Class<T> type) {
    // cannot use result.map() as we want the exception to be thrown
    if (result.isFailure() || type.isInstance(result.getValue())) {
      return (Result<T>) result;
    }
    throw new ClassCastException(Messages.format(
        "Result queried with type '{}' but was '{}'", type.getName(), result.getValue().getClass().getName()));
  }

  //-------------------------------------------------------------------------
  /**
   * Returns a copy of this result with the underlying result updated.
   * 
   * @param underlyingResult  the new underlying result
   * @return a new instance with the result updated
   */
  public CalculationResult withResult(Result<?> underlyingResult) {
    return new CalculationResult(rowIndex, columnIndex, underlyingResult);
  }

  //------------------------- AUTOGENERATED START -------------------------
  /**
   * The meta-bean for {@code CalculationResult}.
   */
  private static final TypedMetaBean<CalculationResult> META_BEAN =
      LightMetaBean.of(CalculationResult.class, MethodHandles.lookup());

  /**
   * The meta-bean for {@code CalculationResult}.
   * @return the meta-bean, not null
   */
  public static TypedMetaBean<CalculationResult> meta() {
    return META_BEAN;
  }

  static {
    MetaBean.register(META_BEAN);
  }

  /**
   * The serialization version id.
   */
  private static final long serialVersionUID = 1L;

  private CalculationResult(
      int rowIndex,
      int columnIndex,
      Result<?> result) {
    JodaBeanUtils.notNull(result, "result");
    this.rowIndex = rowIndex;
    this.columnIndex = columnIndex;
    this.result = result;
  }

  @Override
  public TypedMetaBean<CalculationResult> metaBean() {
    return META_BEAN;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the row index of the value in the results grid.
   * @return the value of the property
   */
  public int getRowIndex() {
    return rowIndex;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the column index of the value in the results grid.
   * @return the value of the property
   */
  public int getColumnIndex() {
    return columnIndex;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the result of the calculation.
   * <p>
   * The result may be a single value or a multi-scenario value.
   * A multi-scenario value will implement {@link ScenarioArray} unless it has been aggregated.
   * <p>
   * If the calculation did not complete successfully, a failure result will be returned
   * explaining the problem. Callers must check whether the result is a success or failure
   * before examining the result value.
   * @return the value of the property, not null
   */
  public Result<?> getResult() {
    return result;
  }

  //-----------------------------------------------------------------------
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      CalculationResult other = (CalculationResult) obj;
      return (rowIndex == other.rowIndex) &&
          (columnIndex == other.columnIndex) &&
          JodaBeanUtils.equal(result, other.result);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash = hash * 31 + JodaBeanUtils.hashCode(rowIndex);
    hash = hash * 31 + JodaBeanUtils.hashCode(columnIndex);
    hash = hash * 31 + JodaBeanUtils.hashCode(result);
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(128);
    buf.append("CalculationResult{");
    buf.append("rowIndex").append('=').append(rowIndex).append(',').append(' ');
    buf.append("columnIndex").append('=').append(columnIndex).append(',').append(' ');
    buf.append("result").append('=').append(JodaBeanUtils.toString(result));
    buf.append('}');
    return buf.toString();
  }

  //-------------------------- AUTOGENERATED END --------------------------
}
