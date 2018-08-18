/*
 * Copyright 2017 Google Inc. All Rights Reserved.
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
package com.google.cloud.bigtable.util;

import com.google.protobuf.ByteString;

public class RowKeyWrapper implements Comparable<RowKeyWrapper> {

  private final ByteString key;

  public RowKeyWrapper(ByteString key) {
    this.key = key;
  }

  public ByteString getKey() {
    return key;
  }

  @Override
  public int compareTo(RowKeyWrapper o) {
    return ByteStringComparator.INSTANCE.compare(key, o.key);
  }
}
