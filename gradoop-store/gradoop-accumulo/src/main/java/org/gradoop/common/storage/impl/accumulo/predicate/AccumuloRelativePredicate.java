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

package org.gradoop.common.storage.impl.accumulo.predicate;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import org.gradoop.common.model.api.entities.EPGMEdge;
import org.gradoop.common.model.impl.id.GradoopIdSet;
import org.gradoop.common.storage.predicate.RelativeFilter;

import javax.annotation.Nonnull;

/**
 * accumulo relative predicate filter
 *
 * @param <E> epgm element type
 */
public class AccumuloRelativePredicate<E extends EPGMEdge> implements RelativeFilter {

  /**
   * query vertex id ranges
   */
  private final GradoopIdSet idRanges;

  /**
   * query edge in
   */
  private final boolean enableEdgeIn;

  /**
   * query edge out
   */
  private final boolean enableEdgeOut;

  /**
   * query edge filter predicate
   */
  private final AccumuloReducer<E> edgeFilter;

  /**
   * accumulo relative predicate constructor
   *
   * @param idRanges query vertex id ranges
   * @param enableEdgeIn query edge in
   * @param enableEdgeOut query edge out
   * @param edgeFilter edge query filter
   */
  public AccumuloRelativePredicate(
    @Nonnull GradoopIdSet idRanges,
    boolean enableEdgeIn,
    boolean enableEdgeOut,
    @Nullable AccumuloReducer<E> edgeFilter
  ) {
    this.idRanges = idRanges;
    this.enableEdgeIn = enableEdgeIn;
    this.enableEdgeOut = enableEdgeOut;
    this.edgeFilter = edgeFilter;
  }

  @NonNull
  @Override
  public GradoopIdSet getSourceIDSet() {
    return idRanges;
  }

  @Override
  public boolean enableEdgeIn() {
    return enableEdgeIn;
  }

  @Override
  public boolean enableEdgeOut() {
    return enableEdgeOut;
  }

  @Nullable
  @Override
  public AccumuloReducer<E> getEdgeFilter() {
    return edgeFilter;
  }

}
