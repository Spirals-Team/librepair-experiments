/*
 * Copyright 2018 SPF4J.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.spf4j.test.log;

import com.google.common.annotations.Beta;
import com.google.common.collect.ImmutableList;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.logging.LogManager;
import java.util.stream.Collector;
import javax.annotation.CheckReturnValue;
import javax.annotation.concurrent.GuardedBy;
import org.hamcrest.Matcher;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.spf4j.base.XCollectors;
import org.spf4j.test.log.junit4.Spf4jTestLogRunListenerSingleton;
//CHECKSTYLE:OFF
import sun.misc.Contended;
//CHECKSTYLE:ON

/**
 * @author Zoltan Farkas
 */
@SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
public final class TestLoggers implements ILoggerFactory {

  private static final Map<Level, java.util.logging.Level> LEV_MAP = new EnumMap<>(Level.class);

  /**
   * FINEST -> TRACE FINER -> DEBUG FINE -> DEBUG INFO -> INFO WARNING -> WARN SEVERE -> ERROR
   */
  static {
    LEV_MAP.put(Level.TRACE, java.util.logging.Level.FINEST);
    LEV_MAP.put(Level.DEBUG, java.util.logging.Level.FINER);
    LEV_MAP.put(Level.INFO, java.util.logging.Level.INFO);
    LEV_MAP.put(Level.WARN, java.util.logging.Level.WARNING);
    LEV_MAP.put(Level.ERROR, java.util.logging.Level.SEVERE);
  }

  private static final TestLoggers INSTANCE = new TestLoggers();

  private final ConcurrentMap<String, Logger> loggerMap;

  private final Object sync;

  private final Function<String, Logger> computer;

  private final java.util.logging.Logger julGlobal;

  private final java.util.logging.Logger julRoot;

  @GuardedBy("sync")
  @Contended
  private volatile LogConfig config;

  public static TestLoggers sys() {
    return INSTANCE;
  }

  @SuppressWarnings("checkstyle:regexp")
  private TestLoggers() {
    sync = new Object();
    loggerMap = new ConcurrentHashMap<String, Logger>();
    Level rootPrintLevel = TestUtils.isExecutedFromIDE()
            ? Level.valueOf(System.getProperty("spf4j.testLog.rootPrintLevelIDE", "DEBUG"))
            : Level.valueOf(System.getProperty("spf4j.testLog.rootPrintLevel", "INFO"));

    computer = (k) -> new TestLogger(k, TestLoggers.this::getConfig);
    config = new LogConfigImpl(
            ImmutableList.of(new LogPrinter(rootPrintLevel), new DefaultAsserter()),
            Collections.EMPTY_MAP);
    LogManager.getLogManager().reset();
    SLF4JBridgeHandler.removeHandlersForRootLogger();
    SLF4JBridgeHandler.install();
    julGlobal = java.util.logging.Logger.getGlobal();
    julRoot = java.util.logging.Logger.getLogger("");
    resetJulConfig();
  }

  private void resetJulConfig() {
    java.util.logging.Level julLevel = LEV_MAP.get(config.minRootLevel());
    julGlobal.setLevel(julLevel);
    julRoot.setLevel(julLevel);
  }

  public LogConfig getConfig() {
    return config;
  }

  /**
   * Print logs above a category and log level.
   *
   * @param category the log category.
   * @param level the log level.
   * @return a handler that allows this printing to stop (when calling close).
   */
  @CheckReturnValue
  public HandlerRegistration print(final String category, final Level level) {
    return intercept(category, new LogPrinter(level));
  }

  /**
   * Ability to intercept log messages logged under a category
   *
   * @param category the logger category name (a.b.c)
   * @param handler the log handler to register.
   * @return a registration handle, that you can use to unregister.
   */
  @CheckReturnValue
  public HandlerRegistration intercept(final String category, final LogHandler handler) {
    synchronized (sync) {
      config = config.add(category, handler);
      resetJulConfig();
    }
    return () -> {
      synchronized (sync) {
        config = config.remove(category, handler);
        resetJulConfig();
      }
    };
  }

  /**
   * Collect a bunch of logs.
   * @param <T> the type of object to collect into.
   * @param category the log category (a.b.c)
   * @param fromLevel from level to collect.
   * @param passThrough pass the logs to lower category handlers or not.
   * @param collector the collector to collect the logs.
   * @return collected logs.
   */
  @CheckReturnValue
  public <T> LogCollection<T> collect(final String category, final Level fromLevel,
          final boolean passThrough,
          final Collector<LogRecord, ?, T> collector) {
    return collect(category, fromLevel, Level.ERROR, passThrough, collector);
  }

  /**
   * Collect a bunch of logs.
   * @param <T> the type of object to collect into.
   * @param category the log category (a.b.c)
   * @param fromLevel from level to collect.
   * @param toLevel to level to collect.
   * @param passThrough pass the logs to lower category handlers or not.
   * @param collector the collector to collect the logs.
   * @return collected logs.
   */
  @CheckReturnValue
  public <T> LogCollection<T> collect(final String category, final Level fromLevel, final Level toLevel,
          final boolean passThrough,
          final Collector<LogRecord, ?, T> collector) {
    LogCollectorHandler handler = new LogCollectorHandler(fromLevel, toLevel, passThrough, collector) {

      private boolean isClosed = false;

      @Override
      public void close() {
        synchronized (sync) {
          if (!isClosed) {
            config = config.remove(category, this);
            resetJulConfig();
            isClosed = true;
          }
        }
      }
    };
    synchronized (sync) {
      config = config.add(category, handler);
      resetJulConfig();
    }
    return handler;
  }

  /**
   * Convenience method for functional use.
   *
   * @param category the log category.
   * @param handler a functional handler.
   * @return a registration handle to allow you to undo the registration.
   */
  @CheckReturnValue
  public HandlerRegistration interceptAllLevels(final String category, final AllLevelsLogHandler handler) {
    return intercept(category, handler);
  }

  /**
   * all logs from category and specified levels will be ignored... (unless there are more specific handlers)
   * @param category the log category.
   * @param from from log level.
   * @param to to log level.
   * @return a registration handle to allow you to undo this filtering.
   */
  @CheckReturnValue
  public HandlerRegistration ignore(final String category, final Level from, final Level to) {
    return intercept(category, new ConsumeAllLogs(from, to));
  }

  /**
   * Create an log expectation that can be asserted like:
   * <code>
   * LogAssert expect = TestLoggers.expect("org.spf4j.test", Level.ERROR, Matchers.hasProperty("format",
   * Matchers.equalTo("Booo")));
   * LOG.error("Booo", new RuntimeException());
   * expect.assertObservation();
   * </code>
   * @param category the category under which we should expect these messages.
   * @param minimumLogLevel minimum log level of expected log messages
   * @param matchers a succession of LogMessages with each matching a Matcher is expected.
   * @return an assertion handle.
   */
  @CheckReturnValue
  public LogAssert expect(final String category, final Level minimumLogLevel,
          final Matcher<LogRecord>... matchers) {
    return logAssert(true, minimumLogLevel, category, matchers);
  }

  /**
   * the opposite of expect.
   * @param category the category under which we should expect these messages.
   * @param minimumLogLevel minimum log level of expected log messages
   * @param matchers a succession of LogMessages with each matching a Matcher is NOT expected.
   * @return an assertion handle.
   */
  @CheckReturnValue
  public LogAssert dontExpect(final String category, final Level minimumLogLevel,
          final Matcher<LogRecord>... matchers) {
    return logAssert(false, minimumLogLevel, category, matchers);
  }

  private LogAssert logAssert(final boolean assertSeen, final Level minimumLogLevel,
          final String category, final Matcher<LogRecord>... matchers) {
    LogMatchingHandler handler = new LogMatchingHandler(assertSeen, category, minimumLogLevel, matchers) {

      private boolean isClosed = false;

      @Override
      public void close() {
        synchronized (sync) {
          if (!isClosed) {
            config = config.remove(category, this);
            isClosed = true;
            resetJulConfig();
          }
        }
      }

    };
    synchronized (sync) {
      config = config.add(category, handler);
      resetJulConfig();
    }
    return handler;
  }

  /**
   * Ability to assert is you expect a sequence of logs to be repeated.
   *
   * @param category the log category (a.b.c)
   * @param minimumLogLevel the minimum log level of expected messages.
   * @param nrTimes number of time the sequence should appear.
   * @param matchers the sequence of matchers.
   * @return the assertion handle.
   */
  public LogAssert expect(final String category, final Level minimumLogLevel,
          final int nrTimes, final Matcher<LogRecord>... matchers) {
    Matcher<LogRecord>[] newMatchers = new Matcher[matchers.length * nrTimes];
    for (int i = 0, j = 0; i < nrTimes; i++) {
      for (Matcher<LogRecord> m : matchers) {
        newMatchers[j++] = m;
      }
    }
    return expect(category, minimumLogLevel, newMatchers);
  }

  /**
   * Assert uncaught exceptions. (from threads)
   * @param matcher the exception matcher.
   * @return the assertion handle.
   */
  @Beta
  public AsyncObservationAssert expectUncaughtException(final Matcher<UncaughtExceptionDetail> matcher) {
    ExceptionHandoverRegistry reg = Spf4jTestLogRunListenerSingleton.getInstance().getUncaughtExceptionHandler();
    UncaughtExceptionAsserter asserter = new UncaughtExceptionAsserter(matcher) {
      @Override
      void unregister() {
        reg.remove(this);
      }
    };
    reg.add(asserter);
    asserter.waitUntilReading();
    return asserter;
  }

  /**
   * Collect up to a number of log messages.
   * @param minimumLogLevel the minimum log level of the messages.
   * @param maxNrLogs the max number of messages to collect.
   * @param collectPrinted collect messages that have been printed or not.
   * @return the collection of messages.
   */
  public LogCollection<ArrayDeque<LogRecord>> collect(final Level minimumLogLevel, final int maxNrLogs,
          final boolean collectPrinted) {
    return collect("", minimumLogLevel, maxNrLogs, collectPrinted);
  }

  private LogCollection<ArrayDeque<LogRecord>> collect(final String category, final Level minimumLogLevel,
          final int maxNrLogs,
          final boolean collectPrinted) {
    if (!collectPrinted) {
      return collect(category,
            minimumLogLevel,
            true,
            XCollectors.filtering(
                    (l) -> !l.hasAttachment(LogPrinter.PRINTED),
                    XCollectors.last(maxNrLogs,
                    new LogRecord(new TestLogger("test", () -> null), Level.INFO, "Truncated beyond {} ", maxNrLogs))));
    } else {
      return collect(category,
            minimumLogLevel,
            true,
            (Collector<LogRecord, ?, ArrayDeque<LogRecord>>) XCollectors.last(maxNrLogs,
                    new LogRecord(new TestLogger("test", () -> null), Level.INFO, "Truncated beyond {} ", maxNrLogs)));
    }
  }

  /**
   * Return an appropriate {@link SimpleLogger} instance by name.
   */
  public Logger getLogger(final String name) {
    return loggerMap.computeIfAbsent(name, computer);
  }

  public java.util.logging.Logger getJulGlobal() {
    return julGlobal;
  }

  public java.util.logging.Logger getJulRoot() {
    return julRoot;
  }

  @Override
  public String toString() {
    return "TestLoggers{ config=" + config + ", loggerMap=" + loggerMap + '}';
  }

}
