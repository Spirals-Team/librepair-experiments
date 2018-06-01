package com.m2dl.dlmovie.config.security.requests;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class LoginRequestDeserializer extends JsonDeserializer<LoginRequest> {

    @Override
    public LoginRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String usernameOrEmail = node.get("usernameOrEmail").textValue();
        String pwd = node.get("password").textValue();
        return new LoginRequest(usernameOrEmail, pwd);
    }
}