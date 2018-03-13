package org.w4tracking.models.transaction;

public interface W4TrackingTransaction {

    void begin();

    void commit();

    void rollback();

    void setRollbackOnly();

    boolean isActive();
}
