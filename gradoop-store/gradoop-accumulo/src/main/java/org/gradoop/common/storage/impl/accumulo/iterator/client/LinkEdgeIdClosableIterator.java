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

import io.netty.util.internal.ConcurrentSet;
import org.apache.accumulo.core.client.BatchScanner;
import org.apache.accumulo.core.data.Key;
import org.apache.accumulo.core.data.Value;
import org.gradoop.common.model.impl.id.GradoopId;
import org.gradoop.common.storage.iterator.ClosableIterator;
import org.gradoop.common.storage.impl.accumulo.constants.AccumuloTables;
import org.gradoop.common.storage.impl.accumulo.row.LinkEdgeId;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * link table result iterator
 */
public class LinkEdgeIdClosableIterator implements ClosableIterator<LinkEdgeId> {

  /**
   * accumulo batch scanner instance
   */
  private final BatchScanner scanner;

  /**
   * scanner iterator
   */
  private final Iterator<Map.Entry<Key, Value>> inner;

  /**
   * max record cache size
   */
  private final int cacheSize;

  /**
   * edges should not be read twice
   */
  private final Set<String> consumeIds = new ConcurrentSet<>();

  /**
   * element cache size
   */
  private List<LinkEdgeId> cache = new ArrayList<>();

  /**
   * link table closeable iterator constructor
   *
   * @param scanner client batch scanner
   * @param cacheSize client result cache size
   */
  public LinkEdgeIdClosableIterator(
    BatchScanner scanner,
    int cacheSize
  ) {
    this.scanner = scanner;
    this.cacheSize = cacheSize;
    this.inner = scanner.iterator();
  }

  @Override
  public void close() {
    scanner.close();
  }

  @Override
  public boolean hasNext() {
    if (!cache.isEmpty()) {
      //cache is not empty
      return true;

    } else if (inner.hasNext()) {
      //cache is empty, read elements to cache
      while (inner.hasNext() && cache.size() < cacheSize) {
        Map.Entry<Key, Value> nextEntry = inner.next();
        boolean isEdgeIn = Objects.equals(
          nextEntry.getKey().getColumnFamily().toString(),
          AccumuloTables.KEY.EDGE_IN);
        GradoopId v0 = GradoopId.fromString(nextEntry.getKey().getRow().toString());
        GradoopId v1 = GradoopId.fromString(nextEntry.getValue().toString());
        GradoopId edgeId = GradoopId.fromString(nextEntry.getKey().getColumnQualifier().toString());

        //check if this edge should be consume
        if (consumeIds.add(edgeId.toString())) {
          LinkEdgeId row = new LinkEdgeId();
          row.setSourceId(isEdgeIn ? v1 : v0);
          row.setTargetId(isEdgeIn ? v0 : v1);
          row.setId(edgeId);
          cache.add(row);
        }
      }
      return hasNext();

    } else {
      //cache is empty and iterator has no element any more
      return false;

    }
  }

  @Override
  public LinkEdgeId next() {
    return cache.remove(0);
  }

}
