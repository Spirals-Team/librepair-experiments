/*
 * Copyright (c) 2017, PostgreSQL Global Development Group
 * See the LICENSE file in the project root for more information.
 */

package org.postgresql.test.jdbc42;

import org.postgresql.test.TestUtil;
import org.postgresql.test.jdbc2.BaseTest4;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

/**
 * This have a really big and slow test, it's ignored for CI but can be tested locally to check that
 * it works.
 */
public class LargeCountJdbc42Test extends BaseTest4 {

  @Override
  public void setUp() throws Exception {
    super.setUp();
    TestUtil.createTable(con, "largetable", "somevalue boolean");
  }

  @Override
  public void tearDown() throws SQLException {
    TestUtil.dropTable(con, "largetable");
    super.tearDown();
  }

  /*
   * Warning: BIG AND SLOW, it's ignored, it takes more than 2 hours to complete.
   * Test PreparedStatement.executeLargeUpdate() and Statement.executeLargeUpdate(String sql)
   */
  @Ignore
  @Test
  public void testExecuteLargeUpdateBIG() throws Exception {
    TestUtil.truncateTable(con, "largetable");
    long expected = Integer.MAX_VALUE + 110L;
    // Test PreparedStatement.executeLargeUpdate()
    try (PreparedStatement stmt = con.prepareStatement("insert into largetable "
        + "select true from generate_series(?, ?)")) {
      stmt.setLong(1, 1);
      stmt.setLong(2, 2147483757L);
      long count = stmt.executeLargeUpdate();
      Assert.assertEquals("PreparedStatement 110 rows more than Integer.MAX_VALUE", expected, count);
    }
    // Test Statement.executeLargeUpdate(String sql)
    try (Statement stmt = con.createStatement()) {
      long count = stmt.executeLargeUpdate("delete from largetable");
      Assert.assertEquals("Statement 110 rows more than Integer.MAX_VALUE", expected, count);
    }
  }

  /*
   * Test Statement.executeLargeUpdate(String sql)
   */
  @Test
  public void testExecuteLargeUpdateStatementSMALL() throws Exception {
    try (Statement stmt = con.createStatement()) {
      long count = stmt.executeLargeUpdate("insert into largetable "
          + "select true from generate_series(1, 1010)");
      long expected = 1010L;
      Assert.assertEquals("Small long return 1010L", expected, count);
    }
  }

  /*
   * Test PreparedStatement.executeLargeUpdate();
   */
  @Test
  public void testExecuteLargeUpdatePreparedStatementSMALL() throws Exception {
    try (PreparedStatement stmt = con.prepareStatement("insert into largetable "
        + "select true from generate_series(?, ?)")) {
      stmt.setLong(1, 1);
      stmt.setLong(2, 1010L);
      long count = stmt.executeLargeUpdate();
      long expected = 1010L;
      Assert.assertEquals("Small long return 1010L", expected, count);
    }
  }

  /*
   * Test Statement.getLargeUpdateCount();
   */
  @Test
  public void testGetLargeUpdateCountStatementSMALL() throws Exception {
    try (Statement stmt = con.createStatement()) {
      boolean isResult = stmt.execute("insert into largetable "
          + "select true from generate_series(1, 1010)");
      Assert.assertFalse("False if it is an update count or there are no results", isResult);
      long count = stmt.getLargeUpdateCount();
      long expected = 1010L;
      Assert.assertEquals("Small long return 1010L", expected, count);
    }
  }

  /*
   * Test PreparedStatement.getLargeUpdateCount();
   */
  @Test
  public void testGetLargeUpdateCountPreparedStatementSMALL() throws Exception {
    try (PreparedStatement stmt = con.prepareStatement("insert into largetable "
        + "select true from generate_series(?, ?)")) {
      stmt.setInt(1, 1);
      stmt.setInt(2, 1010);
      boolean isResult = stmt.execute();
      Assert.assertFalse("False if it is an update count or there are no results", isResult);
      long count = stmt.getLargeUpdateCount();
      long expected = 1010L;
      Assert.assertEquals("Small long return 1010L", expected, count);
    }
  }

  /*
   * Test simple Statement.executeLargeBatch();
   */
  @Test
  public void testExecuteLargeBatchStatementSMALL() throws Exception {
    try (PreparedStatement stmt = con.prepareStatement("insert into largetable "
        + "select true from generate_series(?, ?)")) {
      stmt.setInt(1, 1);
      stmt.setInt(2, 200);
      stmt.addBatch(); // statement one
      stmt.setInt(1, 1);
      stmt.setInt(2, 100);
      stmt.addBatch(); // statement two
      stmt.setInt(1, 1);
      stmt.setInt(2, 50);
      stmt.addBatch(); // statement three
      long[] actual = stmt.executeLargeBatch();
      assertBatchResult("350 rows inserted via 3 batch", new long[]{200L, 100L, 50L}, actual);
    }
  }

  public static void assertBatchResult(String message, long[] expected, long[] actual) {
    long[] clone = expected.clone();
    boolean hasChanges = false;
    for (int i = 0; i < actual.length; i++) {
      long a = actual[i];
      if (a == Statement.SUCCESS_NO_INFO && expected[i] >= 0) {
        clone[i] = a;
        hasChanges = true;
      }
    }
    if (hasChanges) {
      message += ", original expectation: " + Arrays.toString(expected);
    }
    Assert.assertEquals(
        message,
        Arrays.toString(clone),
        Arrays.toString(actual));
  }

}
