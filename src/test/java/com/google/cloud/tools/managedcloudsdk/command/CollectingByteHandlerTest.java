/*
 * Copyright 2017 Google Inc.
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

package com.google.cloud.tools.managedcloudsdk.command;

import com.google.cloud.tools.managedcloudsdk.command.AsyncStreamSaverFactory.CollectingByteHandler;
import org.junit.Assert;
import org.junit.Test;

/** Tests for {@link CollectingByteHandler}. */
public class CollectingByteHandlerTest {

  @Test
  public void testByteHandler_smokeTest() {
    CollectingByteHandler testHandler = new CollectingByteHandler();
    testHandler.bytes("line1\n".getBytes(), 6);
    testHandler.bytes("line2\n".getBytes(), 6);
    Assert.assertEquals("line1\nline2\n", testHandler.getResult());
  }

  @Test
  public void testByteHandler_junkIgnored() {
    CollectingByteHandler testHandler = new CollectingByteHandler();
    testHandler.bytes("line1\n123".getBytes(), 6);
    testHandler.bytes("line2\n456".getBytes(), 6);
    Assert.assertEquals("line1\nline2\n", testHandler.getResult());
  }
}
