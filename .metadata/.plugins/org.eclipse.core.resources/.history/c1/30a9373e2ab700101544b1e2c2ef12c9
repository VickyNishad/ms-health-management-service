/**
 * 
 */
package com.common.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 
 */
public class Mappers {
	
	@SuppressWarnings("unchecked")
	public static Map<String, List<?>> mapResultSetToPojo( Map<String, Object> rawResults, Map<String, Class<?>> resultSetMappings) {

        Map<String, List<?>> mappedResults = new HashMap<>();

        for (Map.Entry<String, Class<?>> entry : resultSetMappings.entrySet()) {
            String rsName = entry.getKey();
            Class<?> pojoClass = entry.getValue();

            List<Map<String, Object>> rawRows = (List<Map<String, Object>>) rawResults.get(rsName);

            if (rawRows != null) {
                List<?> pojoList = rawRows.stream()
                        .map(row -> Convertors.convertObjectToPojo(row, pojoClass))
                        .collect(Collectors.toList());

                mappedResults.put(rsName, pojoList);
            }
        }

        return mappedResults;
    }
	
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> mapResultSet(Map<String, Object> rawResults,String resultSetName,Class<T> pojoClass) {
		
        List<Map<String, Object>> rawRows = (List<Map<String, Object>>) rawResults.get(resultSetName);
        if (rawRows == null) {
            return Collections.emptyList();
        }
        return rawRows.stream()
                .map(row -> Convertors.convertObjectToPojo(row, pojoClass))
                .collect(Collectors.toList());
    }
	

}
