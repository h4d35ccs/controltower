package es.upm.emse.softdesign.controltower.presenter.helper;

import es.upm.emse.softdesign.controltower.presenter.library.RestStatus;

public class RestResponse <T>{
	private String message = "";
	private RestStatus status;
	private T responseObject;
	
	public RestResponse(){
		this.message="";
		this.status=RestStatus.ERROR;
	}
	
	public RestResponse(String message, RestStatus status, T responseObject){
		this.message = message;
		this.status = status;
		this.responseObject = responseObject;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public RestStatus getStatus() {
		return status;
	}
	public void setStatus(RestStatus status) {
		this.status = status;
	}
	public T getResponseObject() {
		return responseObject;
	}
	public void setResponseObject(T responseObject) {
		this.responseObject = responseObject;
	}
}
