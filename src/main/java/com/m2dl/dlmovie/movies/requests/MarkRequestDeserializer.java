package com.m2dl.dlmovie.movies.requests;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class MarkRequestDeserializer extends JsonDeserializer<MarkRequest> {

    @Override
    public MarkRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode nodeMovieId = node.get("movieId");
        JsonNode nodeUserId = node.get("userId");
        JsonNode nodeValue = node.get("value");
        if(nodeMovieId == null || nodeUserId == null || nodeValue == null){
            throw new IOException("Cannot deserialize FavoriteRequest");
        }
        return new MarkRequest(nodeValue.intValue(), nodeMovieId.longValue(), nodeUserId.longValue());
    }

}
