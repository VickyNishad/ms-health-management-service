/**
 * 
 */
package com.health.utility;


import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;



public class Convertors {

    private static final ObjectMapper objectMapper = new ObjectMapper(); // Use static objectMapper for performance reasons

    /**
     * Converts an object to a Map.
     *
     * @param obj The object to be converted
     * @return The converted Map
     */
    @SuppressWarnings("unchecked")
	public static Map<String, Object> convertObjectToMap(Object obj) {
        try {
            return objectMapper.convertValue(obj, Map.class);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Converts an object to a String.
     *
     * @param obj The object to be converted
     * @return The converted String
     */
    public static String convertObjectToString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            return null; 
        }
    }
    
    public static <T> T convertObjectToPojo(Object obj,Class<T> clazz) {
    	try {
    		return objectMapper.convertValue(obj, clazz);
        } catch (Exception e) {
            return null;
        }
    }
}
