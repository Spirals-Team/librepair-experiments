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
import org.gradoop.common.model.impl.properties.PropertyValue;
import org.gradoop.common.storage.impl.accumulo.predicate.AccumuloReducer;


/**
 * property larger than min filter
 *
 * @param <T> input type
 */
public class PropLargerThan<T extends EPGMElement> implements AccumuloReducer<T> {

  /**
   * property key
   */
  private final String key;

  /**
   * property value
   */
  private final double min;

  /**
   * include min flag
   */
  private final boolean include;

  /**
   * property larger than constructor
   *
   * @param key property key
   * @param min property min value
   */
  public PropLargerThan(
    String key,
    double min
  ) {
    this(key, min, true);
  }

  /**
   * property larger than constructor
   *
   * @param key property key
   * @param min property min value
   * @param include include min value
   */
  public PropLargerThan(
    String key,
    double min,
    boolean include
  ) {
    this.key = key;
    this.min = min;
    this.include = include;
  }

  @Override
  public boolean test(T t) {
    PropertyValue value = t.getPropertyValue(key);
    if (value == null) {
      return false;
    }

    double result;
    if (value.isFloat()) {
      result = value.getFloat();
    } else if (value.isDouble()) {
      result = value.getDouble();
    } else if (value.isLong()) {
      result = value.getLong();
    } else if (value.isInt()) {
      result = value.getInt();
    } else {
      return false;
    }

    return include ? result >= min : result > min;
  }
}
