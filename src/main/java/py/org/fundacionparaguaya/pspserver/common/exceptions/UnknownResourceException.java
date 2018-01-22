package py.org.fundacionparaguaya.pspserver.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by rodrigovillalba on 8/27/17.
 *
 * Simulated business-logic exception indicating a desired business entity or record cannot be found.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UnknownResourceException extends RuntimeException {

    public UnknownResourceException(String msg) {
        super(msg);
    }
}