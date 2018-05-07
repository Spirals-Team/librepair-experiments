/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.cloud.tools.managedcloudsdk.command;

/** Exception when sdk command fails. */
public class CommandExitException extends Exception {
  private final int exitCode;
  private final String errLog;

  /**
   * Create a new exception.
   *
   * @param exitCode the process exit code
   * @param errLog additional loggable error information, can be {@code null}
   */
  public CommandExitException(int exitCode, String errLog) {
    super("Process failed with exit code: " + exitCode);
    this.exitCode = exitCode;
    this.errLog = errLog;
  }

  public int getExitCode() {
    return exitCode;
  }

  public String getErrorLog() {
    return errLog;
  }
}
