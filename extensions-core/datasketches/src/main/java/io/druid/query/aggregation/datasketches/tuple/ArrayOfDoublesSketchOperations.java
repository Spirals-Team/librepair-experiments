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

package io.druid.query.aggregation.datasketches.tuple;

import com.google.common.base.Charsets;
import com.yahoo.memory.Memory;

import org.apache.commons.codec.binary.Base64;

import com.yahoo.sketches.tuple.ArrayOfDoublesAnotB;
import com.yahoo.sketches.tuple.ArrayOfDoublesCombiner;
import com.yahoo.sketches.tuple.ArrayOfDoublesIntersection;
import com.yahoo.sketches.tuple.ArrayOfDoublesSketch;
import com.yahoo.sketches.tuple.ArrayOfDoublesSketches;
import com.yahoo.sketches.tuple.ArrayOfDoublesUnion;
import com.yahoo.sketches.tuple.ArrayOfDoublesSetOperationBuilder;

public class ArrayOfDoublesSketchOperations
{

  public enum Func
  {
    UNION, INTERSECT, NOT
  }

  // This is how to combine values for sketch intersection.
  // Might not fit all use cases.
  private static ArrayOfDoublesCombiner COMBINER = new ArrayOfDoublesCombiner()
  {
    @Override
    public double[] combine(final double[] a, final double[] b)
    {
      final double[] result = new double[a.length];
      for (int i = 0; i < a.length; i++) {
        result[i] = a[i] + b[i];
      }
      return result;
    }
  };

  public static ArrayOfDoublesSketch deserialize(final Object serializedSketch)
  {
    if (serializedSketch instanceof String) {
      return deserializeFromBase64EncodedString((String) serializedSketch);
    } else if (serializedSketch instanceof byte[]) {
      return deserializeFromByteArray((byte[]) serializedSketch);
    } else if (serializedSketch instanceof ArrayOfDoublesSketch) {
      return (ArrayOfDoublesSketch) serializedSketch;
    }
    throw new IllegalStateException(
      "Object is not of a type that can deserialize to sketch: " + serializedSketch.getClass()
    );
  }

  public static ArrayOfDoublesSketch deserializeFromBase64EncodedString(final String str)
  {
    return deserializeFromByteArray(Base64.decodeBase64(str.getBytes(Charsets.UTF_8)));
  }

  public static ArrayOfDoublesSketch deserializeFromByteArray(final byte[] data)
  {
    final Memory mem = Memory.wrap(data);
    return ArrayOfDoublesSketches.wrapSketch(mem);
  }

  public static ArrayOfDoublesSketch sketchSetOperation(
      final Func func,
      final int k,
      final int numValues,
      final ArrayOfDoublesSketch... sketches
  )
  {
    switch (func) {
      case UNION:
        final ArrayOfDoublesUnion union = new ArrayOfDoublesSetOperationBuilder().setNominalEntries(k)
            .setNumberOfValues(numValues).buildUnion();
        for (final ArrayOfDoublesSketch sketch : sketches) {
          union.update(sketch);
        }
        return union.getResult();
      case INTERSECT:
        final ArrayOfDoublesIntersection intersection = new ArrayOfDoublesSetOperationBuilder()
            .setNominalEntries(k).setNumberOfValues(numValues).buildIntersection();
        for (final ArrayOfDoublesSketch sketch : sketches) {
          intersection.update(sketch, COMBINER);
        }
        return intersection.getResult();
      case NOT:
        if (sketches.length < 1) {
          throw new IllegalArgumentException("A-Not-B requires at least 1 sketch");
        }

        if (sketches.length == 1) {
          return sketches[0];
        }

        ArrayOfDoublesSketch result = sketches[0];
        for (int i = 1; i < sketches.length; i++) {
          final ArrayOfDoublesAnotB aNotB = new ArrayOfDoublesSetOperationBuilder().setNumberOfValues(numValues)
              .buildAnotB();
          aNotB.update(result, sketches[i]);
          result = aNotB.getResult();
        }
        return result;
      default:
        throw new IllegalArgumentException("Unknown sketch operation " + func);
    }
  }

}
