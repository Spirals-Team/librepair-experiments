package io.ebeaninternal.server.persist;

import io.ebeaninternal.api.SpiTransaction;
import io.ebeaninternal.api.SpiProfileTransactionEvent;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * A batched statement that is held in BatchedPstmtHolder. It has a list of
 * BatchPostExecute which it will process after the statement is executed.
 * <p>
 * This can hold CallableStatements as well.
 * </p>
 */
public class BatchedPstmt implements SpiProfileTransactionEvent {

  /**
   * The underlying statement.
   */
  private PreparedStatement pstmt;

  /**
   * True if an insert that uses generated keys.
   */
  private final boolean isGenKeys;

  /**
   * The list of BatchPostExecute used to perform post processing.
   */
  private final ArrayList<BatchPostExecute> list = new ArrayList<>();

  private final String sql;

  private final SpiTransaction transaction;

  private long profileStart;

  /**
   * Create with a given statement.
   */
  public BatchedPstmt(PreparedStatement pstmt, boolean isGenKeys, String sql, SpiTransaction transaction) {
    this.pstmt = pstmt;
    this.isGenKeys = isGenKeys;
    this.sql = sql;
    this.transaction = transaction;
  }

  /**
   * Return the number of batched statements.
   */
  public int size() {
    return list.size();
  }

  /**
   * Return the sql
   */
  public String getSql() {
    return sql;
  }

  /**
   * Return the statement.
   */
  public PreparedStatement getStatement() {
    return pstmt;
  }

  /**
   * Add the BatchPostExecute to the list for post execute processing.
   */
  public void add(BatchPostExecute batchExecute) {
    list.add(batchExecute);
  }

  /**
   * Execute the statement using executeBatch().
   * Run any post processing including getGeneratedKeys.
   */
  public void executeBatch(boolean getGeneratedKeys) throws SQLException {

    this.profileStart = transaction.profileOffset();
    executeAndCheckRowCounts();
    if (isGenKeys && getGeneratedKeys) {
      getGeneratedKeys();
    }
    postExecute();
    close();
    transaction.profileEvent(this);
  }

  @Override
  public void profile() {
    // just use the first to add the event
    list.get(0).profile(profileStart, list.size());
  }

  /**
   * Close the underlying statement.
   */
  public void close() throws SQLException {
    if (pstmt != null) {
      pstmt.close();
      pstmt = null;
    }
  }

  private void postExecute() {
    for (BatchPostExecute item : list) {
      item.postExecute();
    }
  }

  private void executeAndCheckRowCounts() throws SQLException {

    int[] results = pstmt.executeBatch();
    if (results.length != list.size()) {
      String s = "results array error " + results.length + " " + list.size();
      throw new SQLException(s);
    }

    // check for concurrency exceptions...
    for (int i = 0; i < results.length; i++) {
      list.get(i).checkRowCount(results[i]);
    }
  }

  private void getGeneratedKeys() throws SQLException {

    int index = 0;
    try (ResultSet rset = pstmt.getGeneratedKeys()) {
      while (rset.next()) {
        Object idValue = rset.getObject(1);
        list.get(index).setGeneratedKey(idValue);
        index++;
      }
    }
  }

}
