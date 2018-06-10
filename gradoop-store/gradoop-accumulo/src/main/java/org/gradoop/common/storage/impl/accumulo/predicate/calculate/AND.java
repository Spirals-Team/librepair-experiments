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

package org.gradoop.common.storage.impl.accumulo.predicate.calculate;

import org.gradoop.common.model.api.entities.EPGMElement;
import org.gradoop.common.storage.impl.accumulo.predicate.AccumuloReducer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * disjunctive predicate filter
 *
 * @param <T> element type
 */
public final class AND<T extends EPGMElement> implements AccumuloReducer<T> {

  /**
   * predicate list
   */
  private final List<AccumuloReducer<T>> predicates = new ArrayList<>();

  /**
   * disjunctive principles
   *
   * @param predicates predicates
   */
  private AND(List<AccumuloReducer<T>> predicates) {
    if (predicates.size() < 2) {
      throw new IllegalArgumentException(String.format("predicates len(=%d) < 2",
        predicates.size()));
    }
    this.predicates.addAll(predicates);
  }

  /**
   * create a Conjunctive formula
   *
   * @param predicates filter predicate
   * @param <T> input type
   * @return Conjunctive filter instance
   */
  @SafeVarargs
  public static <T extends EPGMElement> AND<T> create(AccumuloReducer<T>... predicates) {
    List<AccumuloReducer<T>> formula = new ArrayList<>();
    Collections.addAll(formula, predicates);
    return new AND<>(formula);
  }

  @Override
  public boolean test(T t) {
    for (AccumuloReducer<T> predicate : predicates) {
      if (!predicate.test(t)) {
        return false;
      }
    }
    return true;
  }

}
