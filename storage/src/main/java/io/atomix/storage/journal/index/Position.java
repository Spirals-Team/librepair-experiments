/*
 * Copyright 2018-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.atomix.storage.journal.index;

import static com.google.common.base.MoreObjects.toStringHelper;

/**
 * Journal index position.
 */
public class Position {
  private final long index;
  private final int position;

  public Position(long index, int position) {
    this.index = index;
    this.position = position;
  }

  public long index() {
    return index;
  }

  public int position() {
    return position;
  }

  @Override
  public String toString() {
    return toStringHelper(this)
        .add("index", index)
        .add("position", position)
        .toString();
  }
}
