/*
 * Copyright (c) 2017, PostgreSQL Global Development Group
 * See the LICENSE file in the project root for more information.
 */

package org.postgresql.test.jdbc42;

import static org.junit.Assert.assertEquals;

import org.postgresql.test.TestUtil;
import org.postgresql.test.jdbc2.BaseTest4;

import org.junit.Ignore;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This is a really large and slow test, it's ignored for CI, can be tested locally.
 * WARNING: the resulting tables requires more than 100 GB of space
 */
public class LargeCountJdbc42Test extends BaseTest4 {

  @Override
  public void setUp() throws Exception {
    super.setUp();
  }

  @Override
  public void tearDown() throws SQLException {
    super.tearDown();
  }

  @Ignore
  @Test
  public void testExecuteLargeUpdateStatement() throws Exception {
    TestUtil.createTable(con, "largetable2", "somevalue boolean");
    long count;
    try (Statement stmt = con.createStatement()) {
      count = stmt.executeLargeUpdate("insert into largetable2 "
          + "select true from generate_series(1, 2147483757)");
    }
    long expected = Integer.MAX_VALUE + 110L;
    assertEquals("110 rows more than Integer.MAX_VALUE (long value)", expected, count);
    TestUtil.dropTable(con, "largetable2");
  }

  @Ignore
  @Test
  public void testExecuteLargeUpdatePreparedStatement() throws Exception {
    TestUtil.createTable(con, "largetable1", "somevalue boolean");
    long count;
    try (PreparedStatement stmt = con.prepareStatement("insert into largetable1 "
        + "select true from generate_series(?, ?)")) {
      stmt.setLong(1, 1);
      stmt.setLong(2, 2147483757L);
      count = stmt.executeLargeUpdate();
    }
    long expected = Integer.MAX_VALUE + 110L;
    assertEquals("110 rows more than Integer.MAX_VALUE (long value)", expected, count);
    TestUtil.dropTable(con, "largetable1");
  }

}
