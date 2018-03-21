package com.objectia.twostep.model;

public class Resource<T> {
	protected int status;
	protected String message;
    protected boolean success;
    protected T data;

    public Resource() {
    }

	public int getStatus() {
		return this.status;
	}

    public String getMessage() {
		return this.message;
    }

	public boolean getSuccess() {
		return this.success;
    }
    
    public T getData() {
        return this.data;
    }

}