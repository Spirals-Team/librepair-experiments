package com.objectia.twostep.model;

import java.util.List;
import java.util.Map;

public class Response {
	private int status;
	private String body;
	private Headers headers;

	public Response(int status, String body) {
		this.status = status;
		this.body = body;
		this.headers = null;
	}

	public Response(int status, String body, Map<String, List<String>> headers) {
		this.status = status;
		this.body = body;
		this.headers = new Headers(headers);
	}

	public int getStatus() {
		return this.status;
	}

	public String getBody() {
		return this.body;
	}

	public Headers getHeaders() {
		return headers;
	}
}