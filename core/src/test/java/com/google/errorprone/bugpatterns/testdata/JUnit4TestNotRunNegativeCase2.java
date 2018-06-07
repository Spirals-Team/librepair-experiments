/*
 * Copyright 2013 The Error Prone Authors.
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

package com.google.errorprone.bugpatterns.testdata;

import org.junit.internal.runners.JUnit38ClassRunner;
import org.junit.runner.RunWith;

/**
 * Not a JUnit 4 test (run with a JUnit3 test runner).
 *
 * @author eaftan@google.com (Eddie Aftandilian)
 */
@RunWith(JUnit38ClassRunner.class)
public class JUnit4TestNotRunNegativeCase2 {
  public void testThisIsATest() {}
}
