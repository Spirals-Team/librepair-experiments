/*
 * MIT License
 * <p>
 * Copyright (c) 2017 David Krebs
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.dkarv.jdcallgraph.util;

import com.dkarv.jdcallgraph.util.log.*;

import java.util.HashMap;
import java.util.Map;

public class LineNumbers {
  private static final Logger LOG = new Logger(LineNumbers.class);
  private static final Map<String, Map<String, Integer>> LINES = new HashMap<>();

  /**
   * Whether the line number is already known for this method.
   */
  public static boolean contains(String clazz, String method) {
    Map<String, Integer> lines = LINES.get(clazz);
    return lines != null && lines.containsKey(method);
  }

  public static void add(String clazz, String method, int lineNumber) {
    LOG.debug("Add line number for {}::{}#{}", clazz, method, lineNumber);
    Map<String, Integer> lines = LINES.get(clazz);
    if (lines == null) {
      lines = new HashMap<>();
      LINES.put(clazz, lines);
    }

    lines.put(method, lineNumber);
  }

  public static int get(String clazz, String method) {
    Map<String, Integer> lines = LINES.get(clazz);
    if (lines == null) {
      throw new IllegalArgumentException("Line number for class " + clazz + " not known.");
    }
    Integer i = lines.get(method);
    if (i == null) {
      throw new IllegalArgumentException("Line number for method " + clazz + "::" + method +
          " unknown.");
    }
    return i;
  }

}
