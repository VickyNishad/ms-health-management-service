/**
 * 
 */
package com.health.service;

/**
 * 
 */
public interface ProcessService {
	
	boolean is2xxSuccessful();
	
	String getError();
	
	<T> T getSuccess();

}
