package org.w4tracking.representations.idm;

import java.io.Serializable;
import java.util.Map;

public class Representation<T> implements Serializable {

    private T data;
    private Map<String, Object> meta;
    private Map<String, String> links;

    public Representation() {

    }

    public Representation(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Map<String, Object> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, Object> meta) {
        this.meta = meta;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }

}
