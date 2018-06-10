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

package org.gradoop.common.storage.api;

import org.gradoop.common.model.api.entities.EPGMEdge;
import org.gradoop.common.model.api.entities.EPGMGraphHead;
import org.gradoop.common.model.api.entities.EPGMVertex;
import org.gradoop.common.storage.iterator.ClosableIterator;
import org.gradoop.common.storage.predicate.ElementFilter;
import org.gradoop.common.storage.predicate.RelativeFilter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;

/**
 * create graph output iterator with external predicate
 *
 * @param <OG> output epgm graph head type
 * @param <OV> output epgm vertex type
 * @param <OE> output epgm edge type
 * @param <FG> graph factory
 * @param <FV> vertex factory
 * @param <FE> edge factory
 * @param <LF> relative edge factory
 */
public interface EPGMGraphPredictableOutput<
  OG extends EPGMGraphHead,
  OV extends EPGMVertex,
  OE extends EPGMEdge,
  FG extends ElementFilter,
  FV extends ElementFilter,
  FE extends ElementFilter,
  LF extends RelativeFilter> extends EPGMGraphOutput<OG, OV, OE> {

  @Nonnull
  @Override
  default ClosableIterator<OG> getGraphSpace(int cacheSize) throws IOException {
    return getGraphSpace(null, DEFAULT_CACHE_SIZE);
  }

  @Nonnull
  @Override
  default ClosableIterator<OV> getVertexSpace(int cacheSize) throws IOException {
    return getVertexSpace(null, DEFAULT_CACHE_SIZE);
  }

  @Nonnull
  @Override
  default ClosableIterator<OE> getEdgeSpace(int cacheSize) throws IOException {
    return getEdgeSpace(null, DEFAULT_CACHE_SIZE);
  }

  /**
   * get graphs by filter predicate
   *
   * @param filter filter predicate
   * @return edges
   */
  @Nonnull
  default ClosableIterator<OG> getGraphSpace(@Nullable FG filter) throws IOException {
    return getGraphSpace(filter, DEFAULT_CACHE_SIZE);
  }

  /**
   * get graphs by filter predicate
   *
   * @param filter filter predicate
   * @param cacheSize result cache size
   * @return edges
   */
  @Nonnull
  ClosableIterator<OG> getGraphSpace(
    @Nullable FG filter,
    int cacheSize
  ) throws IOException;

  /**
   * get vertices by filter predicate
   *
   * @param filter filter predicate
   * @return vertices
   */
  @Nonnull
  default ClosableIterator<OV> getVertexSpace(@Nullable FV filter) throws IOException {
    return getVertexSpace(filter, DEFAULT_CACHE_SIZE);
  }

  /**
   * get vertices by filter predicate
   *
   * @param filter filter predicate
   * @param cacheSize result cache size
   * @return vertices
   */
  @Nonnull
  ClosableIterator<OV> getVertexSpace(
    @Nullable FV filter,
    int cacheSize
  ) throws IOException;

  /**
   * get edges by filter predicate
   *
   * @param filter filter predicate
   * @return edges
   */
  @Nonnull
  default ClosableIterator<OE> getEdgeSpace(@Nullable FE filter) throws IOException {
    return getEdgeSpace(filter, DEFAULT_CACHE_SIZE);
  }

  /**
   * get edges by filter predicate
   *
   * @param filter filter predicate
   * @param cacheSize result cache size
   * @return edges
   */
  @Nonnull
  ClosableIterator<OE> getEdgeSpace(
    @Nullable FE filter,
    int cacheSize
  ) throws IOException;

  /**
   * get relative edges definition by vertex
   *
   * @param filterPredicate relative filter predicate
   * @return edge definition set
   */
  @Nonnull
  default ClosableIterator<OE> getRelativeEdgeSpace(
    @Nonnull LF filterPredicate
  ) throws IOException {
    return getRelativeEdgeSpace(filterPredicate, DEFAULT_CACHE_SIZE);
  }

  /**
   * get relative edges definition by vertex
   *
   * @param filterPredicate relative filter predicate
   * @param cacheSize result cache size
   * @return edge definition set
   */
  @Nonnull
  ClosableIterator<OE> getRelativeEdgeSpace(
    @Nonnull LF filterPredicate,
    int cacheSize
  ) throws IOException;

}
