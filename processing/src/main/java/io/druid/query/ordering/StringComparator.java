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

package io.druid.query.ordering;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.druid.java.util.common.IAE;

import java.util.Comparator;

public abstract class StringComparator implements Comparator<String>
{
  @JsonCreator
  public static StringComparator fromString(String type)
  {
    switch (type.toLowerCase()) {
      case StringComparators.LEXICOGRAPHIC_NAME:
        return StringComparators.LEXICOGRAPHIC;
      case StringComparators.ALPHANUMERIC_NAME:
        return StringComparators.ALPHANUMERIC;
      case StringComparators.STRLEN_NAME:
        return StringComparators.STRLEN;
      case StringComparators.NUMERIC_NAME:
        return StringComparators.NUMERIC;
      default:
        throw new IAE("Unknown string comparator[%s]", type);
    }
  }

  public abstract byte[] getCacheKey();
}
