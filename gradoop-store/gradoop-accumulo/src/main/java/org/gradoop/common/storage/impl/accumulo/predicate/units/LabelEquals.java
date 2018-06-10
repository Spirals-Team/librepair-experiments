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

package org.gradoop.common.storage.impl.accumulo.predicate.units;


import org.gradoop.common.model.api.entities.EPGMElement;
import org.gradoop.common.storage.impl.accumulo.predicate.AccumuloReducer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * label filter predicate
 *
 * @param <T>
 */
public class LabelEquals<T extends EPGMElement> implements AccumuloReducer<T> {

  /**
   * labels range
   */
  private final Set<String> labels = new HashSet<>();

  /**
   * label filter constructor
   * @param labels label
   */
  public LabelEquals(String... labels) {
    Collections.addAll(this.labels, labels);
  }

  @Override
  public boolean test(T t) {
    return t.getLabel() != null &&
      labels.contains(t.getLabel());
  }

}
