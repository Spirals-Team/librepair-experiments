/*
   Copyright 2018 Open School Management
   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
       http://www.apache.org/licenses/LICENSE-2.0
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */

package open.schoolmanagement.time.calendartaskprojectservice.exception;

import java.util.Date;


/**
 * The type Exception response.
 */
public class ExceptionResponse {
  private Date timestamp;
  private String message;
  private String details;

  /**
   * ExceptionResponse: convert the Exception into a externalised form.
   *
   * @param timestamp Timestamp when the exception happend
   * @param message   What went wrong!
   * @param details   Details of the error
   */
  public ExceptionResponse(Date timestamp, String message, String details) {
    super();
    this.timestamp = timestamp;
    this.message = message;
    this.details = details;
  }

  /**
   * Gets timestamp.
   *
   * @return the timestamp
   */
  public Date getTimestamp() {
    return timestamp;
  }

  /**
   * Gets message.
   *
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * Gets details.
   *
   * @return the details
   */
  public String getDetails() {
    return details;
  }
}
