/*
 * Copyright 2017 The Error Prone Authors.
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
package com.google.errorprone.bugpatterns.testdata;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import java.util.function.BiConsumer;
import java.util.stream.Collector;

/** @author sulku@google.com (Marsela Sulku) */
public class CollectorShouldNotUseStatePositiveCases {
  public void test() {
    // BUG: Diagnostic contains: Collector.of() should not use state
    Collector.of(
        ImmutableList::builder,
        new BiConsumer<ImmutableList.Builder<Object>, Object>() {

          boolean isFirst = true;
          private static final String bob = "bob";

          @Override
          public void accept(Builder<Object> objectBuilder, Object o) {
            if (isFirst) {
              System.out.println("it's first");
            } else {
              objectBuilder.add(o);
            }
          }
        },
        (left, right) -> left.addAll(right.build()),
        ImmutableList.Builder::build);

    // BUG: Diagnostic contains: Collector.of() should not use state
    Collector.of(
        ImmutableList::builder,
        new BiConsumer<ImmutableList.Builder<Object>, Object>() {

          boolean isFirst = true;
          private final String bob = "bob";
          private final String joe = "joe";

          @Override
          public void accept(Builder<Object> objectBuilder, Object o) {
            if (isFirst) {
              System.out.println("it's first");
            } else {
              objectBuilder.add(o);
            }
          }
        },
        (left, right) -> left.addAll(right.build()),
        ImmutableList.Builder::build);
  }
}
