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

import org.apache.accumulo.core.data.Range;
import org.gradoop.common.model.api.entities.EPGMElement;
import org.gradoop.common.model.impl.id.GradoopId;
import org.gradoop.common.model.impl.id.GradoopIdSet;
import org.gradoop.common.storage.predicate.ElementFilter;
import org.gradoop.common.utils.KryoUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * accumulo predicate filter definition
 *
 * @param <T> epgm element type
 */
public class AccumuloPredicate<T extends EPGMElement> implements ElementFilter, Serializable {

  /**
   * query ranges in accumulo table, should be serializable
   */
  private final byte[] queryRanges;

  /**
   * reduce filter for epgm element
   */
  private final AccumuloReducer<T> reduceFilter;

  /**
   * accumulo predicate instance, low level api for store implement
   *
   * @param logicalRanges accumulo logical ranges for ele ment table,
   * @param reduceFilter query reduce filter
   *                     only those in predicate should be return from tserver.
   *                     if null, return all in range
   */
  private AccumuloPredicate(
    @Nullable List<Range> logicalRanges,
    @Nullable AccumuloReducer<T> reduceFilter
  ) {
    RangeWrapper wrapper = new RangeWrapper();
    wrapper.ranges = logicalRanges;
    this.queryRanges = wrapper.encrypt();
    this.reduceFilter = reduceFilter;
  }

  /**
   * create a predicate within all range
   * @param reduceFilter reduce filter predicate
   * @param <T> epgm element type
   * @return accumulo predicate
   */
  public static <T extends EPGMElement> AccumuloPredicate<T> create(
    @Nullable AccumuloReducer<T> reduceFilter
  ) {
    return new AccumuloPredicate<>(null, reduceFilter);
  }

  /**
   * create a predicate within a certain id ranges
   *
   * @param idRanges gradoop id set for query element
   * @param reduceFilter reduce filter logic
   * @param <T> epgm element type
   * @return accumulo predicate
   */
  public static <T extends EPGMElement> AccumuloPredicate<T> create(
    @Nonnull GradoopIdSet idRanges,
    @Nullable AccumuloReducer<T> reduceFilter
  ) {
    List<Range> ranges = Range.mergeOverlapping(idRanges.stream()
      .map(GradoopId::toString)
      .map(Range::exact)
      .collect(Collectors.toList()));
    return new AccumuloPredicate<>(ranges, reduceFilter);
  }

  /**
   * create a predicate within a certain accumulo id ranges
   *
   * @param idRanges gradoop row-id ranges for query element
   * @param reduceFilter reducer filter logic
   * @param <T> epgm element type
   * @return accumulo predicate
   */
  public static <T extends EPGMElement> AccumuloPredicate<T> create(
    @Nonnull List<Range> idRanges,
    @Nullable AccumuloReducer<T> reduceFilter
  ) {
    if (idRanges.isEmpty()) {
      throw new IllegalArgumentException("id range is empty");
    }
    return new AccumuloPredicate<>(idRanges, reduceFilter);
  }

  /**
   * get query ranges by anti-encrypt wrapper
   * @return seek range
   */
  public List<Range> getQueryRanges() {
    //noinspection unchecked
    return queryRanges == null ? null : RangeWrapper.decrypt(queryRanges).ranges;
  }

  public AccumuloReducer<T> getReduceFilter() {
    return reduceFilter;
  }

  /**
   * range wrapper definition
   */
  private static class RangeWrapper {

    /**
     * query ranges, may be null
     */
    private List<Range> ranges;

    /**
     * encrypt as byte array
     * @return byte array result
     */
    private byte[] encrypt() {
      try {
        return KryoUtils.dumps(this);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

    /**
     * decrypted from byte array
     *
     * @param data encrypted data
     * @return range wrapper instance
     */
    private static RangeWrapper decrypt(byte[] data) {
      try {
        return KryoUtils.loads(data, RangeWrapper.class);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

}
