package io.ebeaninternal.server.core;

import io.ebeaninternal.api.SpiEbeanServer;
import io.ebeaninternal.api.SpiTransaction;
import io.ebeaninternal.api.TxnProfileEventCodes;
import io.ebeaninternal.server.persist.BatchControl;
import io.ebeaninternal.server.persist.BatchPostExecute;
import io.ebeaninternal.server.persist.BatchedSqlException;
import io.ebeaninternal.server.persist.PersistExecute;

/**
 * Wraps all the objects used to persist a bean.
 */
public abstract class PersistRequest extends BeanRequest implements BatchPostExecute, TxnProfileEventCodes {

  public enum Type {
    INSERT(EVT_INSERT),
    UPDATE(EVT_UPDATE),
    DELETE(EVT_DELETE),
    SOFT_DELETE(EVT_SOFT_DELETE),
    DELETE_PERMANENT(EVT_DELETE_PERMANENT),
    UPDATESQL(EVT_UPDATESQL),
    CALLABLESQL(EVT_CALLABLESQL);
    String profileEventId;
    Type(String profileEventId) {
      this.profileEventId = profileEventId;
    }
  }

  boolean persistCascade;

  /**
   * One of INSERT, UPDATE, DELETE, UPDATESQL or CALLABLESQL.
   */
  protected Type type;

  final PersistExecute persistExecute;

  /**
   * Used by CallableSqlRequest and UpdateSqlRequest.
   */
  public PersistRequest(SpiEbeanServer server, SpiTransaction t, PersistExecute persistExecute) {
    super(server, t);
    this.persistExecute = persistExecute;
  }

  /**
   * Execute a the request or queue/batch it for later execution.
   */
  public abstract int executeOrQueue();

  /**
   * Execute the request right now.
   */
  public abstract int executeNow();

  void profileBase(String event, long offset, short beanTypeId, int beanCount) {
    transaction.profileStream().addPersistEvent(event, offset, beanTypeId, beanCount);
  }

  @Override
  public boolean isLogSql() {
    return transaction.isLogSql();
  }

  @Override
  public boolean isLogSummary() {
    return transaction.isLogSummary();
  }


  /**
   * Return true if this persist request should use JDBC batch.
   */
  public boolean isBatchThisRequest() {
    return transaction.isBatchThisRequest(type);
  }

  /**
   * Execute the statement.
   */
  int executeStatement() {

    boolean batch = isBatchThisRequest();

    try {
      int rows;
      BatchControl control = transaction.getBatchControl();
      if (control != null) {
        rows = control.executeStatementOrBatch(this, batch);

      } else if (batch) {
        // need to create the BatchControl
        control = persistExecute.createBatchControl(transaction);
        rows = control.executeStatementOrBatch(this, true);
      } else {
        rows = executeNow();
      }
      return rows;
    } catch (BatchedSqlException e) {
      throw transaction.translate(e.getMessage(), e.getCause());
    }
  }

  public void initTransIfRequired() {
    createImplicitTransIfRequired();
    persistCascade = transaction.isPersistCascade();
  }

  /**
   * Return the type of this request. One of INSERT, UPDATE, DELETE, UPDATESQL
   * or CALLABLESQL.
   */
  public Type getType() {
    return type;
  }

  /**
   * Return true if save and delete should cascade.
   */
  public boolean isPersistCascade() {
    return persistCascade;
  }

}
