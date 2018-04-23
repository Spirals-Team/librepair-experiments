package com.epam.brest.course.rest.errorhandling;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Locale;

/**
 * error handler.
 */
@ControllerAdvice
public class RestErrorHandler {
    /**
     * logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * @param error error.
     * @return message and type.
     */
    private MessageDTO processFieldError(final FieldError error) {
        MessageDTO message = null;
        if (error != null) {
            Locale currentLocale = LocaleContextHolder.getLocale();
            String msg = msgSource.getMessage(error.getCode(),
                    error.getCodes(),

                    null, currentLocale);
            message = new MessageDTO(MessageType.ERROR, msg);
        }
        return message;
    }

    /**
     * @param e exception.
     * @return string and local message.
     */
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public final String handleDataAccessException(
                                                @RequestBody final DataAccessException e) {
        LOGGER.debug("handleDataAccessException({})", e);

        return "DataAccessException: " + e.getLocalizedMessage();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler( DataIntegrityViolationException.class)
    public void handleConflict() {

    }

        /**
         * @param e exception.
         * @return string and local message.
         */
    @ExceptionHandler(ParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final String handleParseException(
                                                    final ParseException e) {

        LOGGER.debug("handleIllegalArgumentException({})", e);
        return "Parse-Exception: " + e.getLocalizedMessage();
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) //400
    public final void illegalStateHandler( ) {
    }


    /**
     * message source.
     */
    @Autowired
    private MessageSource msgSource;

    /**
     *
     * @param ex x.
     * @return message.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final MessageDTO handleMethodArgumentNotValidException (
                          final  MethodArgumentNotValidException ex) {
        LOGGER.debug("processValidationError({})", ex);

        BindingResult result = ex.getBindingResult();
        FieldError error = result.getFieldError();
        return processFieldError(error);
    }

    /**
     * @param ex x.
     * @return message.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final String alreadyExistsException(final IllegalArgumentException ex) {
        LOGGER.debug("processValidationError({})", ex);

        return "IllegalStateException: " + ex.getMessage();
    }

}
