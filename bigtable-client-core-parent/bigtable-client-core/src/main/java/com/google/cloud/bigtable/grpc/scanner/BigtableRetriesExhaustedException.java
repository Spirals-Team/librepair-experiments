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

import java.io.IOException;

/**
 * An Exception that is thrown when an operation fails, even in the face of retries.
 *
 * @author sduskis
 * @version $Id: $Id
 */
public class BigtableRetriesExhaustedException extends IOException {
  private static final long serialVersionUID = 6905598607595217072L;

  /**
   * <p>Constructor for BigtableRetriesExhaustedException.</p>
   *
   * @param message a {@link java.lang.String} object.
   * @param cause a {@link java.lang.Throwable} object.
   */
  public BigtableRetriesExhaustedException(String message, Throwable cause) {
    super(message, cause);
  }
}
