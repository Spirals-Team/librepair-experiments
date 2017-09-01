/*
 * Copyright (c) 2017, PostgreSQL Global Development Group
 * See the LICENSE file in the project root for more information.
 */

package org.postgresql.benchmark.statement;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark to test performance of Boolean String Conversion Methods method.
 *
 * @author jorsol
 */
@Fork(value = 1, jvmArgsPrepend = "-Xmx128m")
@Measurement(iterations = 15, time = 1, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Thread)
@Threads(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class StringEqualsBoolean {

  @Param({"0", "1", " T", "F ", "True", "False", "   ON   ", "   OFF   "})
  public String value;

  @Param({"0", "500", "1000"})
  private int tokens;

  @Benchmark
  public void getBooleanFromStringNew(Blackhole b) throws SQLException {
    Blackhole.consumeCPU(tokens);
    b.consume(fromStringNew(value));
  }

  @Benchmark
  public void getBooleanFromStringOld(Blackhole b) throws SQLException {
    Blackhole.consumeCPU(tokens);
    b.consume(fromStringOld(value));
  }

  public static boolean fromStringNew(final String strval) throws SQLException {
    // Leading or trailing whitespace is ignored, and case does not matter.
    final String val = strval.trim();
    if (1 == val.length()) {
      return fromCharacterNew(val.charAt(0));
    }
    if ("true".equalsIgnoreCase(val) || "yes".equalsIgnoreCase(val) || "on".equalsIgnoreCase(val)) {
      return true;
    }
    if ("false".equalsIgnoreCase(val) || "no".equalsIgnoreCase(val) || "off".equalsIgnoreCase(val)) {
      return false;
    }
    throw cannotCoerceException(strval);
  }

  public static boolean fromCharacterNew(final Character charval) throws SQLException {
    switch (charval) {
      case '1':
      case 't':
      case 'T':
      case 'y':
      case 'Y':
        return true;
      case '0':
      case 'f':
      case 'F':
      case 'n':
      case 'N':
        return false;
      default:
        throw cannotCoerceException(charval);
    }
  }

  public static boolean fromStringOld(final String strval) throws SQLException {
    // Leading or trailing whitespace is ignored, and case does not matter.
    final String val = strval.trim();
    if ("1".equals(val) || "true".equalsIgnoreCase(val)
        || "t".equalsIgnoreCase(val) || "yes".equalsIgnoreCase(val)
        || "y".equalsIgnoreCase(val) || "on".equalsIgnoreCase(val)) {
      return true;
    }
    if ("0".equals(val) || "false".equalsIgnoreCase(val)
        || "f".equalsIgnoreCase(val) || "no".equalsIgnoreCase(val)
        || "n".equalsIgnoreCase(val) || "off".equalsIgnoreCase(val)) {
      return false;
    }
    throw cannotCoerceException(strval);
  }

  public static boolean fromCharacterOld(final Character charval) throws SQLException {
    if ('1' == charval || 't' == charval || 'T' == charval
        || 'y' == charval || 'Y' == charval) {
      return true;
    }
    if ('0' == charval || 'f' == charval || 'F' == charval
        || 'n' == charval || 'N' == charval) {
      return false;
    }
    throw cannotCoerceException(charval);
  }

  public static SQLException cannotCoerceException(final Object value) {
    return new SQLException("Cannot cast to boolean: " + String.valueOf(value));
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(StringEqualsBoolean.class.getSimpleName())
        .detectJvmArgs()
        .build();

    new Runner(opt).run();
  }
}
