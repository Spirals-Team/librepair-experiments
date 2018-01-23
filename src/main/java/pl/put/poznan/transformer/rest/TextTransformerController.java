package pl.put.poznan.transformer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.TextTransformer;
import pl.put.poznan.transformer.logic.transform.TransformTypes;
import pl.put.poznan.transformer.rest.dto.ListResponse;
import pl.put.poznan.transformer.rest.dto.Request;
import pl.put.poznan.transformer.rest.dto.StringResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;


@RestController
public class TextTransformerController {

    private static final Logger logger = LoggerFactory.getLogger(TextTransformerController.class);

    @RequestMapping(value = "/transforms", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ListResponse getTransforms() {
        return new ListResponse(HttpStatus.OK.value(), Arrays.asList(TransformTypes.values()).stream().map(trans -> trans.toString()).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/text/{text}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    StringResponse get(
            @PathVariable String text,
            @RequestParam(value = "transforms", defaultValue = "upper,escape") String[] transforms) {

        logger.debug(text);
        logger.debug(Arrays.toString(transforms));

        TextTransformer transformer = new TextTransformer(transforms);
        return new StringResponse(HttpStatus.OK.value(), transformer.transform(text));
    }

    @RequestMapping(value = "/text", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public @ResponseBody
    StringResponse post(@RequestBody Request request) {

        //Poor not-null validation
        if (request.getText() == null) {
            throw new IllegalArgumentException("Field 'text' cannot be null.");
        }
        if (request.getTransforms() == null) {
            throw new IllegalArgumentException("Field 'transforms' cannot be null.");
        } else {
            for (String transform : request.getTransforms()) {
                if (transform == null)
                    throw new IllegalArgumentException("Values of field 'transforms' cannot be null");
            }
        }
        logger.debug(request.getText());
        logger.debug(Arrays.toString(request.getTransforms()));

        TextTransformer transformer = new TextTransformer(request.getTransforms());
        return new StringResponse(HttpStatus.OK.value(), transformer.transform(request.getText()));
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(IllegalArgumentException.class)
    public @ResponseBody
    StringResponse illegalArgumentExceptionHandler(HttpServletRequest req, Exception ex) {
        logger.warn(Arrays.toString(ex.getStackTrace()));
        return new StringResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public @ResponseBody
    StringResponse httpMessageNotReadableExceptionHandler(HttpServletRequest req, Exception ex) {
        logger.warn(Arrays.toString(ex.getStackTrace()));
        return new StringResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody
    StringResponse exceptionHandler(HttpServletRequest req, Exception ex) {
        logger.error(Arrays.toString(ex.getStackTrace()));
        return new StringResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
}