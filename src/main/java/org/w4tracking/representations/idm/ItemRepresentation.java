package org.w4tracking.representations.idm;

public class ItemRepresentation<T extends AttributesRepresentation> extends Representation<DataRepresentation<T>> {

    public ItemRepresentation() {
        super();
    }

    public ItemRepresentation(DataRepresentation<T> data) {
        super(data);
    }

    public ItemRepresentation(Builder<T> builder) {
        super();

        DataRepresentation<T> data = new DataRepresentation<>();
        data.setId(builder.id);
        data.setType(builder.type);
        data.setLinks(builder.links);
        data.setAttributes(builder.attributes);

        setData(data);
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

        public ItemRepresentation<T> build() {
            return new ItemRepresentation<>(this);
        }
    }

}
