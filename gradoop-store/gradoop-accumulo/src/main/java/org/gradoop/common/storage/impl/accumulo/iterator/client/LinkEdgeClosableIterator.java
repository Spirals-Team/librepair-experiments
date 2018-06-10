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

package org.gradoop.common.storage.impl.accumulo.iterator.client;

import org.gradoop.common.model.api.entities.EPGMEdge;
import org.gradoop.common.model.impl.id.GradoopId;
import org.gradoop.common.model.impl.id.GradoopIdSet;
import org.gradoop.common.model.impl.pojo.Edge;
import org.gradoop.common.model.impl.pojo.Element;
import org.gradoop.common.storage.impl.accumulo.predicate.AccumuloPredicate;
import org.gradoop.common.storage.impl.accumulo.predicate.AccumuloRelativePredicate;
import org.gradoop.common.storage.iterator.ClosableIterator;
import org.gradoop.common.storage.impl.accumulo.AccumuloEPGMStore;
import org.gradoop.common.storage.impl.accumulo.row.LinkEdgeId;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * link edge closable iterator
 *
 * @param <E> edge type
 */
public class LinkEdgeClosableIterator<E extends EPGMEdge> implements ClosableIterator<E> {

  /**
   * inner link table iterator
   */
  private final ClosableIterator<LinkEdgeId> inner;

  /**
   * accumulo table api
   */
  private final AccumuloEPGMStore<?, ?, E> store;

  /**
   * edge filter
   */
  private final AccumuloRelativePredicate<Edge> filter;

  /**
   * client cache size
   */
  private final int cacheSize;

  /**
   * cached edges
   */
  private final List<E> cache = new ArrayList<>();

  /**
   * link edge closable iterator constructor
   *  @param inner inner link table iterator
   * @param store accumulo table api
   * @param filter edge filter
   * @param cacheSize client cache size
   */
  public LinkEdgeClosableIterator(
    @Nonnull ClosableIterator<LinkEdgeId> inner,
    @Nonnull AccumuloEPGMStore<?, ?, E> store,
    @Nullable AccumuloRelativePredicate<Edge> filter,
    int cacheSize
  ) {
    this.inner = inner;
    this.store = store;
    this.filter = filter;
    this.cacheSize = cacheSize;
  }

  @Override
  public void close() throws IOException {
    inner.close();
  }

  @Override
  public boolean hasNext() {
    if (!cache.isEmpty()) {
      //cache is not empty
      return true;

    } else if (inner.hasNext()) {
      List<GradoopId> nextIdSet = inner.read(cacheSize)
        .stream()
        .map(Element::getId)
        .collect(Collectors.toList());
      GradoopIdSet nextSet = GradoopIdSet.fromExisting(nextIdSet);
      AccumuloPredicate<Edge> nextFilter = AccumuloPredicate.create(
        nextSet,
        filter == null ? null : filter.getEdgeFilter());
      try {
        cache.addAll(store
          .getEdgeSpace(nextFilter, cacheSize)
          .readRemainsAndClose());
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }
      return hasNext();

    } else {
      //cache is empty and iterator has no element any more
      return false;

    }
  }

  @Override
  public E next() {
    return cache.remove(0);
  }

}
