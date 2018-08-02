/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.jackrabbit.oak.plugins.document;

import org.apache.jackrabbit.oak.spi.gc.LoggingGCMonitor;
import org.slf4j.Logger;

/**
 * A variant of a logging GCMonitor that emits debug log messages for
 * {@link #info(String, Object...)} calls.
 */
class QuietGCMonitor extends LoggingGCMonitor {

    private final Logger log;

    QuietGCMonitor(Logger log) {
        super(log);
        this.log = log;
    }

    @Override
    public void info(String message, Object... arguments) {
        log.debug(message, arguments);
    }
}
