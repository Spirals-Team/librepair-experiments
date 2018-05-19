package org.lwstudio.springtodo.exception;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -2565431806475335331L;

    private String resourceName;
    private Long id;

    public ResourceNotFoundException() {
        super();
    }
    public ResourceNotFoundException(String s) {
        super(s);
    }
    public ResourceNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }
    public ResourceNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String getMessage() {
        return resourceName + " with id " + id + " is not found.";
    }

}
