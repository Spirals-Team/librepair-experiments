/*
 * Copyright 2017 The Error Prone Authors.
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

package com.google.errorprone.bugpatterns.overloading.testdata;

import java.util.List;
import java.util.Map;

public class InconsistentOverloadsPositiveCasesOverrides {

  class SuperClass {

    void someMethod(String foo, int bar) {}

    // BUG: Diagnostic contains: someMethod(String foo, int bar, List<String> baz)
    void someMethod(int bar, String foo, List<String> baz) {}
  }

  class SubClass extends SuperClass {

    @Override // no bug
    void someMethod(String foo, int bar) {}

    @Override // no bug
    void someMethod(int bar, String foo, List<String> baz) {}

    // BUG: Diagnostic contains: someMethod(String foo, int bar, List<String> baz, Map<String,
    // String> fizz)
    void someMethod(int bar, String foo, List<String> baz, Map<String, String> fizz) {}
  }
}
