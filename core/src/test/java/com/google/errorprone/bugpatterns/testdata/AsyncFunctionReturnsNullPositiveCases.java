/*
 * Copyright 2015 The Error Prone Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.errorprone.bugpatterns.testdata;

import static com.google.common.util.concurrent.Futures.immediateFuture;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.CheckedFuture;
import com.google.common.util.concurrent.ListenableFuture;

/** Positive cases for {@link AsyncFunctionReturnsNull}. */
public class AsyncFunctionReturnsNullPositiveCases {
  static {
    new AsyncFunction<String, Object>() {
      @Override
      public ListenableFuture<Object> apply(String input) throws Exception {
        // BUG: Diagnostic contains: immediateFuture(null)
        return null;
      }
    };

    new AsyncFunction<Object, String>() {
      @Override
      public ListenableFuture<String> apply(Object o) {
        if (o instanceof String) {
          return immediateFuture((String) o);
        }
        // BUG: Diagnostic contains: immediateFuture(null)
        return null;
      }
    };

    new AsyncFunction<Object, String>() {
      @Override
      public CheckedFuture<String, Exception> apply(Object o) {
        // BUG: Diagnostic contains: immediateFuture(null)
        return null;
      }
    };

    new MyAsyncFunction() {
      @Override
      public CheckedFuture<String, Exception> apply(Object o) {
        // BUG: Diagnostic contains: immediateFuture(null)
        return null;
      }
    };
  }

  static class MyAsyncFunction implements AsyncFunction<Object, String> {
    @Override
    public ListenableFuture<String> apply(Object input) throws Exception {
      return immediateFuture(input.toString());
    }
  }
}
