package org.w4tracking.representations.idm;

public class CompanyAttributes implements AttributesRepresentation {

    private String name;

    public CompanyAttributes() {

    }

    public CompanyAttributes(Builder builder) {
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

        public CompanyAttributes build() {
            return new CompanyAttributes(this);
        }
    }
}
