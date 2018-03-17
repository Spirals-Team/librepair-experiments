package org.w4tracking.representations.idm;

import java.net.URI;

public class LinksRepresentation {

    private String self;
    private String meta;
    private String related;

    public LinksRepresentation() {

    }

    private LinksRepresentation(Builder builder) {
        this.setSelf(builder.self != null ? builder.self.toString() : null);
        this.setMeta(builder.meta != null ? builder.meta.toString() : null);
        this.setRelated(builder.related != null ? builder.related.toString() : null);
    }

    public String getSelf() {
        return self;
    }

    public String getMeta() {
        return meta;
    }

    public String getRelated() {
        return related;
    }

    public void setSelf(String self) {
        this.self = self;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public void setRelated(String related) {
        this.related = related;
    }

    public static class Builder {
        private URI self;
        private URI meta;
        private URI related;

        public Builder withSelf(URI self) {
            this.self = self;
            return this;
        }

        public Builder withMeta(URI meta) {
            this.meta = meta;
            return this;
        }

        public Builder withRelated(URI related) {
            this.related = related;
            return this;
        }

        public LinksRepresentation build() {
            return new LinksRepresentation(this);
        }
    }
}
