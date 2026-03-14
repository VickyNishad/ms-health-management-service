/**
 * 
 */
package com.health.service.impl;

import com.health.service.ProcessService;

/**
 * @param <T>
 * 
 */
public abstract class AbstractProcessService<T> implements ProcessService {
	
	protected int statusCode;
	
	protected String errorMessage;
	
	protected T successData;
	
	protected AbstractProcessService(int statusCode, T successData, String errorMessage ) {
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
		this.successData = successData;
	}
	
	
	@Override
	public boolean is2xxSuccessful() {
	    return statusCode >= 200 && statusCode < 300;
	}


	@Override
	public String getError() {
		// TODO Auto-generated method stub
		return errorMessage;
	}

	@Override
	public T getSuccess() {
		// TODO Auto-generated method stub
		return successData;
	}

}
