package org.postgresql.core;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class ReturningParserTest {
  private final String columnName;
  private final String colPrefix;
  private final String colSuffix;
  private final String returning;
  private final String prefix;
  private final String suffix;

  public ReturningParserTest(String columnName, String colPrefix, String colSuffix,
      String returning, String prefix, String suffix) {
    this.columnName = columnName;
    this.colPrefix = colPrefix;
    this.colSuffix = colSuffix;
    this.returning = returning;
    this.prefix = prefix;
    this.suffix = suffix;
  }


  @Parameterized.Parameters(name = "columnName={1} {0} {2}, returning={4} {3} {5}")
  public static Iterable<Object[]> data() {
    Collection<Object[]> ids = new ArrayList<Object[]>();

    String[] delimiters = {"", "_", "3", "*", " "};

    for (String colPrefix : delimiters) {
      for (String colSuffix : delimiters) {
        for (String columnName : new String[]{"returning", "returningreturning"}) {
          for (String prefix : delimiters) {
            for (String suffix : delimiters) {
              for (String returning : new String[]{"returning", "returningreturning"}) {
                ids.add(new Object[]{columnName, colPrefix, colSuffix, returning, prefix, suffix});
              }
            }
          }
        }
      }
    }
    return ids;
  }

  @Test
  public void test() throws SQLException {
    String query =
        "insert into\"prep\"(a, " + colPrefix + columnName + colSuffix + ")values(1,2)" + prefix
            + returning + suffix;
    List<NativeQuery> qry =
        Parser.parseJdbcSql(
            query, true, true, true, true, new String[0]);
    boolean returningKeywordPresent = qry.get(0).command.isReturningKeywordPresent();

    boolean expectedReturning = this.returning.equalsIgnoreCase("returning")
        && (prefix.length() == 0 || !Character.isJavaIdentifierStart(prefix.charAt(0)))
        && (suffix.length() == 0 || !Character.isJavaIdentifierPart(suffix.charAt(0)));
    if (expectedReturning != returningKeywordPresent) {
      Assert.assertEquals("Wrong <returning_clause> detected in SQL " + query, expectedReturning
          , returningKeywordPresent);
    }
  }

}
