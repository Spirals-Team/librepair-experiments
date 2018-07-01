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
import open.schoolmanagement.time.calendartaskprojectservice.exception.calendar.AppointmentCreationFailedException;
import open.schoolmanagement.time.calendartaskprojectservice.exception.calendar.MeetingCreationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The type Customized response entity exception handler.
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


  /**
   * ExceptionHandler for general exception.
   *
   * @param ex      The Exception
   * @param request The failed Request
   * @return Returing an exception ExceptionResponse
   */
  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex,
                                                                     WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<ExceptionResponse>(exceptionResponse,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Handler for MeetingCreationFailed.
   *
   * @param ex      MeetingCreationFailedException
   * @param request WebRequest
   * @return ExceptionResponse response entity
   */
  @ExceptionHandler(MeetingCreationFailedException.class)
  public final ResponseEntity<ExceptionResponse> handleMeetingCreationFailed(
       MeetingCreationFailedException ex, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  /**
   * Handler for Appointment Creation Failed.
   *
   * @param ex      the ex
   * @param request the request
   * @return response entity
   */
  @ExceptionHandler(AppointmentCreationFailedException.class)
  public final ResponseEntity<ExceptionResponse> handleUserNotFoundException(
      AppointmentCreationFailedException ex, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }
}
