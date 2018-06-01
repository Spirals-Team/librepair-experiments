package com.m2dl.dlmovie.config.security.requests;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class SignUpRequestDeserializer extends JsonDeserializer<SignUpRequest> {
    @Override
    public SignUpRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode nodeEmail = node.get("email");
        JsonNode nodePseudo = node.get("pseudo");
        JsonNode nodePwd = node.get("password");
        if(nodeEmail == null || nodePseudo == null || nodePwd == null) throw new IOException("Cannot deserialize SignUpRequest");
        String email = nodeEmail.textValue();
        String pseudo = nodePseudo.textValue();
        String pwd = nodePwd.textValue();
        return new SignUpRequest(pseudo, email, pwd);
    }
}