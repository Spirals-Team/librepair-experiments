package com.hubspot.singularity.executor.models;

import com.google.common.base.Optional;

/**
 * Handlebars context for generating the runner.sh file.
 */
public class RunnerContext {

  private final String cmd;
  private final String taskAppDirectory;
  private final String logDir;
  private final String user;
  private final String logFile;
  private final String logFilePath;
  private final String taskId;

  private final Optional<Integer> maxTaskThreads;
  private final boolean shouldChangeUser;
  private final Integer maxOpenFiles;
  private final String switchUserCommand;
  private final boolean useFileAttributes;
  private final Integer cfsQuota;
  private final Integer cfsPeriod;

  public RunnerContext(String cmd,
                       String taskAppDirectory,
                       String logDir,
                       String user,
                       String logFile,
                       String logFilePath,
                       String taskId,
                       Optional<Integer> maxTaskThreads,
                       boolean shouldChangeUser,
                       Integer maxOpenFiles,
                       String switchUserCommand,
                       boolean useFileAttributes,
                       Integer cfsQuota,
                       Integer cfsPeriod) {
    this.cmd = cmd;
    this.taskAppDirectory = taskAppDirectory;
    this.logDir = logDir;
    this.user = user;
    this.logFile = logFile;
    this.logFilePath = logFilePath;
    this.taskId = taskId;

    this.maxTaskThreads = maxTaskThreads;
    this.shouldChangeUser = shouldChangeUser;
    this.maxOpenFiles = maxOpenFiles;
    this.switchUserCommand = switchUserCommand;
    this.useFileAttributes = useFileAttributes;
    this.cfsQuota = cfsQuota;
    this.cfsPeriod = cfsPeriod;
  }

  public String getCmd() {
    return cmd;
  }

  public String getTaskAppDirectory() {
    return taskAppDirectory;
  }

  public String getLogDir() {
    return logDir;
  }

  public String getUser() {
    return user;
  }

  public String getLogFile() {
    return logFile;
  }

  public String getLogFilePath() {
    return logFilePath;
  }

  public String getTaskId() {
    return taskId;
  }

  public Optional<Integer> getMaxTaskThreads() {
    return maxTaskThreads;
  }

  public boolean isShouldChangeUser() {
    return shouldChangeUser;
  }

  public Integer getMaxOpenFiles() {
    return maxOpenFiles;
  }

  public String getSwitchUserCommand() {
    return switchUserCommand;
  }

  public boolean isUseFileAttributes() {
    return useFileAttributes;
  }

  public Integer getCfsQuota() {
    return cfsQuota;
  }

  public Integer getCfsPeriod() {
    return cfsPeriod;
  }

  @Override
  public String toString() {
    return "RunnerContext{" +
        "cmd='" + cmd + '\'' +
        ", taskAppDirectory='" + taskAppDirectory + '\'' +
        ", logDir='" + logDir + '\'' +
        ", user='" + user + '\'' +
        ", logFile='" + logFile + '\'' +
        ", logFilePath='" + logFilePath + '\'' +
        ", taskId='" + taskId + '\'' +
        ", maxTaskThreads=" + maxTaskThreads +
        ", shouldChangeUser=" + shouldChangeUser +
        ", maxOpenFiles=" + maxOpenFiles +
        ", switchUserCommand='" + switchUserCommand + '\'' +
        ", useFileAttributes=" + useFileAttributes +
        ", cfsQuota=" + cfsQuota +
        ", cfsPeriod=" + cfsPeriod +
        '}';
  }
}
