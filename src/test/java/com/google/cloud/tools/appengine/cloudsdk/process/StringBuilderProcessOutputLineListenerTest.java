/*
 * Copyright 2016 Google Inc.
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

package com.google.cloud.tools.appengine.cloudsdk.process;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/** Unit tests for {@link StringBuilderProcessOutputLineListener} */
public class StringBuilderProcessOutputLineListenerTest {

  private StringBuilderProcessOutputLineListener listener;

  @Before
  public void setup() {
    listener = new StringBuilderProcessOutputLineListener();
  }

  @Test
  public void testToString_empty() {
    assertEquals("", listener.toString());
  }

  @Test
  public void testToString_nonempty() {
    listener.onOutputLine("line 1");
    listener.onOutputLine("line 2");
    assertEquals("line 1line 2", listener.toString());
  }
}
