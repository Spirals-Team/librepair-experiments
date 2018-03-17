package org.w4tracking.models;

public enum ModelType {

    COMPANY("company");

    private String alias;

    ModelType(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }
}
