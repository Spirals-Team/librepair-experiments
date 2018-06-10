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

package org.gradoop.flink.io.impl.accumulo;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.gradoop.common.model.impl.pojo.Edge;
import org.gradoop.common.model.impl.pojo.GraphHead;
import org.gradoop.common.model.impl.pojo.Vertex;
import org.gradoop.common.storage.impl.accumulo.AccumuloEPGMStore;
import org.gradoop.common.storage.impl.accumulo.predicate.AccumuloPredicate;
import org.gradoop.flink.io.api.FilterableDataSource;
import org.gradoop.flink.io.impl.accumulo.inputformats.EdgeInputFormat;
import org.gradoop.flink.io.impl.accumulo.inputformats.GraphHeadInputFormat;
import org.gradoop.flink.io.impl.accumulo.inputformats.VertexInputFormat;
import org.gradoop.flink.model.api.epgm.GraphCollection;
import org.gradoop.flink.model.api.epgm.GraphCollectionFactory;
import org.gradoop.flink.model.api.epgm.LogicalGraph;
import org.gradoop.flink.model.impl.operators.combination.ReduceCombination;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Accumulo DataSource
 */
public class AccumuloDataSource extends AccumuloBase<GraphHead, Vertex, Edge>
  implements FilterableDataSource<
  AccumuloPredicate<GraphHead>,
  AccumuloPredicate<Vertex>,
  AccumuloPredicate<Edge>> {

  /**
   * graph head filter
   */
  private final AccumuloPredicate<GraphHead> graphHeadFilter;

  /**
   * vertex filter
   */
  private final AccumuloPredicate<Vertex> vertexFilter;

  /**
   * edge filter
   */
  private final AccumuloPredicate<Edge> edgeFilter;

  /**
   * Creates a new Accumulo data source.
   *
   * @param store accumulo epgm store
   */
  public AccumuloDataSource(
    AccumuloEPGMStore<GraphHead, Vertex, Edge> store
  ) {
    this(store, null, null, null);
  }

  /**
   * Creates a new Accumulo data source.
   *
   * @param store accumulo epgm store
   * @param graphHeadFilter graph head filter
   * @param vertexFilter vertex filter
   * @param edgeFilter edge filter
   */
  public AccumuloDataSource(
    @Nonnull AccumuloEPGMStore<GraphHead, Vertex, Edge> store,
    @Nullable AccumuloPredicate<GraphHead> graphHeadFilter,
    @Nullable AccumuloPredicate<Vertex> vertexFilter,
    @Nullable AccumuloPredicate<Edge> edgeFilter
  ) {
    super(store, store.getConfig());
    this.graphHeadFilter = graphHeadFilter;
    this.vertexFilter = vertexFilter;
    this.edgeFilter = edgeFilter;
  }

  @Override
  public LogicalGraph getLogicalGraph() {
    return getGraphCollection().reduce(new ReduceCombination());
  }

  @Override
  public GraphCollection getGraphCollection() {
    GraphCollectionFactory factory = getAccumuloConfig().getGraphCollectionFactory();
    ExecutionEnvironment env = getAccumuloConfig().getExecutionEnvironment();
    return factory.fromDataSets(
      /*graph head format*/
      env.createInput(new GraphHeadInputFormat(
        getStore().getConfig().getAccumuloProperties(),
        graphHeadFilter)),
      /*vertex input format*/
      env.createInput(new VertexInputFormat(getStore().getConfig().getAccumuloProperties(),
        vertexFilter)),
      /*edge input format*/
      env.createInput(new EdgeInputFormat(getStore().getConfig().getAccumuloProperties(),
        edgeFilter)));
  }

  @Nonnull
  @Override
  public FilterableDataSource applyGraphPredicate(
    @Nullable AccumuloPredicate<GraphHead> graphHeadFilter
  ) {
    return new AccumuloDataSource(
      getStore(),
      graphHeadFilter,
      vertexFilter,
      edgeFilter
    );
  }

  @Nonnull
  @Override
  public FilterableDataSource applyVertexPredicate(
    @Nullable AccumuloPredicate<Vertex> vertexFilter
  ) {
    return new AccumuloDataSource(
      getStore(),
      graphHeadFilter,
      vertexFilter,
      edgeFilter
    );
  }

  @Nonnull
  @Override
  public FilterableDataSource applyEdgePredicate(
    @Nullable AccumuloPredicate<Edge> edgeFilter
  ) {
    return new AccumuloDataSource(
      getStore(),
      graphHeadFilter,
      vertexFilter,
      edgeFilter
    );
  }

  @Override
  public boolean isFilterPushedDown() {
    return this.graphHeadFilter != null ||
      this.vertexFilter != null ||
      this.edgeFilter != null;
  }

}
