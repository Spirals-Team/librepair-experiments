/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to you under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.calcite.test;


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for parsing java version from a generic string
 */
public class JdkVersionTest {

  @Test
  public void current() {
    // shouldn't throw any exceptions
    JdkVersion.major();
  }

  @Test
  public void fromString() {
    assertEquals(4, JdkVersion.majorFromString("1.4.2_03"));
    assertEquals(5, JdkVersion.majorFromString("1.5.0_16"));
    assertEquals(6, JdkVersion.majorFromString("1.6.0_22"));
    assertEquals(8, JdkVersion.majorFromString("1.8.0_72-internal"));
    assertEquals(8, JdkVersion.majorFromString("1.8.0_151"));
    assertEquals(8, JdkVersion.majorFromString("1.8.0_141"));
    assertEquals(9, JdkVersion.majorFromString("1.9.0_20-b62"));
    assertEquals(9, JdkVersion.majorFromString("1.9.0-ea-b19"));
    assertEquals(9, JdkVersion.majorFromString("9"));
    assertEquals(9, JdkVersion.majorFromString("9.0"));
    assertEquals(9, JdkVersion.majorFromString("9.0.1"));
    assertEquals(9, JdkVersion.majorFromString("9-ea"));
    assertEquals(9, JdkVersion.majorFromString("9.0.1"));
    assertEquals(10, JdkVersion.majorFromString("10"));
    assertEquals(10, JdkVersion.majorFromString("10-ea"));
    assertEquals(10, JdkVersion.majorFromString("10.0"));
    assertEquals(10, JdkVersion.majorFromString("10.0.1"));
    assertEquals(10, JdkVersion.majorFromString("10.1.1-foo"));
    assertEquals(11, JdkVersion.majorFromString("11"));
    assertEquals(11, JdkVersion.majorFromString("11-ea"));
    assertEquals(11, JdkVersion.majorFromString("11.0"));
    assertEquals(2017, JdkVersion.majorFromString("2017"));
    assertEquals(2017, JdkVersion.majorFromString("2017.0"));
    assertEquals(2017, JdkVersion.majorFromString("2017.12"));
    assertEquals(2017, JdkVersion.majorFromString("2017.12.31"));
  }
}

// End JdkVersionTest.java
