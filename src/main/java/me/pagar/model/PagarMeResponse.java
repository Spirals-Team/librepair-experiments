package me.pagar.model;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class PagarMeResponse {

    private int code = -1;

    private final JsonElement body;

    public PagarMeResponse(final JsonElement body) {
        this.body = body;
    }

    public PagarMeResponse(final int code, final JsonElement body) {
        this.code = code;
        this.body = body;
    }

    public PagarMeResponse(final JsonObject object) {
        this.code = object.get("code").getAsInt();
        this.body = object.get("body");
    }

    public int getCode() {
        return code;
    }

    public JsonElement getBody() {
        return body;
    }

}
