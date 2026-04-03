/**
 * 
 */
package com.health.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.health.dto.response.ApiResponse;

/**
 * 
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	
	 // Validation error handler
   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<ApiResponse<String>> handleValidationErrors(MethodArgumentNotValidException ex) {
       return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.BAD_REQUEST);
   }
   
   
   @ExceptionHandler({BindException.class})
   public ResponseEntity<Map<String, String>> handleBindErrors(BindException ex) {
       Map<String, String> errors = new HashMap<>();
       ex.getBindingResult().getFieldErrors().forEach(error -> {
           errors.put(error.getField(), error.getDefaultMessage());
       });
       return ResponseEntity.badRequest().body( errors);
   }
   
   
   @ExceptionHandler(InvalidDefinitionException.class)
   public ResponseEntity<ApiResponse<Object>> handleJacksonException(
           InvalidDefinitionException ex) {

       return new ResponseEntity<>(ApiResponse.error(ex.getLocalizedMessage()), HttpStatus.OK);
   }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDuplicate(DataIntegrityViolationException ex) {

        String message = "Duplicate or invalid data";

        String rootMsg = ex.getRootCause() != null
                ? ex.getRootCause().getMessage()
                : ex.getMessage();

        if (rootMsg != null && rootMsg.contains("Duplicate entry")) {

            String value = rootMsg.split("'")[1]; // extract duplicate value

            message = "Duplicate entry: " + value;
        }

        return ResponseEntity.badRequest().body(
                Map.of(
                        "success", false,
                        "message", message,
                        "data", null
                )
        );
    }
}
