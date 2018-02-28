package me.pagar.util;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JSONUtils {

    public static final Gson getInterpreter() {
        return getNewDefaultGsonBuilder().create();
    }

    public static <T> T getAsObject(JsonObject json, Class<T> clazz) {
        return getNewDefaultGsonBuilder().create().fromJson(json, clazz);
    }

    public static <T> T getAsObject(JsonObject json, DateTimeAdapter adapter, Class<T> clazz) {
        GsonBuilder builder = getNewDefaultGsonBuilder().registerTypeAdapter(DateTime.class, adapter);
        return builder.create().fromJson(json, clazz);
    }

    public static <T> Collection<T> getAsList(JsonArray json, Type listType) {
        return getNewDefaultGsonBuilder().create().fromJson(json, listType);
    }

    public static <T> Collection<T> getAsList(JsonArray json, DateTimeAdapter adapter, Type listType) {
        GsonBuilder builder = getNewDefaultGsonBuilder().registerTypeAdapter(DateTime.class, adapter);
        return builder.create().fromJson(json, listType);
    }

    public static String getAsJson(Object object) {
        return getNewDefaultGsonBuilder().create().toJson(object);
    }

    public static String getAsJson(Object object, DateTimeAdapter adapter) {
        GsonBuilder builder = getNewDefaultGsonBuilder().registerTypeAdapter(DateTime.class, adapter);
        return builder.create().toJson(object);
    }

    public static Map<String, Object> objectToMap(Object object) {
        GsonBuilder builder = getNewDefaultGsonBuilder();
        final String json = builder.create().toJson(object);
        return builder.create().fromJson(json, new TypeToken<HashMap<String, Object>>() {
        }.getType());
    }

    public static Map<String, Object> objectToMap(Object object, DateTimeAdapter adapter) {
        GsonBuilder builder = getNewDefaultGsonBuilder().registerTypeAdapter(DateTime.class, adapter);
        final String json = builder.create().toJson(object);
        return builder.create().fromJson(json, new TypeToken<HashMap<String, Object>>() {
        }.getType());
    }

    public static JsonObject treeToJson(Object object) {
        return getNewDefaultGsonBuilder().create().toJsonTree(object).getAsJsonObject();
    }

    private static GsonBuilder getNewDefaultGsonBuilder(){
        return new GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .registerTypeAdapter(DateTime.class, new DateTimeIsodateAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setExclusionStrategies(new ExclusionStrategy(){

                public boolean shouldSkipClass(Class<?> clazz) {
                    return false;
                }

                public boolean shouldSkipField(FieldAttributes fieldAttrs) {
                    return fieldAttrs.equals(null);
                }
                
            });
    }
}
