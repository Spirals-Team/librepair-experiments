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

import java.util.Arrays;

/**
 * assert property value equals
 *
 * @param <T> input type
 */
public class PropEquals<T extends EPGMElement> implements AccumuloReducer<T> {

  /**
   * property key
   */
  private final String key;

  /**
   * property value
   */
  private final PropertyValue value;

  /**
   * property equals filter
   *
   * @param key property key
   * @param value property value
   */
  public PropEquals(
    String key,
    Object value
  ) {
    this.key = key;
    this.value = value instanceof PropertyValue ?
      (PropertyValue) value : PropertyValue.create(value);
  }

  @Override
  public boolean test(T t) {
    return t.getPropertyValue(key) != null &&
      Arrays.equals(t.getPropertyValue(key).getRawBytes(), value.getRawBytes());
  }

}
