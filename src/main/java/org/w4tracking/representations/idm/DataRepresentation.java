package org.w4tracking.representations.idm;

public class DataRepresentation<T extends AttributesRepresentation> {

    private String id;
    private String type;
    private LinksRepresentation links;
    private T attributes;

    public DataRepresentation() {

    }

    private DataRepresentation(Builder<T> builder) {
        this.setId(builder.id);
        this.setType(builder.type);
        this.setLinks(builder.links);
        this.setAttributes(builder.attributes);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LinksRepresentation getLinks() {
        return links;
    }

    public void setLinks(LinksRepresentation links) {
        this.links = links;
    }

    public T getAttributes() {
        return attributes;
    }

    public void setAttributes(T attributes) {
        this.attributes = attributes;
    }

    public static class Builder<T extends AttributesRepresentation> {
        private String id;
        private String type;
        private LinksRepresentation links;
        private T attributes;

        public Builder<T> withId(String id) {
            this.id = id;
            return this;
        }

        public Builder<T> withType(String type) {
            this.type = type;
            return this;
        }

        public Builder<T> withLinks(LinksRepresentation links) {
            this.links = links;
            return this;
        }

        public Builder<T> withAttributes(T attributes) {
            this.attributes = attributes;
            return this;
        }

        public DataRepresentation<T> build() {
            return new DataRepresentation<>(this);
        }
    }
}
