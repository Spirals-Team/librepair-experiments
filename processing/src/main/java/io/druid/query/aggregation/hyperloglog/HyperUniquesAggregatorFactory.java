/*
 * Licensed to Metamarkets Group Inc. (Metamarkets) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Metamarkets licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.query.aggregation.hyperloglog;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.druid.hll.HyperLogLogCollector;
import io.druid.java.util.common.IAE;
import io.druid.java.util.common.StringUtils;
import io.druid.java.util.common.guava.Comparators;
import io.druid.query.aggregation.AggregateCombiner;
import io.druid.query.aggregation.Aggregator;
import io.druid.query.aggregation.AggregatorFactory;
import io.druid.query.aggregation.AggregatorFactoryNotMergeableException;
import io.druid.query.aggregation.AggregatorUtil;
import io.druid.query.aggregation.BufferAggregator;
import io.druid.query.aggregation.NoopAggregator;
import io.druid.query.aggregation.NoopBufferAggregator;
import io.druid.query.aggregation.cardinality.HyperLogLogCollectorAggregateCombiner;
import io.druid.query.cache.CacheKeyBuilder;
import io.druid.segment.ColumnSelectorFactory;
import io.druid.segment.ObjectColumnSelector;
import org.apache.commons.codec.binary.Base64;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 */
public class HyperUniquesAggregatorFactory extends AggregatorFactory
{
  public static Object estimateCardinality(Object object, boolean round)
  {
    if (object == null) {
      return 0;
    }

    final HyperLogLogCollector collector = (HyperLogLogCollector) object;

    // Avoid ternary, it causes estimateCardinalityRound to be cast to double.
    if (round) {
      return collector.estimateCardinalityRound();
    } else {
      return collector.estimateCardinality();
    }
  }

  private final String name;
  private final String fieldName;
  private final boolean isInputHyperUnique;
  private final boolean round;

  @JsonCreator
  public HyperUniquesAggregatorFactory(
      @JsonProperty("name") String name,
      @JsonProperty("fieldName") String fieldName,
      @JsonProperty("isInputHyperUnique") boolean isInputHyperUnique,
      @JsonProperty("round") boolean round
  )
  {
    this.name = name;
    this.fieldName = fieldName;
    this.isInputHyperUnique = isInputHyperUnique;
    this.round = round;
  }

  public HyperUniquesAggregatorFactory(
      String name,
      String fieldName
  )
  {
    this(name, fieldName, false, false);
  }

  @Override
  public Aggregator factorize(ColumnSelectorFactory metricFactory)
  {
    ObjectColumnSelector selector = metricFactory.makeObjectColumnSelector(fieldName);

    if (selector == null) {
      return NoopAggregator.instance();
    }

    final Class classOfObject = selector.classOfObject();
    if (classOfObject.equals(Object.class) || HyperLogLogCollector.class.isAssignableFrom(classOfObject)) {
      return new HyperUniquesAggregator(selector);
    }

    throw new IAE(
        "Incompatible type for metric[%s], expected a HyperUnique, got a %s", fieldName, classOfObject
    );
  }

  @Override
  public BufferAggregator factorizeBuffered(ColumnSelectorFactory metricFactory)
  {
    ObjectColumnSelector selector = metricFactory.makeObjectColumnSelector(fieldName);

    if (selector == null) {
      return NoopBufferAggregator.instance();
    }

    final Class classOfObject = selector.classOfObject();
    if (classOfObject.equals(Object.class) || HyperLogLogCollector.class.isAssignableFrom(classOfObject)) {
      return new HyperUniquesBufferAggregator(selector);
    }

    throw new IAE(
        "Incompatible type for metric[%s], expected a HyperUnique, got a %s", fieldName, classOfObject
    );
  }

  @Override
  public Comparator getComparator()
  {
    return Comparators.naturalNullsFirst();
  }

  @Override
  public Object combine(Object lhs, Object rhs)
  {
    if (rhs == null) {
      return lhs;
    }
    if (lhs == null) {
      return rhs;
    }
    return ((HyperLogLogCollector) lhs).fold((HyperLogLogCollector) rhs);
  }

  @Override
  public AggregateCombiner makeAggregateCombiner()
  {
    return new HyperLogLogCollectorAggregateCombiner();
  }

  @Override
  public AggregatorFactory getCombiningFactory()
  {
    return new HyperUniquesAggregatorFactory(name, name, false, round);
  }

  @Override
  public AggregatorFactory getMergingFactory(AggregatorFactory other) throws AggregatorFactoryNotMergeableException
  {
    if (other.getName().equals(this.getName()) && this.getClass() == other.getClass()) {
      return getCombiningFactory();
    } else {
      throw new AggregatorFactoryNotMergeableException(this, other);
    }
  }

  @Override
  public List<AggregatorFactory> getRequiredColumns()
  {
    return Arrays.<AggregatorFactory>asList(new HyperUniquesAggregatorFactory(
        fieldName,
        fieldName,
        isInputHyperUnique,
        round
    ));
  }

  @Override
  public Object deserialize(Object object)
  {
    final ByteBuffer buffer;

    if (object instanceof byte[]) {
      buffer = ByteBuffer.wrap((byte[]) object);
    } else if (object instanceof ByteBuffer) {
      // Be conservative, don't assume we own this buffer.
      buffer = ((ByteBuffer) object).duplicate();
    } else if (object instanceof String) {
      buffer = ByteBuffer.wrap(Base64.decodeBase64(StringUtils.toUtf8((String) object)));
    } else {
      return object;
    }

    return HyperLogLogCollector.makeCollector(buffer);
  }

  @Override
  public Object finalizeComputation(Object object)
  {
    return estimateCardinality(object, round);
  }

  @Override
  @JsonProperty
  public String getName()
  {
    return name;
  }

  @Override
  public List<String> requiredFields()
  {
    return Collections.singletonList(fieldName);
  }

  @JsonProperty
  public String getFieldName()
  {
    return fieldName;
  }

  @JsonProperty
  public boolean getIsInputHyperUnique()
  {
    return isInputHyperUnique;
  }

  @JsonProperty
  public boolean isRound()
  {
    return round;
  }

  @Override
  public byte[] getCacheKey()
  {
    return new CacheKeyBuilder(AggregatorUtil.HYPER_UNIQUE_CACHE_TYPE_ID)
        .appendString(fieldName)
        .appendBoolean(round)
        .build();
  }

  @Override
  public String getTypeName()
  {
    if (isInputHyperUnique) {
      return "preComputedHyperUnique";
    } else {
      return "hyperUnique";
    }
  }

  @Override
  public int getMaxIntermediateSize()
  {
    return HyperLogLogCollector.getLatestNumBytesForDenseStorage();
  }

  @Override
  public String toString()
  {
    return "HyperUniquesAggregatorFactory{" +
           "name='" + name + '\'' +
           ", fieldName='" + fieldName + '\'' +
           ", isInputHyperUnique=" + isInputHyperUnique +
           ", round=" + round +
           '}';
  }

  @Override
  public boolean equals(final Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final HyperUniquesAggregatorFactory that = (HyperUniquesAggregatorFactory) o;
    return isInputHyperUnique == that.isInputHyperUnique &&
           round == that.round &&
           Objects.equals(name, that.name) &&
           Objects.equals(fieldName, that.fieldName);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(name, fieldName, isInputHyperUnique, round);
  }
}
