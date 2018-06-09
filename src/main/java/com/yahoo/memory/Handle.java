/*
 * Copyright 2017, Yahoo! Inc. Licensed under the terms of the
 * Apache License 2.0. See LICENSE file at the project root for terms.
 */

package com.yahoo.memory;

/**
 * A handle for Memory.
 * @author Lee Rhodes
 */
public interface Handle extends AutoCloseable {

  /**
   * Gets a Memory
   * @return a Memory
   */
  Memory get();

  @Override
  void close();
}
