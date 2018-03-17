package org.w4tracking.representations.idm;

public class ItemRepresentation<T extends AttributesRepresentation> {

    private DataRepresentation<T> data;

    public ItemRepresentation() {

    }

    public ItemRepresentation(DataRepresentation<T> data) {
        this.setData(data);
    }

    private ItemRepresentation(Builder<T> builder) {
        this.setData(builder.data);
    }

    public DataRepresentation<T> getData() {
        return data;
    }

    public void setData(DataRepresentation<T> data) {
        this.data = data;
    }

    public static class Builder<T extends AttributesRepresentation> {
        private DataRepresentation<T> data;

        public Builder<T> withData(DataRepresentation<T> data) {
            this.data = data;
            return this;
        }

        public ItemRepresentation<T> build() {
            return new ItemRepresentation<>(this);
        }
    }

}
