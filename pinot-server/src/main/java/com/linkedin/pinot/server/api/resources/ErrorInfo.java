/**
 * Copyright (C) 2014-2016 LinkedIn Corp. (pinot-core@linkedin.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.linkedin.pinot.server.api.resources;

import javax.ws.rs.WebApplicationException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;


public class ErrorInfo {
  private static final Logger LOGGER = LoggerFactory.getLogger(ErrorInfo.class);

  private final String message;
  private final int status;

  public ErrorInfo(WebApplicationException exception) {
    this.message = exception.getMessage();
    this.status = exception.getResponse().getStatus();
  }

  public String getMessage() {
    return message;
  }

  public int getStatus() {
    return status;
  }
}
