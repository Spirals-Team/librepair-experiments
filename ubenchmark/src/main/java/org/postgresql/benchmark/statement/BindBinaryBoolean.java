/*
 * Copyright (c) 2017, PostgreSQL Global Development Group
 * See the LICENSE file in the project root for more information.
 */

package org.postgresql.benchmark.statement;

import org.postgresql.util.ConnectionUtil;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.infra.Blackhole;

@Fork(value = 1, jvmArgsPrepend = "-Xmx128m")
@Measurement(iterations = 15, time = 1, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@State(Scope.Thread)
@Threads(2)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class BindBinaryBoolean {

  private Connection connection;
  private PreparedStatement ps;

  @Param({"true", "false"})
  private boolean binaryTransferDisable;

  @Param({"0", "500", "1000"})
  private int tokens;

  @Setup(Level.Trial)
  public void setUp() throws SQLException {
    Properties props = ConnectionUtil.getProperties();
    if (binaryTransferDisable) {
      props.setProperty("binaryTransferDisable", "16");
    }

    connection = DriverManager.getConnection(ConnectionUtil.getURL(), props);
    ps = connection.prepareStatement("select ?, ?, ?, ?, ?");
  }

  @TearDown(Level.Trial)
  public void tearDown() throws SQLException {
    ps.close();
    connection.close();
  }

  @Benchmark
  public void bindBinaryBoolean(Blackhole b) throws SQLException {
    Blackhole.consumeCPU(tokens);
    ps.setBoolean(1, true);
    ps.setObject(2, 0, Types.BOOLEAN);
    ps.setObject(3, null, Types.BOOLEAN);
    ps.setObject(4, "on", Types.BOOLEAN);
    ps.setObject(5, "1", Types.BOOLEAN);
    try (ResultSet rs = ps.executeQuery()) {
      while (rs.next()) {
        b.consume(rs.getBoolean(1));
        b.consume(rs.getBoolean(2));
        b.consume(rs.getBoolean(3));
        b.consume(rs.getBoolean(4));
        b.consume(rs.getBoolean(5));
      }
    }
  }

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(BindBinaryBoolean.class.getSimpleName())
        .detectJvmArgs()
        .build();

    new Runner(opt).run();
  }
}
