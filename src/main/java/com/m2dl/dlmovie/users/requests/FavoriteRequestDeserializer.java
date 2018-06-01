package com.m2dl.dlmovie.users.requests;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class FavoriteRequestDeserializer extends JsonDeserializer<FavoriteRequest> {
    @Override
    public FavoriteRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode nodeId = node.get("movieId");
        if(nodeId == null) throw new IOException("Cannot deserialize FavoriteRequest");
        Long movieId = Long.parseLong(nodeId.toString());
        return new FavoriteRequest(movieId);
    }
}
