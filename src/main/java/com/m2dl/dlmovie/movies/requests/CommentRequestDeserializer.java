package com.m2dl.dlmovie.movies.requests;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class CommentRequestDeserializer extends JsonDeserializer<CommentRequest> {

    @Override
    public CommentRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        JsonNode nodeMovieId = node.get("movieId");
        JsonNode nodeUserId = node.get("userId");
        JsonNode nodeValue = node.get("text");
        if(nodeMovieId == null || nodeUserId == null || nodeValue == null){
            throw new IOException("Cannot deserialize FavoriteRequest");
        }
        return new CommentRequest(nodeValue.toString(), nodeMovieId.longValue(), nodeUserId.longValue());
    }

}