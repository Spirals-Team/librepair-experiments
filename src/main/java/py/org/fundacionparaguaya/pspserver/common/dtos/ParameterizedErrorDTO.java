package py.org.fundacionparaguaya.pspserver.common.dtos;

import java.io.Serializable;
import java.util.Map;

/**
 * View Model for sending a parameterized error message.
 */
public class ParameterizedErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String message;

    private final String description;

    private final Map<String, String> paramMap;

    public ParameterizedErrorDTO(String message, String description, Map<String, String> paramMap) {
        this.message = message;
        this.paramMap = paramMap;
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getParams() {
        return paramMap;
    }

    public String getDescription() {
        return description;
    }
}
