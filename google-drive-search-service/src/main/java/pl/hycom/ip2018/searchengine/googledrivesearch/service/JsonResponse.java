package pl.hycom.ip2018.searchengine.googledrivesearch.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Class for managing responses
 */
@Service
public class JsonResponse {

    @Autowired
    private Gson gson;

    /**
     * Serializes a given map
     * @param map map intended to serialize
     * @return Json String
     */
    public String getAsString(Map map) {
        return gson.toJson(map);
    }

    /**
     * Converts Json String to desired type
     * @param json Json String to convert
     * @param resultType common superinterface for all types
     * @param <T> desired type
     * @return Json String in form of object of a desired type
     */
    public <T> T getAsObject(String json, Type resultType) {
        return gson.fromJson(json, resultType);
    }
}
