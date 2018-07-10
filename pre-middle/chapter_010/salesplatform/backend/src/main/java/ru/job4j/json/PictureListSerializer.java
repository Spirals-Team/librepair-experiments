package ru.job4j.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.job4j.model.Picture;

import java.lang.reflect.Type;
import java.util.List;

public class PictureListSerializer implements JsonSerializer<List<Picture>> {

    @Override
    public JsonElement serialize(final List<Picture> posts, final Type type,
                                 final JsonSerializationContext jsonSerializationContext) {

        JsonArray jsonPost = new JsonArray();
        posts.forEach(p -> jsonPost.add(p.getId()));
        return jsonPost;
    }
}
