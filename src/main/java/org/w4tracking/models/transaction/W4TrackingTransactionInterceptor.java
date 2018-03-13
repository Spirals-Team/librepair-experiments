package org.w4tracking.models.transaction;

import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.transaction.Transactional;

@Interceptor
@Transactional
@W4TrackingTransactional
public class W4TrackingTransactionInterceptor {

    private static final Logger logger = Logger.getLogger(W4TrackingTransactionInterceptor.class);

    @Inject
    private W4TrackingTransaction transaction;

    @AroundInvoke
    public Object manageTransaction(InvocationContext ctx) {
        Object obj = null;
        boolean isFirstTransaction = false;

        try {
            if (!transaction.isActive()) {
                logger.debug("Starting new transaction...");
                transaction.begin();
                isFirstTransaction = true;
            }

            obj = ctx.proceed();

            if (transaction.isActive() && isFirstTransaction) {
                logger.debug("Finishing transaction...");
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction.isActive() && isFirstTransaction) {
                transaction.rollback();
                throw new RuntimeException("Transaction rolled back!", e);
            }
        }

        return obj;
    }
}