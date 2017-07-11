/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.calcite.rel.metadata;

/** Placeholder for null values. */
public enum NullSentinel {
  /** Placeholder for a null value. */
  INSTANCE,

  /** Placeholder that means that a request for metadata is already active,
   * therefore this request forms a cycle. */
  ACTIVE;

  public static Comparable mask(Comparable value) {
    if (value == null) {
      return INSTANCE;
    }
    return value;
  }

  public static Object mask(Object value) {
    if (value == null) {
      return INSTANCE;
    }
    return value;
  }
}

// End NullSentinel.java
