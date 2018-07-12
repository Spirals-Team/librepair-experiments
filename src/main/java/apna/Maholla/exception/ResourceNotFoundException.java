package apna.Maholla.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException implements ResourceFoundNotFound {
    private String resourceName;
    private String fieldName;
    private String fieldValue;
    private String status;
    private String message;

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue, String status, String message) {
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.status = status;
        this.message = message;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
