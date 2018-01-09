/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.storm.utils;

import org.apache.storm.multilang.ShellMsg;
import org.apache.storm.task.TopologyContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handle output from non-JVM processes.
 */
public class DefaultShellLogHandler implements ShellLogHandler {
    public static final Logger LOG = LoggerFactory.getLogger(DefaultShellLogHandler.class);

    /**
     * Save information about the current process.
     */
    private ShellProcess process;

    /**
     * Default constructor; used when loading with
     * Class.forName(...).newInstance().
     */
    public DefaultShellLogHandler() {
    }

    /**
     * This default implementation saves the {@link ShellProcess} so it can
     * output the process info string later.
     * @see {@link ShellLogHandler#setUpContext}
     *
     * @param process
     *            - the current {@link ShellProcess}.
     * @param context
     *            - the current {@link TopologyContext}.
     */
    public void setUpContext(final ShellProcess process, final TopologyContext context) {
        this.process = process;
        // context is not used by the default implementation, but is included
        // in the interface in case it is useful to subclasses
    }

    /**
     * Log the given message.
     * @see {@link ShellLogHandler#log}
     *
     * @param shellMsg
     *            - the {@link ShellMsg} to log.
     */
    public void log(final ShellMsg shellMsg) {
        if (shellMsg == null) {
            throw new IllegalArgumentException("shellMsg is required");
        }
        String msg = shellMsg.getMsg();
        if (this.process == null) {
            msg = "ShellLog " + msg;
        } else {
            msg = "ShellLog " + process.getProcessInfoString() + " " + msg;
        }
        ShellMsg.ShellLogLevel logLevel = shellMsg.getLogLevel();

        switch (logLevel) {
            case TRACE:
                LOG.trace(msg);
                break;
            case DEBUG:
                LOG.debug(msg);
                break;
            case INFO:
                LOG.info(msg);
                break;
            case WARN:
                LOG.warn(msg);
                break;
            case ERROR:
                LOG.error(msg);
                break;
            default:
                LOG.info(msg);
                break;
        }
    }
}
