package me.pagar.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PagarMeError {

    private String parameterName;

    private String type;

    private String message;

    private static String safeValue(JsonElement value) {
        return (null == value || value.isJsonNull() ? null : value.getAsString());
    }

    public PagarMeError(JsonObject error) {
        this.parameterName = safeValue(error.get("parameter_name"));
        this.type = safeValue(error.get("type"));
        this.message = safeValue(error.get("message"));
    }

    public String getParameterName() {
        return parameterName;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
