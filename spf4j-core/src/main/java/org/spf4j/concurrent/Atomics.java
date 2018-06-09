/*
 * Copyright (c) 2001-2017, Zoltan Farkas All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 * Additionally licensed with:
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.spf4j.concurrent;

import com.google.common.util.concurrent.AtomicDouble;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.UnaryOperator;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 *
 * @author zoly
 */
@ParametersAreNonnullByDefault
public final class Atomics {

  private Atomics() {
  }

  public static <T> UpdateResult<T> update(final AtomicReference<T> ar, final UnaryOperator<T> function) {
    T initial;
    T newObj;
    do {
      initial = ar.get();
      newObj = function.apply(initial);
      if (Objects.equals(initial, newObj)) {
        return UpdateResult.same(initial);
      } else if (initial == newObj) {
        throw new IllegalStateException("Function " + function + " is mutating " + initial + ", this is not allowed");
      }
    } while (!ar.compareAndSet(initial, newObj));
    return UpdateResult.updated(newObj);
  }

  @SuppressFBWarnings("FE_FLOATING_POINT_EQUALITY")
  public static boolean maybeAccumulate(final AtomicDouble dval, final double x,
          final DoubleBinaryOperator accumulatorFunction) {
    double prev, next;
    do {
      prev = dval.get();
      next = accumulatorFunction.applyAsDouble(prev, x);
      if (prev == next) {
        return false;
      }
    } while (!dval.compareAndSet(prev, next));
    return true;
  }

  public static void accumulate(final AtomicDouble dval, final double x,
          final DoubleBinaryOperator accumulatorFunction) {
    double prev, next;
    do {
      prev = dval.get();
      next = accumulatorFunction.applyAsDouble(prev, x);
    } while (!dval.compareAndSet(prev, next));
  }

  @SuppressFBWarnings("FE_FLOATING_POINT_EQUALITY")
  public static boolean maybeAccumulate(final AtomicDouble dval,
          final DoubleUnaryOperator accumulatorFunction) {
    double prev, next;
    do {
      prev = dval.get();
      next = accumulatorFunction.applyAsDouble(prev);
      if (prev == next) {
        return false;
      }
    } while (!dval.compareAndSet(prev, next));
    return true;
  }

  public static double getAndAccumulate(final AtomicDouble dval,
          final DoubleUnaryOperator accumulatorFunction) {
    double prev, next;
    do {
      prev = dval.get();
      next = accumulatorFunction.applyAsDouble(prev);
    } while (!dval.compareAndSet(prev, next));
    return prev;
  }


}
