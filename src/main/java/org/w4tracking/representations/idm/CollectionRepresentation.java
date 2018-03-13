package org.w4tracking.representations.idm;

import java.util.List;
import java.util.Map;

public class CollectionRepresentation<T extends AttributesRepresentation> extends Representation<List<? extends DataRepresentation<T>>> {

    public CollectionRepresentation() {
        super();
    }

    public CollectionRepresentation(List<? extends DataRepresentation<T>> list) {
        super(list);
    }

    public CollectionRepresentation(Builder<T> builder) {
        super();

        setData(builder.data);
        setLinks(builder.links);
        setMeta(builder.meta);
    }

    public static class Builder<T extends AttributesRepresentation> {
        private Map<String, Object> meta;
        private Map<String, String> links;
        private List<? extends DataRepresentation<T>> data;

        public Builder withMeta(Map<String, Object> meta) {
            this.meta = meta;
            return this;
        }

        public Builder withLinks(Map<String, String> links) {
            this.links = links;
            return this;
        }

        public Builder withData(List<? extends DataRepresentation<T>> data) {
            this.data = data;
            return this;
        }

        public CollectionRepresentation<T> build() {
            return new CollectionRepresentation<>(this);
        }
    }

}
