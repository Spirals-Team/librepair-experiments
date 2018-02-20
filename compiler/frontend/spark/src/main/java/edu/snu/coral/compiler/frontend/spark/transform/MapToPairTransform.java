/*
 * Copyright (C) 2017 Seoul National University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.snu.coral.compiler.frontend.spark.transform;

import edu.snu.coral.common.ir.OutputCollector;
import edu.snu.coral.common.ir.vertex.transform.Transform;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Iterator;

/**
 * Map elements to Pair elements.
 * @param <T> input type.
 * @param <K> output key type.
 * @param <V> output value type.
 */
public final class MapToPairTransform<T, K, V> implements Transform<T, Tuple2<K, V>> {
  private final PairFunction<T, K, V> func;
  private OutputCollector<Tuple2<K, V>> oc;

  /**
   * Constructor.
   * @param func Pair function to apply to each element.
   */
  public MapToPairTransform(final PairFunction<T, K, V> func) {
    this.func = func;
  }

  @Override
  public void prepare(final Context context, final OutputCollector<Tuple2<K, V>> outputCollector) {
    this.oc = outputCollector;
  }

  @Override
  public void onData(final Iterator<T> elements, final String srcVertexId) {
    elements.forEachRemaining(element -> {
      try {
        oc.emit(func.call(element));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }

  @Override
  public void close() {
  }
}
