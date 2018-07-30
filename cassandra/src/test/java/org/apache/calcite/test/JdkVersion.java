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

import com.google.common.annotations.VisibleForTesting;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses java version string (like {@code 1.9.0_20-b62}) to detect major version number
 * (like {@code 8, 9, 10} etc.).
 *
 * <p>Starting with version 9 this information is available using API
 * <a href="https://docs.oracle.com/javase/9/docs/api/java/lang/Runtime.Version.html">Runtime.version()</a>.
 *
 * @see <a href="http://openjdk.java.net/jeps/223">JEP 223: New Version-String Scheme</a>
 */
class JdkVersion {

  private JdkVersion() {}

  /**
   * Returns major JDK version parsed from {@code java.version} system property.
   *
   * <p>{@code
   *      1.9.0_20-b62 => 9
   *      9.0 => 9
   *      11-ea => 11
   *   }
   *
   *  @return major version (as integer)
   */
  static int major() {
    // TODO starting with JDK9 use Runtime.version()
    return majorFromString(System.getProperty("java.version"));
  }

  /**
   * See <a href="http://openjdk.java.net/jeps/223">JEP 223: New Version-String Scheme</a>
   *
   * @param version current version as string usually from {@code java.version} property.
   * @return major java version
   */
  @VisibleForTesting
  static int majorFromString(String version) {
    Objects.requireNonNull(version, "version");
    if (version.contains(".")) {
      String[] parts = version.split("\\.");
      final int major = Integer.parseInt(parts[0]);
      final int minor = Integer.parseInt(parts[1]);
      return major == 1 ? minor : major;
    } else {
      Matcher matcher = Pattern.compile("^\\d+").matcher(version);
      if (matcher.find()) {
        return Integer.parseInt(matcher.group());
      } else {
        throw new IllegalStateException("Unknown java version: " + version);
      }
    }
  }
}

// End JdkVersion.java
