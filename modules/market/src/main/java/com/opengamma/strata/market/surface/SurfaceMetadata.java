/*
 * Copyright (C) 2015 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.strata.market.surface;

import java.util.List;
import java.util.Optional;

import com.opengamma.strata.basics.date.Tenor;
import com.opengamma.strata.collect.Messages;
import com.opengamma.strata.market.ValueType;
import com.opengamma.strata.market.param.ParameterMetadata;

/**
 * Metadata about a surface and surface parameters.
 * <p>
 * Implementations of this interface are used to store metadata about a surface.
 * For example, a surface may be defined based on financial instruments.
 * The parameters might represent 1 day, 1 week, 1 month, 3 month and 6 months.
 * The metadata could be used to describe each parameter in terms of a {@link Tenor}.
 * <p>
 * This metadata can be used by applications to interpret the parameters of the surface.
 * For example, the scenario framework uses the data when applying perturbations.
 */
public interface SurfaceMetadata {

  /**
   * Gets the surface name.
   * 
   * @return the surface name
   */
  public abstract SurfaceName getSurfaceName();

  /**
   * Gets the x-value type, providing meaning to the x-values of the surface.
   * <p>
   * This type provides meaning to the x-values. For example, the x-value might
   * represent a year fraction, as represented using {@link ValueType#YEAR_FRACTION}.
   * 
   * @return the x-value type
   */
  public abstract ValueType getXValueType();

  /**
   * Gets the y-value type, providing meaning to the y-values of the surface.
   * <p>
   * This type provides meaning to the y-values.
   * 
   * @return the y-value type
   */
  public abstract ValueType getYValueType();

  /**
   * Gets the z-value type, providing meaning to the z-values of the surface.
   * <p>
   * This type provides meaning to the z-values.
   * 
   * @return the z-value type
   */
  public abstract ValueType getZValueType();

  //-------------------------------------------------------------------------
  /**
   * Gets surface information of a specific type.
   * <p>
   * If the information is not found, an exception is thrown.
   * 
   * @param <T>  the type of the info
   * @param type  the type to find
   * @return the surface information
   * @throws IllegalArgumentException if the information is not found
   */
  public default <T> T getInfo(SurfaceInfoType<T> type) {
    return findInfo(type).orElseThrow(() -> new IllegalArgumentException(
        Messages.format("Surface info not found for type '{}'", type)));
  }

  /**
   * Finds surface information of a specific type.
   * <p>
   * If the info is not found, optional empty is returned.
   * 
   * @param <T>  the type of the info
   * @param type  the type to find
   * @return the surface information
   */
  public abstract <T> Optional<T> findInfo(SurfaceInfoType<T> type);

  /**
   * Gets the metadata of the parameter at the specified index.
   * <p>
   * If there is no specific parameter metadata, an empty instance will be returned.
   * 
   * @param parameterIndex  the zero-based index of the parameter to get
   * @return the metadata of the parameter
   * @throws IndexOutOfBoundsException if the index is invalid
   */
  public default ParameterMetadata getParameterMetadata(int parameterIndex) {
    return getParameterMetadata().map(pm -> pm.get(parameterIndex)).orElse(ParameterMetadata.empty());
  }

  /**
   * Gets metadata about each parameter underlying the surface, optional.
   * <p>
   * If present, the parameter metadata will match the number of parameters on the surface.
   * 
   * @return the parameter metadata
   */
  public abstract Optional<List<ParameterMetadata>> getParameterMetadata();

  //-------------------------------------------------------------------------
  /**
   * Returns an instance where the specified additional information has been added.
   * <p>
   * The additional information is stored in the result using {@code Map.put} semantics,
   * removing the key if the instance is null.
   * 
   * @param <T>  the type of the info
   * @param type  the type to store under
   * @param value  the value to store, may be null
   * @return the new surface metadata
   */
  public abstract <T> DefaultSurfaceMetadata withInfo(SurfaceInfoType<T> type, T value);

  /**
   * Returns an instance where the parameter metadata has been changed.
   * <p>
   * The result will contain the specified parameter metadata.
   * A null value is accepted and causes the result to have no parameter metadata.
   * 
   * @param parameterMetadata  the new parameter metadata, may be null
   * @return the new surface metadata
   */
  public abstract SurfaceMetadata withParameterMetadata(List<? extends ParameterMetadata> parameterMetadata);

}
