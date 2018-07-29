package io.zero.quiz.fakecluster;

import io.vertx.core.*;
import io.vertx.core.shareddata.Lock;

import java.util.LinkedList;
import java.util.Queue;

public class AsynchronousLock implements Lock {
    private final Vertx vertx;
    private final Queue<AsynchronousLock.LockWaiter> waiters = new LinkedList<>();
    private boolean owned;

    public AsynchronousLock(final Vertx vertx) {
        this.vertx = vertx;
    }

    public void acquire(final long timeout, final Handler<AsyncResult<Lock>> resultHandler) {
        final Context context = this.vertx.getOrCreateContext();
        this.doAcquire(context, timeout, resultHandler);
    }

    @Override
    public synchronized void release() {
        final AsynchronousLock.LockWaiter waiter = this.pollWaiters();
        if (waiter != null) {
            waiter.acquire(this);
        } else {
            this.owned = false;
        }

    }

    public void doAcquire(final Context context, final long timeout, final Handler<AsyncResult<Lock>> resultHandler) {
        synchronized (this) {
            if (!this.owned) {
                this.owned = true;
                this.lockAcquired(context, resultHandler);
            } else {
                this.waiters.add(new AsynchronousLock.LockWaiter(this, context, timeout, resultHandler));
            }

        }
    }

    private void lockAcquired(final Context context, final Handler<AsyncResult<Lock>> resultHandler) {
        context.runOnContext((v) -> {
            resultHandler.handle(Future.succeededFuture(this));
        });
    }

    private LockWaiter pollWaiters() {
        LockWaiter waiter;
        do {
            waiter = (LockWaiter) this.waiters.poll();
            if (waiter == null) {
                return null;
            }
        } while (waiter.timedOut);

        return waiter;
    }

    private static class LockWaiter {
        final AsynchronousLock lock;
        final Context context;
        final Handler<AsyncResult<Lock>> resultHandler;
        volatile boolean timedOut;
        volatile boolean acquired;

        LockWaiter(final AsynchronousLock lock, final Context context, final long timeout, final Handler<AsyncResult<Lock>> resultHandler) {
            this.lock = lock;
            this.context = context;
            this.resultHandler = resultHandler;
            if (timeout != 9223372036854775807L) {
                context.owner().setTimer(timeout, (tid) -> {
                    this.timedOut();
                });
            }

        }

        void timedOut() {
            final AsynchronousLock var1 = this.lock;
            synchronized (this.lock) {
                if (!this.acquired) {
                    this.timedOut = true;
                    this.context.runOnContext((v) -> {
                        this.resultHandler.handle(Future.failedFuture(new VertxException("Timed out waiting to getNull lock")));
                    });
                }

            }
        }

        void acquire(final AsynchronousLock lock) {
            this.acquired = true;
            lock.lockAcquired(this.context, this.resultHandler);
        }
    }
}
