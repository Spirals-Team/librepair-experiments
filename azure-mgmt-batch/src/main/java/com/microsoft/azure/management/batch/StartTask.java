/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.batch;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A task which is run when a compute node joins a pool in the Azure Batch
 * service, or when the compute node is rebooted or reimaged.
 */
public class StartTask {
    /**
     * The command line of the start task.
     * The command line does not run under a shell, and therefore cannot take
     * advantage of shell features such as environment variable expansion. If
     * you want to take advantage of such features, you should invoke the shell
     * in the command line, for example using "cmd /c MyCommand" in Windows or
     * "/bin/sh -c MyCommand" in Linux. Required if any other properties of the
     * startTask are specified.
     */
    @JsonProperty(value = "commandLine")
    private String commandLine;

    /**
     * A list of files that the Batch service will download to the compute node
     * before running the command line.
     */
    @JsonProperty(value = "resourceFiles")
    private List<ResourceFile> resourceFiles;

    /**
     * A list of environment variable settings for the start task.
     */
    @JsonProperty(value = "environmentSettings")
    private List<EnvironmentSetting> environmentSettings;

    /**
     * The user identity under which the start task runs.
     * If omitted, the task runs as a non-administrative user unique to the
     * task.
     */
    @JsonProperty(value = "userIdentity")
    private UserIdentity userIdentity;

    /**
     * The maximum number of times the task may be retried.
     * The Batch service retries a task if its exit code is nonzero. Note that
     * this value specifically controls the number of retries. The Batch
     * service will try the task once, and may then retry up to this limit. For
     * example, if the maximum retry count is 3, Batch tries the task up to 4
     * times (one initial try and 3 retries). If the maximum retry count is 0,
     * the Batch service does not retry the task. If the maximum retry count is
     * -1, the Batch service retries the task without limit.
     */
    @JsonProperty(value = "maxTaskRetryCount")
    private Integer maxTaskRetryCount;

    /**
     * Whether the Batch service should wait for the start task to complete
     * successfully (that is, to exit with exit code 0) before scheduling any
     * tasks on the compute node.
     * If true and the start task fails on a compute node, the Batch service
     * retries the start task up to its maximum retry count
     * (maxTaskRetryCount). If the task has still not completed successfully
     * after all retries, then the Batch service marks the compute node
     * unusable, and will not schedule tasks to it. This condition can be
     * detected via the node state and scheduling error detail. If false, the
     * Batch service will not wait for the start task to complete. In this
     * case, other tasks can start executing on the compute node while the
     * start task is still running; and even if the start task fails, new tasks
     * will continue to be scheduled on the node. The default is false.
     */
    @JsonProperty(value = "waitForSuccess")
    private Boolean waitForSuccess;

    /**
     * Get the commandLine value.
     *
     * @return the commandLine value
     */
    public String commandLine() {
        return this.commandLine;
    }

    /**
     * Set the commandLine value.
     *
     * @param commandLine the commandLine value to set
     * @return the StartTask object itself.
     */
    public StartTask withCommandLine(String commandLine) {
        this.commandLine = commandLine;
        return this;
    }

    /**
     * Get the resourceFiles value.
     *
     * @return the resourceFiles value
     */
    public List<ResourceFile> resourceFiles() {
        return this.resourceFiles;
    }

    /**
     * Set the resourceFiles value.
     *
     * @param resourceFiles the resourceFiles value to set
     * @return the StartTask object itself.
     */
    public StartTask withResourceFiles(List<ResourceFile> resourceFiles) {
        this.resourceFiles = resourceFiles;
        return this;
    }

    /**
     * Get the environmentSettings value.
     *
     * @return the environmentSettings value
     */
    public List<EnvironmentSetting> environmentSettings() {
        return this.environmentSettings;
    }

    /**
     * Set the environmentSettings value.
     *
     * @param environmentSettings the environmentSettings value to set
     * @return the StartTask object itself.
     */
    public StartTask withEnvironmentSettings(List<EnvironmentSetting> environmentSettings) {
        this.environmentSettings = environmentSettings;
        return this;
    }

    /**
     * Get the userIdentity value.
     *
     * @return the userIdentity value
     */
    public UserIdentity userIdentity() {
        return this.userIdentity;
    }

    /**
     * Set the userIdentity value.
     *
     * @param userIdentity the userIdentity value to set
     * @return the StartTask object itself.
     */
    public StartTask withUserIdentity(UserIdentity userIdentity) {
        this.userIdentity = userIdentity;
        return this;
    }

    /**
     * Get the maxTaskRetryCount value.
     *
     * @return the maxTaskRetryCount value
     */
    public Integer maxTaskRetryCount() {
        return this.maxTaskRetryCount;
    }

    /**
     * Set the maxTaskRetryCount value.
     *
     * @param maxTaskRetryCount the maxTaskRetryCount value to set
     * @return the StartTask object itself.
     */
    public StartTask withMaxTaskRetryCount(Integer maxTaskRetryCount) {
        this.maxTaskRetryCount = maxTaskRetryCount;
        return this;
    }

    /**
     * Get the waitForSuccess value.
     *
     * @return the waitForSuccess value
     */
    public Boolean waitForSuccess() {
        return this.waitForSuccess;
    }

    /**
     * Set the waitForSuccess value.
     *
     * @param waitForSuccess the waitForSuccess value to set
     * @return the StartTask object itself.
     */
    public StartTask withWaitForSuccess(Boolean waitForSuccess) {
        this.waitForSuccess = waitForSuccess;
        return this;
    }

}
