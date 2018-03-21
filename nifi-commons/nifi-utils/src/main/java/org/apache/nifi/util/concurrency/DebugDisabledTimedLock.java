/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nifi.util.concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class DebugDisabledTimedLock implements DebuggableTimedLock {

    private final Lock lock;

    public DebugDisabledTimedLock(final Lock lock) {
        this.lock = lock;
    }

    /**
     *
     * @return true if lock obtained; false otherwise
     */
    @Override
    public boolean tryLock() {
        return lock.tryLock();
    }

    /**
     *
     * @param timeout the duration of time to wait for the lock
     * @param timeUnit the unit which provides meaning to the duration
     * @return true if obtained lock in time; false otherwise
     */
    @Override
    public boolean tryLock(final long timeout, final TimeUnit timeUnit) {
        try {
            return lock.tryLock(timeout, timeUnit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void lock() {
        lock.lock();
    }

    @Override
    public void unlock(final String task) {
        lock.unlock();
    }

}
