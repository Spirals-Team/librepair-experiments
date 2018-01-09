package org.apache.storm.utils;

import org.apache.storm.multilang.ShellMsg;
import org.apache.storm.task.TopologyContext;

/**
 * Handles output from multilang processes.
 */
public interface ShellLogHandler {

    /**
     * Called at least once before {@link ShellLogHandler#log} for each
     * spout and bolt. Allows implementing classes to save information about
     * the current running context e.g. pid, thread, task.
     *
     * @param process
     *            - the current {@link ShellProcess}.
     * @param context
     *            - the current {@link TopologyContext}.
     */
    void setUpContext(ShellProcess process, TopologyContext context);

    /**
     * Called by spouts and bolts when they receive a 'log' command from a
     * multilang process.
     *
     * @param msg
     *            - the {@link ShellMsg} containing the message to log.
     */
    void log(ShellMsg msg);
}
