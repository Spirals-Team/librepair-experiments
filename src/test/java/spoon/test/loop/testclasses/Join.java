/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2015 the original author or authors.
 */
package spoon.test.loop.testclasses;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Collections.unmodifiableCollection;


/**
 * Join of two or more Condition.
 * @param <T> the type of object this condition accepts.
 *
 * @author Yvonne Wang
 * @author Mikhail Mazursky
 */
public abstract class Join<T> extends Condition<T> {

  final Collection<Condition<? super T>> conditions;

  /**
   * Creates a new Join.
   * @param conditions the conditions to join.
   * @throws NullPointerException if the given array is {@code null}.
   * @throws NullPointerException if any of the elements in the given array is {@code null}.
   */
  @SafeVarargs
  protected Join(Condition<? super T>... conditions) {
    if (conditions == null) throw conditionsIsNull();
    this.conditions = new ArrayList<>();
    for (Condition<? super T> condition : conditions)
      this.conditions.add(notNull(condition));
  }

  private static NullPointerException conditionsIsNull() {
    return new NullPointerException("The given conditions should not be null");
  }

  private static <T> Condition<T> notNull(Condition<T> condition) {
    return null;
  }

  /**
   * Returns the conditions to join.
   * @return the conditions to join.
   */
  protected final Collection<Condition<? super T>> conditions() {
    return unmodifiableCollection(conditions);
  }
}
