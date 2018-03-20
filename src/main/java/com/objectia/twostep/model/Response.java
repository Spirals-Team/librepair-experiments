package com.objectia.twostep.model;

public class Response<T> {
	protected int status;
	protected String message;
    protected boolean success;
    protected T data;

    public Response() {
    }

	public Response(int status, String message) {
		this.status = status;
		this.message = message;
		this.success = false;
	}

	public int getStatus() {
		return this.status;
	}

    public String getMessage() {
		return this.message;
    }
    
    public T getData() {
        return this.data;
    }

}