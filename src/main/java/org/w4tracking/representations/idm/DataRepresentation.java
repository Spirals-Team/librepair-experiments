package org.w4tracking.representations.idm;

import java.io.Serializable;

public class DataRepresentation<T extends AttributesRepresentation> implements Serializable {

    private String id;
    private String type;
    private LinksRepresentation links;
    private T attributes;

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
}
