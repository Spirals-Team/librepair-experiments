package org.w4tracking.models.jpa.entities;

import org.w4tracking.models.transaction.W4TrackingTransaction;

import javax.annotation.Resource;
import javax.enterprise.context.RequestScoped;
import javax.transaction.*;

@RequestScoped
public class JpaW4TrackingTransaction implements W4TrackingTransaction {

    @Resource
    private UserTransaction utx;

    @Override
    public void begin() {
        try {
            utx.begin();
        } catch (NotSupportedException | SystemException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void commit() {
        try {
            utx.commit();
        } catch (RollbackException | SystemException | HeuristicRollbackException | HeuristicMixedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void rollback() {
        try {
            utx.rollback();
        } catch (SystemException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setRollbackOnly() {
        try {
            utx.setRollbackOnly();
        } catch (SystemException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isActive() {
        try {
            return utx.getStatus() == 0;
        } catch (SystemException e) {
            throw new RuntimeException(e);
        }
    }

//    STATUS_ACTIVE               0
//    STATUS_COMMITTED            3
//    STATUS_COMMITTING           8
//    STATUS_MARKED_ROLLBACK      1
//    STATUS_NO_TRANSACTION       6
//    STATUS_PREPARED             2
//    STATUS_PREPARING            7
//    STATUS_ROLLEDBACK           4
//    STATUS_ROLLING_BACK         9
//    STATUS_UNKNOWN              5

}
