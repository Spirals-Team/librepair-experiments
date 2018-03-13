package org.w4tracking.representations.idm;

import java.io.Serializable;

public class LinksRepresentation implements Serializable {

    private String self;
    private String meta;
    private String related;

    public LinksRepresentation() {

    }

    private LinksRepresentation(Builder builder) {
        this.self = builder.self;
        this.meta = builder.meta;
        this.related = builder.related;
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

    public static class Builder {
        private String self;
        private String meta;
        private String related;

        public Builder withSelf(String self) {
            this.self = self;
            return this;
        }

        public Builder withMeta(String meta) {
            this.meta = meta;
            return this;
        }

        public Builder withRelated(String related) {
            this.related = related;
            return this;
        }

        public LinksRepresentation build() {
            return new LinksRepresentation(this);
        }
    }
}
