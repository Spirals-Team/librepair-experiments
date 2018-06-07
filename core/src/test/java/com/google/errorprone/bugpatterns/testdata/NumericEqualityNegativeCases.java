/*
 * Copyright 2012 The Error Prone Authors.
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

/** @author scottjohnson@google.com (Scott Johnsson) */
public class NumericEqualityNegativeCases {

  public static final Integer NULLINT = null;

  public boolean testEquality(Integer x, Integer y) {
    boolean retVal;

    retVal = x.equals(y);
    retVal = (x == null);
    retVal = (x != null);
    retVal = (null == x);
    retVal = (null != x);
    retVal = x == 1000;
    retVal = x + y == y + x;
    retVal = x == NULLINT;
    retVal = NULLINT == x;

    return retVal;
  }

  @SuppressWarnings("NumericEquality")
  public boolean testSuppressWarnings(Integer x, Integer y) {
    boolean retVal;

    retVal = (x != y);
    retVal = (x == y);

    return retVal;
  }

  public boolean testComparisons(Integer x, Integer y) {
    boolean retVal;

    retVal = x <= y;
    retVal = x < y;
    retVal = x >= y;
    retVal = x > y;

    return retVal;
  }

  public boolean testUnboxing(Integer x, int y) {
    boolean retVal;

    retVal = (x == y);
    retVal = (x != y);

    final int constValue = 1000;
    retVal = (x == constValue);
    retVal = (x != constValue);

    return retVal;
  }
}
