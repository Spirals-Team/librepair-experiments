/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.appservice.implementation;

import com.microsoft.azure.management.appservice.TriggeredJobRun;
import com.microsoft.azure.management.appservice.WebJobType;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;
import com.microsoft.azure.management.appservice.ProxyOnlyResource;

/**
 * Triggered Web Job Information.
 */
@JsonFlatten
public class TriggeredWebJobInner extends ProxyOnlyResource {
    /**
     * Latest job run information.
     */
    @JsonProperty(value = "properties.latestRun")
    private TriggeredJobRun latestRun;

    /**
     * History URL.
     */
    @JsonProperty(value = "properties.historyUrl")
    private String historyUrl;

    /**
     * Scheduler Logs URL.
     */
    @JsonProperty(value = "properties.schedulerLogsUrl")
    private String schedulerLogsUrl;

    /**
     * Job name. Used as job identifier in ARM resource URI.
     */
    @JsonProperty(value = "properties.name", access = JsonProperty.Access.WRITE_ONLY)
    private String triggeredWebJobName;

    /**
     * Run command.
     */
    @JsonProperty(value = "properties.runCommand")
    private String runCommand;

    /**
     * Job URL.
     */
    @JsonProperty(value = "properties.url")
    private String url;

    /**
     * Extra Info URL.
     */
    @JsonProperty(value = "properties.extraInfoUrl")
    private String extraInfoUrl;

    /**
     * Job type. Possible values include: 'Continuous', 'Triggered'.
     */
    @JsonProperty(value = "properties.jobType")
    private WebJobType jobType;

    /**
     * Error information.
     */
    @JsonProperty(value = "properties.error")
    private String error;

    /**
     * Using SDK?.
     */
    @JsonProperty(value = "properties.usingSdk")
    private Boolean usingSdk;

    /**
     * Job settings.
     */
    @JsonProperty(value = "properties.settings")
    private Map<String, Object> settings;

    /**
     * Get the latestRun value.
     *
     * @return the latestRun value
     */
    public TriggeredJobRun latestRun() {
        return this.latestRun;
    }

    /**
     * Set the latestRun value.
     *
     * @param latestRun the latestRun value to set
     * @return the TriggeredWebJobInner object itself.
     */
    public TriggeredWebJobInner withLatestRun(TriggeredJobRun latestRun) {
        this.latestRun = latestRun;
        return this;
    }

    /**
     * Get the historyUrl value.
     *
     * @return the historyUrl value
     */
    public String historyUrl() {
        return this.historyUrl;
    }

    /**
     * Set the historyUrl value.
     *
     * @param historyUrl the historyUrl value to set
     * @return the TriggeredWebJobInner object itself.
     */
    public TriggeredWebJobInner withHistoryUrl(String historyUrl) {
        this.historyUrl = historyUrl;
        return this;
    }

    /**
     * Get the schedulerLogsUrl value.
     *
     * @return the schedulerLogsUrl value
     */
    public String schedulerLogsUrl() {
        return this.schedulerLogsUrl;
    }

    /**
     * Set the schedulerLogsUrl value.
     *
     * @param schedulerLogsUrl the schedulerLogsUrl value to set
     * @return the TriggeredWebJobInner object itself.
     */
    public TriggeredWebJobInner withSchedulerLogsUrl(String schedulerLogsUrl) {
        this.schedulerLogsUrl = schedulerLogsUrl;
        return this;
    }

    /**
     * Get the triggeredWebJobName value.
     *
     * @return the triggeredWebJobName value
     */
    public String triggeredWebJobName() {
        return this.triggeredWebJobName;
    }

    /**
     * Get the runCommand value.
     *
     * @return the runCommand value
     */
    public String runCommand() {
        return this.runCommand;
    }

    /**
     * Set the runCommand value.
     *
     * @param runCommand the runCommand value to set
     * @return the TriggeredWebJobInner object itself.
     */
    public TriggeredWebJobInner withRunCommand(String runCommand) {
        this.runCommand = runCommand;
        return this;
    }

    /**
     * Get the url value.
     *
     * @return the url value
     */
    public String url() {
        return this.url;
    }

    /**
     * Set the url value.
     *
     * @param url the url value to set
     * @return the TriggeredWebJobInner object itself.
     */
    public TriggeredWebJobInner withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * Get the extraInfoUrl value.
     *
     * @return the extraInfoUrl value
     */
    public String extraInfoUrl() {
        return this.extraInfoUrl;
    }

    /**
     * Set the extraInfoUrl value.
     *
     * @param extraInfoUrl the extraInfoUrl value to set
     * @return the TriggeredWebJobInner object itself.
     */
    public TriggeredWebJobInner withExtraInfoUrl(String extraInfoUrl) {
        this.extraInfoUrl = extraInfoUrl;
        return this;
    }

    /**
     * Get the jobType value.
     *
     * @return the jobType value
     */
    public WebJobType jobType() {
        return this.jobType;
    }

    /**
     * Set the jobType value.
     *
     * @param jobType the jobType value to set
     * @return the TriggeredWebJobInner object itself.
     */
    public TriggeredWebJobInner withJobType(WebJobType jobType) {
        this.jobType = jobType;
        return this;
    }

    /**
     * Get the error value.
     *
     * @return the error value
     */
    public String error() {
        return this.error;
    }

    /**
     * Set the error value.
     *
     * @param error the error value to set
     * @return the TriggeredWebJobInner object itself.
     */
    public TriggeredWebJobInner withError(String error) {
        this.error = error;
        return this;
    }

    /**
     * Get the usingSdk value.
     *
     * @return the usingSdk value
     */
    public Boolean usingSdk() {
        return this.usingSdk;
    }

    /**
     * Set the usingSdk value.
     *
     * @param usingSdk the usingSdk value to set
     * @return the TriggeredWebJobInner object itself.
     */
    public TriggeredWebJobInner withUsingSdk(Boolean usingSdk) {
        this.usingSdk = usingSdk;
        return this;
    }

    /**
     * Get the settings value.
     *
     * @return the settings value
     */
    public Map<String, Object> settings() {
        return this.settings;
    }

    /**
     * Set the settings value.
     *
     * @param settings the settings value to set
     * @return the TriggeredWebJobInner object itself.
     */
    public TriggeredWebJobInner withSettings(Map<String, Object> settings) {
        this.settings = settings;
        return this;
    }

}
