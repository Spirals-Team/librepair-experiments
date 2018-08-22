/*
 * Copyright 2015 Google Inc. All Rights Reserved.
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
package com.google.cloud.bigtable.grpc.scanner;


import java.io.Closeable;
import java.io.IOException;

/**
 * A scanner of Bigtable rows.
 *
 * @param <T> The type of Rows this scanner will iterate over. Expected Bigtable Row objects.
 * @author sduskis
 * @version $Id: $Id
 */
public interface ResultScanner<T> extends Closeable {
  /**
   * Read the next row and block until a row is available. Will return null on end-of-stream.
   *
   * @return a T object.
   * @throws java.io.IOException if any.
   */
  T next() throws IOException;

  /**
   * Read the next N rows where N &lt;= count. Will block until count are available or end-of-stream is
   * reached.
   *
   * @param count The number of rows to read.
   * @return an array of T objects.
   * @throws java.io.IOException if any.
   */
  T[] next(int count) throws IOException;

  /**
   * Check number of rows immediately available. Calls to {@link #next()} will not block on network for at least
   * n results.
   *
   * @return a int.
   */
  int available();
}
