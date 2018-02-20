/*
 * Copyright (C) 2017 Seoul National University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.snu.coral.common.exception;

/**
 * DynamicOptimizationException.
 * Thrown for dynamic optimization related exceptions.
 */
public class CompileTimeOptimizationException extends RuntimeException {
  /**
   * Constructor of DynamicOptimizationException.
   * @param cause cause.
   */
  public CompileTimeOptimizationException(final Throwable cause) {
    super(cause);
  }

  /**
   * Constructor of DynamicOptimizationException.
   * @param message message.
   */
  public CompileTimeOptimizationException(final String message) {
    super(message);
  }
}
