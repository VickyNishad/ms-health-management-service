package com.health.models;

/**
 * 
 */
public class ResponseModel {

	private String status;
	private Object data;

	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public ResponseModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ResponseModel(String status, Object data) {
		super();
		this.status = status;
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseModel [status=" + status + ", data=" + data + "]";
	}

	

	
}
