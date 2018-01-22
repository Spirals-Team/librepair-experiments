package py.org.fundacionparaguaya.pspserver.web.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import py.org.fundacionparaguaya.pspserver.common.dtos.ErrorDTO;

/**
 * Created by rodrigovillalba on 12/13/17.
 */
public abstract class AbstractBaseExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> generateResponseEntity(ErrorDTO errorEntity, HttpStatus status) {
        return ResponseEntity.status(status).body(errorEntity);
    }

}