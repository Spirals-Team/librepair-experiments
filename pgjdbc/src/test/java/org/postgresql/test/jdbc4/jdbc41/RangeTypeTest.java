/*
 * Copyright (c) 2017, PostgreSQL Global Development Group
 * See the LICENSE file in the project root for more information.
 */

package org.postgresql.test.jdbc4.jdbc41;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.postgresql.test.TestUtil;
import org.postgresql.test.jdbc2.BaseTest4;
import org.postgresql.util.PGint4range;
import org.postgresql.util.PGint8range;
import org.postgresql.util.PGnumrange;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

@RunWith(Parameterized.class)
public class RangeTypeTest extends BaseTest4 {

  public RangeTypeTest(BinaryMode binaryMode) {
    setBinaryMode(binaryMode);
  }

  @Parameterized.Parameters(name = "binary = {0}")
  public static Iterable<Object[]> data() {
    Collection<Object[]> ids = new ArrayList<Object[]>();
    for (BinaryMode binaryMode : BinaryMode.values()) {
      ids.add(new Object[]{binaryMode});
    }
    return ids;
  }

  @Override
  public void setUp() throws Exception {
    super.setUp();
    TestUtil.createTable(con, "table1",
            "int4range_column int4range,"
            + "int8range_column int8range,"
            + "numrange_column numrange,"
            + "tsrange_column tsrange,"
            + "tstzrange_column tstzrange,"
            + "daterange_column daterange"
    );
  }

  @Override
  public void tearDown() throws SQLException {
    TestUtil.dropTable(con, "table1");
    super.tearDown();
  }

  @Test
  public void select() throws SQLException {
    try (Statement stmt = con.createStatement()) {

      stmt.executeUpdate(TestUtil.insertSQL("table1",
              "int4range_column,"
              + "int8range_column,"
              + "numrange_column,"
              + "tsrange_column,"
              + "tstzrange_column,"
              + "daterange_column",
              "'[4,20)',"
              + "'[8,80)',"
              + "'[11.1,22.2)',"
              + "'[2010-01-01 14:30, 2010-01-01 15:30)',"
              + "'[2010-01-01 14:30 PST, 2010-01-01 15:30 PST)',"
              + "'[2010-01-01, 2010-01-01)'"));

      try (ResultSet rs = stmt.executeQuery(TestUtil.selectSQL("table1", "int4range_column"))) {
        assertTrue(rs.next());
        assertNotNull(rs.getObject("int4range_column"));
        assertFalse(rs.next());
      }
    }

  }

  @Test
  public void selectInt4Range() throws SQLException {
    try (Statement stmt = con.createStatement()) {

      stmt.executeUpdate(TestUtil.insertSQL("table1", "int4range_column", "'(,)'"));
      stmt.executeUpdate(TestUtil.insertSQL("table1", "int4range_column", "'[-2147483648,2147483647)'"));
      stmt.executeUpdate(TestUtil.insertSQL("table1", "int4range_column", "'(,3)'"));
      stmt.executeUpdate(TestUtil.insertSQL("table1", "int4range_column", "'[4,20)'"));
      stmt.executeUpdate(TestUtil.insertSQL("table1", "int4range_column", "'[50,)'"));

      try (ResultSet rs = stmt.executeQuery(TestUtil.selectSQL("table1", "int4range_column", null, "int4range_column"))) {

        assertTrue(rs.next());
        PGint4range range = rs.getObject("int4range_column", PGint4range.class);
        // (,)
        assertFalse(range.isLowerInclusive());
        assertTrue(range.isLowerInfinite());
        assertFalse(range.isUpperInclusive());
        assertTrue(range.isUpperInfinite());

        assertTrue(rs.next());
        range = rs.getObject("int4range_column", PGint4range.class);
        // [-2147483648,2147483647)
        assertTrue(range.isLowerInclusive());
        assertFalse(range.isLowerInfinite());
        assertEquals(Integer.valueOf(Integer.MIN_VALUE), range.getLowerBound());
        assertEquals(Integer.valueOf(Integer.MAX_VALUE), range.getUpperBound());
        assertFalse(range.isUpperInclusive());
        assertFalse(range.isUpperInfinite());

        assertTrue(rs.next());
        range = rs.getObject("int4range_column", PGint4range.class);
        // (,3)
        assertFalse(range.isLowerInclusive());
        assertTrue(range.isLowerInfinite());
        assertEquals(Integer.valueOf(3), range.getUpperBound());
        assertFalse(range.isUpperInclusive());
        assertFalse(range.isUpperInfinite());

        assertTrue(rs.next());
        range = rs.getObject("int4range_column", PGint4range.class);
        // [4,20)
        assertTrue(range.isLowerInclusive());
        assertFalse(range.isLowerInfinite());
        assertEquals(Integer.valueOf(4), range.getLowerBound());
        assertEquals(Integer.valueOf(20), range.getUpperBound());
        assertFalse(range.isUpperInclusive());
        assertFalse(range.isUpperInfinite());

        assertTrue(rs.next());
        range = rs.getObject("int4range_column", PGint4range.class);
        // [50,)
        assertTrue(range.isLowerInclusive());
        assertFalse(range.isLowerInfinite());
        assertEquals(Integer.valueOf(50), range.getLowerBound());
        assertFalse(range.isUpperInclusive());
        assertTrue(range.isUpperInfinite());

        assertFalse(rs.next());
      }
    }

  }

  @Test
  public void insertInt4Range() throws SQLException {
    try (PreparedStatement insert = con.prepareStatement("INSERT INTO table1 (int4range_column) VALUES (?)")) {
      assertInt4RangeInsert(insert, new PGint4range("[1,1)"), "(,)");
      assertInt4RangeInsert(insert, new PGint4range("(,)"), "(,)");

      assertInt4RangeInsert(insert, new PGint4range("[1,)"), "[1,)");
      assertInt4RangeInsert(insert, new PGint4range("(0,]"), "[1,)");

      assertInt4RangeInsert(insert, new PGint4range("[,3)"), "(,3)");
      assertInt4RangeInsert(insert, new PGint4range("[,2]"), "(,3)");

      assertInt4RangeInsert(insert, new PGint4range("[1,2)"), "[1,2)");
      assertInt4RangeInsert(insert, new PGint4range("[1,3)"), "[1,3)");
      assertInt4RangeInsert(insert, new PGint4range("(0,2]"), "[1,3)");
      assertInt4RangeInsert(insert, new PGint4range(1, 3), "[1,3)");
      assertInt4RangeInsert(insert, new PGint4range(0, false, 2, true), "[1,3)");
    }
  }

  private void assertInt4RangeInsert(PreparedStatement insert, PGint4range int4range, String expected)
          throws SQLException {
    insert.setObject(1, int4range);
    insert.executeUpdate();


    try (Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(TestUtil.selectSQL("table1", "int4range_column"))) {
      assertTrue(rs.next());
      assertEquals(expected, rs.getObject(1, PGint4range.class).getValue());
      assertFalse(rs.next());
      stmt.executeUpdate("DELETE FROM table1");
    }
  }

  @Test
  public void selectInt8Range() throws SQLException {
    try (Statement stmt = con.createStatement()) {

      stmt.executeUpdate(TestUtil.insertSQL("table1", "int8range_column", "'(,)'"));
      stmt.executeUpdate(TestUtil.insertSQL("table1", "int8range_column", "'[-9223372036854775808,9223372036854775807)'"));
      stmt.executeUpdate(TestUtil.insertSQL("table1", "int8range_column", "'(,3)'"));
      stmt.executeUpdate(TestUtil.insertSQL("table1", "int8range_column", "'[4,20)'"));
      stmt.executeUpdate(TestUtil.insertSQL("table1", "int8range_column", "'[50,)'"));

      try (ResultSet rs = stmt.executeQuery(TestUtil.selectSQL("table1", "int8range_column", null, "int8range_column"))) {

        assertTrue(rs.next());
        PGint8range range = rs.getObject("int8range_column", PGint8range.class);
        // (,)
        assertFalse(range.isLowerInclusive());
        assertTrue(range.isLowerInfinite());
        assertFalse(range.isUpperInclusive());
        assertTrue(range.isUpperInfinite());

        assertTrue(rs.next());
        range = rs.getObject("int8range_column", PGint8range.class);
        // [-9223372036854775808,9223372036854775807)
        assertTrue(range.isLowerInclusive());
        assertFalse(range.isLowerInfinite());
        assertEquals(Long.valueOf(Long.MIN_VALUE), range.getLowerBound());
        assertEquals(Long.valueOf(Long.MAX_VALUE), range.getUpperBound());
        assertFalse(range.isUpperInclusive());
        assertFalse(range.isUpperInfinite());

        assertTrue(rs.next());
        range = rs.getObject("int8range_column", PGint8range.class);
        // (,3)
        assertFalse(range.isLowerInclusive());
        assertTrue(range.isLowerInfinite());
        assertEquals(Long.valueOf(3), range.getUpperBound());
        assertFalse(range.isUpperInclusive());
        assertFalse(range.isUpperInfinite());

        assertTrue(rs.next());
        range = rs.getObject("int8range_column", PGint8range.class);
        // [4,20)
        assertTrue(range.isLowerInclusive());
        assertFalse(range.isLowerInfinite());
        assertEquals(Long.valueOf(4), range.getLowerBound());
        assertEquals(Long.valueOf(20), range.getUpperBound());
        assertFalse(range.isUpperInclusive());
        assertFalse(range.isUpperInfinite());

        assertTrue(rs.next());
        range = rs.getObject("int8range_column", PGint8range.class);
        // [50,)
        assertTrue(range.isLowerInclusive());
        assertFalse(range.isLowerInfinite());
        assertEquals(Long.valueOf(50), range.getLowerBound());
        assertFalse(range.isUpperInclusive());
        assertTrue(range.isUpperInfinite());

        assertFalse(rs.next());

        assertFalse(rs.next());
      }
    }

  }

  @Test
  public void insertInt8Range() throws SQLException {
    try (PreparedStatement insert = con.prepareStatement("INSERT INTO table1 (int8range_column) VALUES (?)")) {
      assertInt8RangeInsert(insert, new PGint8range("[1,1)"), "(,)");
      assertInt8RangeInsert(insert, new PGint8range("(,)"), "(,)");

      assertInt8RangeInsert(insert, new PGint8range("[1,)"), "[1,)");
      assertInt8RangeInsert(insert, new PGint8range("(0,]"), "[1,)");

      assertInt8RangeInsert(insert, new PGint8range("[,3)"), "(,3)");
      assertInt8RangeInsert(insert, new PGint8range("[,2]"), "(,3)");

      assertInt8RangeInsert(insert, new PGint8range("[1,2)"), "[1,2)");
      assertInt8RangeInsert(insert, new PGint8range("[1,3)"), "[1,3)");
      assertInt8RangeInsert(insert, new PGint8range("(0,2]"), "[1,3)");
      assertInt8RangeInsert(insert, new PGint8range(1L, 3L), "[1,3)");
      assertInt8RangeInsert(insert, new PGint8range(0L, false, 2L, true), "[1,3)");
    }
  }

  private void assertInt8RangeInsert(PreparedStatement insert, PGint8range int8range, String expected)
          throws SQLException {
    insert.setObject(1, int8range);
    insert.executeUpdate();


    try (Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(TestUtil.selectSQL("table1", "int8range_column"))) {
      assertTrue(rs.next());
      assertEquals(expected, rs.getObject(1, PGint8range.class).getValue());
      assertFalse(rs.next());
      stmt.executeUpdate("DELETE FROM table1");
    }
  }

  @Test
  public void selecNumRange() throws SQLException {
    try (Statement stmt = con.createStatement()) {

      stmt.executeUpdate(TestUtil.insertSQL("table1", "numrange_column", "'(,)'"));
      stmt.executeUpdate(TestUtil.insertSQL("table1", "numrange_column", "'[-9223372036854775808.8,9223372036854775807.7)'"));
      stmt.executeUpdate(TestUtil.insertSQL("table1", "numrange_column", "'(,3.3)'"));
      stmt.executeUpdate(TestUtil.insertSQL("table1", "numrange_column", "'[4.4,20.20)'"));
      stmt.executeUpdate(TestUtil.insertSQL("table1", "numrange_column", "'(4.4,20.20]'"));
      stmt.executeUpdate(TestUtil.insertSQL("table1", "numrange_column", "'[50.50,)'"));

      try (ResultSet rs = stmt.executeQuery(TestUtil.selectSQL("table1", "numrange_column", null, "numrange_column"))) {

        assertTrue(rs.next());
        PGnumrange range = rs.getObject("numrange_column", PGnumrange.class);
        // (,)
        assertFalse(range.isLowerInclusive());
        assertTrue(range.isLowerInfinite());
        assertFalse(range.isUpperInclusive());
        assertTrue(range.isUpperInfinite());

        assertTrue(rs.next());
        range = rs.getObject("numrange_column", PGnumrange.class);
        // [-9223372036854775808.8,9223372036854775807.7)
        assertTrue(range.isLowerInclusive());
        assertFalse(range.isLowerInfinite());
        assertEquals(new BigDecimal("-9223372036854775808.8"), range.getLowerBound());
        assertEquals(new BigDecimal("9223372036854775807.7"), range.getUpperBound());
        assertFalse(range.isUpperInclusive());
        assertFalse(range.isUpperInfinite());

        assertTrue(rs.next());
        range = rs.getObject("numrange_column", PGnumrange.class);
        // (,3.3)
        assertFalse(range.isLowerInclusive());
        assertTrue(range.isLowerInfinite());
        assertEquals(new BigDecimal("3.3"), range.getUpperBound());
        assertFalse(range.isUpperInclusive());
        assertFalse(range.isUpperInfinite());

        assertTrue(rs.next());
        range = rs.getObject("numrange_column", PGnumrange.class);
        // [4.4,20.20)
        assertTrue(range.isLowerInclusive());
        assertFalse(range.isLowerInfinite());
        assertEquals(new BigDecimal("4.4"), range.getLowerBound());
        assertEquals(new BigDecimal("20.20"), range.getUpperBound());
        assertFalse(range.isUpperInclusive());
        assertFalse(range.isUpperInfinite());

        assertTrue(rs.next());
        range = rs.getObject("numrange_column", PGnumrange.class);
        // (4.4,20.20]
        assertFalse(range.isLowerInclusive());
        assertFalse(range.isLowerInfinite());
        assertEquals(new BigDecimal("4.4"), range.getLowerBound());
        assertEquals(new BigDecimal("20.20"), range.getUpperBound());
        assertTrue(range.isUpperInclusive());
        assertFalse(range.isUpperInfinite());

        assertTrue(rs.next());
        range = rs.getObject("numrange_column", PGnumrange.class);
        // [50.50,)
        assertTrue(range.isLowerInclusive());
        assertFalse(range.isLowerInfinite());
        assertEquals(new BigDecimal("50.50"), range.getLowerBound());
        assertFalse(range.isUpperInclusive());
        assertTrue(range.isUpperInfinite());

        assertFalse(rs.next());

        assertFalse(rs.next());
      }
    }

  }

  @Test
  public void insertNumRange() throws SQLException {
    try (PreparedStatement insert = con.prepareStatement("INSERT INTO table1 (numrange_column) VALUES (?)")) {
      assertNumRangeInsert(insert, new PGnumrange("[1.1,1.1)"), "(,)");
      assertNumRangeInsert(insert, new PGnumrange("(,)"), "(,)");

      assertNumRangeInsert(insert, new PGnumrange("[1.1,)"), "[1.1,)");
      assertNumRangeInsert(insert, new PGnumrange("(1.1,]"), "(1.1,)");

      assertNumRangeInsert(insert, new PGnumrange("[,3.3)"), "(,3.3)");
      assertNumRangeInsert(insert, new PGnumrange("[,2.2]"), "(,2.2]");

      assertNumRangeInsert(insert, new PGnumrange("[1.1,2.2)"), "[1.1,2.2)");
      assertNumRangeInsert(insert, new PGnumrange("[1.1,3.3)"), "[1.1,3.3)");
      assertNumRangeInsert(insert, new PGnumrange("(1.1,2.2]"), "(1.1,2.2]");
      assertNumRangeInsert(insert, new PGnumrange(new BigDecimal("1.1"), new BigDecimal("3.3")), "[1.1,3.3)");
      assertNumRangeInsert(insert, new PGnumrange(new BigDecimal("1.1"), false, new BigDecimal("3.3"), true), "(1.1,3.3]");
    }
  }

  private void assertNumRangeInsert(PreparedStatement insert, PGnumrange numrange, String expected)
          throws SQLException {
    insert.setObject(1, numrange);
    insert.executeUpdate();


    try (Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(TestUtil.selectSQL("table1", "numrange_column"))) {
      assertTrue(rs.next());
      assertEquals(expected, rs.getObject(1, PGnumrange.class).getValue());
      assertFalse(rs.next());
      stmt.executeUpdate("DELETE FROM table1");
    }
  }

  @Test
  public void insert() {

  }

}
