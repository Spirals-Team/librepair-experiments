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
import org.gradoop.common.storage.impl.accumulo.iterator.tserver.BaseElementIterator;
import org.gradoop.common.storage.impl.accumulo.iterator.tserver.GradoopEdgeIterator;
import org.gradoop.common.storage.impl.accumulo.iterator.tserver.GradoopGraphHeadIterator;
import org.gradoop.common.storage.impl.accumulo.iterator.tserver.GradoopVertexIterator;
import org.gradoop.common.storage.impl.accumulo.predicate.calculate.AND;
import org.gradoop.common.storage.impl.accumulo.predicate.calculate.NOT;
import org.gradoop.common.storage.impl.accumulo.predicate.calculate.OR;
import org.gradoop.common.storage.predicate.ElementFilter;

import javax.annotation.Nonnull;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.List;
import java.util.function.Predicate;

/**
 * Accumulo Reducer Filter
 *
 * A reducer filter predicate will be
 * - created by client,
 * - serialized as accumulo options, transport as rpc query parameter to tserver
 * - anti-serialized by tserver runtime {@link BaseElementIterator} and execute as element filter
 *
 * @param <T> epgm element type
 * @see GradoopEdgeIterator
 * @see GradoopGraphHeadIterator
 * @see GradoopVertexIterator
 */
public interface AccumuloReducer<T extends EPGMElement> extends
  Predicate<T>, ElementFilter, Serializable {

  /**
   * anti-serialize reducer from base64 encoded string
   * this action will be execute by tserver
   *
   * @param encoded encoded string
   * @param <T> filter element type
   * @return filter instance
   */
  static <T extends EPGMElement> AccumuloReducer<T> decode(String encoded) {
    byte[] content = Base64.getDecoder().decode(encoded);
    try (
      ByteArrayInputStream arr = new ByteArrayInputStream(content);
      ObjectInput in = new ObjectInputStream(arr);
    ) {
      //noinspection unchecked
      return (AccumuloReducer<T>) in.readObject();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * serialize reducer as base64 encoded string
   * this action will be execute by client
   *
   * @return encoded string
   */
  default String encode() {
    try (
      ByteArrayOutputStream arr = new ByteArrayOutputStream();
      ObjectOutputStream out = new ObjectOutputStream(arr)) {
      out.writeObject(this);
      return Base64.getEncoder().encodeToString(arr.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * conjunctive operator
   *
   * @param another another reduce filter
   * @return conjunctive logic filter
   */
  default AccumuloReducer<T> or(AccumuloReducer<T> another) {
    return OR.create(this, another);
  }

  /**
   * disjunctive operator
   *
   * @param another another reduce filter
   * @return conjunctive logic filter
   */
  default AccumuloReducer<T> and(AccumuloReducer<T> another) {
    return AND.create(this, another);
  }

  /**
   * negative operator
   * @return negative logic for current filter
   */
  default AccumuloReducer<T> not() {
    return NOT.of(this);
  }

  /**
   * query elements all
   *
   * @return accumulo predicate within all ranges
   */
  default AccumuloPredicate<T> all() {
    return AccumuloPredicate.create(this);
  }

  /**
   * query elements within a set of ids
   *
   * @param ids id ranges
   * @return accumulo predicate within a certain id ranges
   */
  default AccumuloPredicate<T> within(@Nonnull GradoopIdSet ids) {
    return AccumuloPredicate.create(ids, this);
  }

  /**
   * query element at a single id
   *
   * @param id single id
   * @return accumulo predicate at a single id range
   */
  default AccumuloPredicate<T> single(@Nonnull GradoopId id) {
    return AccumuloPredicate.create(GradoopIdSet.fromExisting(id), this);
  }

  /**
   * query element within a set of accumulo range
   * @param ranges accumulo ranges
   * @return accumulo predicate within a certain accumulo row-id ranges
   */
  default AccumuloPredicate<T> within(@Nonnull List<Range> ranges) {
    return AccumuloPredicate.create(ranges, this);
  }

}
