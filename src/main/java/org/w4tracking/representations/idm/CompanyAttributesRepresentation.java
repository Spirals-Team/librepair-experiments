package org.w4tracking.representations.idm;

public class CompanyAttributesRepresentation implements AttributesRepresentation {

    private String name;

    public CompanyAttributesRepresentation() {

    }

    private CompanyAttributesRepresentation(Builder builder) {
        this.name = builder.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Builder {
        private String name;

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public CompanyAttributesRepresentation build() {
            return new CompanyAttributesRepresentation(this);
        }
    }
}
