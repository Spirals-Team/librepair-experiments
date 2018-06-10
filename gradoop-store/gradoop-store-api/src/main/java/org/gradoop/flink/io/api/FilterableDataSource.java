/**
 * Copyright © 2014 - 2018 Leipzig University (Database Research Group)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradoop.flink.io.api;

import org.gradoop.common.storage.predicate.ElementFilter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Data source in with support for filter push-down. A Source
 * extending this interface is able to filter records such
 * that the returned DataSet returns fewer records.
 *
 * @param <GF> graph element filter
 * @param <VF> vertex element filter
 * @param <EF> edge element filter
 */
public interface FilterableDataSource<
  GF extends ElementFilter,
  VF extends ElementFilter,
  EF extends ElementFilter> extends DataSource {

  /**
   * Returns a copy of the DataSource with added predicates.
   * The predicates parameter is a mutable list of conjunctive
   * predicates that are “offered” to the DataSource.
   *
   * @param graphHeadFilter graph head filter, get all if null
   * @return a copy of the FilterableDataSource with added predicates
   */
  @Nonnull
  FilterableDataSource applyGraphPredicate(@Nullable GF graphHeadFilter);

  /**
   * Returns a copy of the DataSource with added predicates.
   * The predicates parameter is a mutable list of conjunctive
   * predicates that are “offered” to the DataSource.
   *
   * @param vertexFilter vertex filter, get all if null
   * @return a copy of the FilterableDataSource with added predicates
   */
  @Nonnull
  FilterableDataSource applyVertexPredicate(@Nullable VF vertexFilter);

  /**
   * Returns a copy of the DataSource with added predicates.
   * The predicates parameter is a mutable list of conjunctive
   * predicates that are “offered” to the DataSource.
   *
   * @param edgeFilter edge filter, get all if null
   * @return a copy of the FilterableDataSource with added predicates
   */
  @Nonnull
  FilterableDataSource applyEdgePredicate(@Nullable EF edgeFilter);

  /**
   * Returns true if the applyPredicate() method was called before.
   * Hence, isFilterPushedDown() must return true for all
   * TableSource instances returned from a applyPredicate() call.
   *
   * @return true if the applyPredicate() method was called before
   */
  boolean isFilterPushedDown();

}
