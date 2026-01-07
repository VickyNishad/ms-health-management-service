/**
 * 
 */
package com.common.utils;


import java.util.Map;

import tools.jackson.databind.ObjectMapper;

public class Convertors {

    private static ObjectMapper objectMapper = new ObjectMapper(); // Use static objectMapper for performance reasons

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
        	e.printStackTrace();
            return null; 
        }
    }
    
    public static int getIntValue(Object value) {
        if (value == null) {
            return 0; 
        }

        try {
            if (value instanceof Integer) {
                return (int) value;
            } else if (value instanceof String) {
                return Integer.parseInt((String) value);
            } else if (value instanceof Number) {
                return ((Number) value).intValue(); 
            } else {
                return Integer.parseInt(value.toString());
            }
        } catch (NumberFormatException e) {
            System.err.println("Unable to convert to int: " + value);
            return 0; 
        }
    }
    
    public static String getStringValue(Object value) {
        if (value == null) {
            return ""; // 
        }

        return String.valueOf(value);
    }


}
