/*
 * Copyright 2018 The Hekate Project
 *
 * The Hekate Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.hekate.core;

import io.hekate.core.internal.util.ErrorUtils;
import java.util.concurrent.ExecutionException;

/**
 * Asynchronous execution error.
 */
public class HekateFutureException extends ExecutionException {
    private static final long serialVersionUID = 1;

    /**
     * Constructs new instance with the specified error message and cause.
     *
     * @param message Error message.
     * @param cause Cause.
     */
    public HekateFutureException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Returns {@code true} if this exception is caused by an error of the specified type.
     *
     * @param type Error type.
     *
     * @return {@code true} if this exception is caused by an error of the specified type.
     */
    public boolean isCausedBy(Class<? extends Throwable> type) {
        return ErrorUtils.isCausedBy(type, this);
    }

    /**
     * Searches for an error cause of the specified type. Returns {@code null} if there is no such error.
     *
     * @param type Error type.
     * @param <T> Error type.
     *
     * @return Error or {@code null}.
     */
    public <T extends Throwable> T findCause(Class<T> type) {
        return ErrorUtils.findCause(type, this);
    }
}
