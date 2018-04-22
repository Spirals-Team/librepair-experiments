package com.acqua.rest.responses;



/**
 * Base response message
 * 
 * @author Christian Lusardi
 * @version 1.0
 *
 */
public class BaseResponse {
	
	private String statusCode;
	
	private String statusMessage;
	
	private String message;
	
	
	
	/**
	 * Empty constructor
	 */
	public BaseResponse() {
		super();
	}

	
	/**
	 * Constructor for {@link BaseResponse} object
	 * 
	 * @param statusCode {@link String} http status code
	 * 
	 * @param statusMessage {@link String} http status code description/message
	 */
	public BaseResponse(String statusCode, String statusMessage) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
	}
	
	
	/**
	 * Constructor for {@link BaseResponse} object
	 * 
	 * @param statusCode {@link String} http status code
	 * 
	 * @param statusMessage {@link String} http status code description/message
	 * 
	 * @param message {@link String} additional message
	 */
	public BaseResponse(String statusCode, String statusMessage, String message) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.message = message;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
