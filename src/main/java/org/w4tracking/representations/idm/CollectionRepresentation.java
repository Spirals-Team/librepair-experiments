package org.w4tracking.representations.idm;

import java.util.List;
import java.util.Map;

public class CollectionRepresentation<T extends AttributesRepresentation> {

    private List<DataRepresentation<T>> data;
    private Map<String, Object> meta;
    private Map<String, String> links;

    public CollectionRepresentation() {

    }

    public CollectionRepresentation(List<DataRepresentation<T>> data) {
        this.setData(data);
    }

    private CollectionRepresentation(Builder<T> builder) {
        this.setData(builder.data);
        this.setMeta(builder.meta);
        this.setLinks(builder.links);
    }

    public List<DataRepresentation<T>> getData() {
        return data;
    }

    public void setData(List<DataRepresentation<T>> data) {
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

    public static class Builder<T extends AttributesRepresentation> {
        private Map<String, Object> meta;
        private Map<String, String> links;
        private List<DataRepresentation<T>> data;

        public Builder<T> withMeta(Map<String, Object> meta) {
            this.meta = meta;
            return this;
        }

        public Builder<T> withLinks(Map<String, String> links) {
            this.links = links;
            return this;
        }

        public Builder<T> withData(List<DataRepresentation<T>> data) {
            this.data = data;
            return this;
        }

        public CollectionRepresentation<T> build() {
            return new CollectionRepresentation<>(this);
        }
    }

}
