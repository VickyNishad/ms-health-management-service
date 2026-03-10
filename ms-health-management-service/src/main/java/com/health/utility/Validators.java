
package com.health.utility;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import com.health.constants.Constants; 


/**
 * Utility class for common validation checks.
 */
public class Validators {

    /**
     * Checks if the given value is null or empty based on its type.
     * * @param <T> The type of the value.
     * @param value The object to check.
     * @return true if the value is null or considered empty (e.g., empty String, 
     * Collection, Array, Optional, or Number is 0), false otherwise.
     */
    public static <T> boolean isNullOrEmpty(T value) {
        if (value == null) {
            return true;
        }

        // Check for String
        if (value instanceof String) {
            // Use 'trim().isEmpty()' to treat strings with only whitespace as empty
            return ((String) value).trim().isEmpty();
        }

        // Check for Collection (List, Set, etc.)
        if (value instanceof Collection) {
            return ((Collection<?>) value).isEmpty();
        }

        // Check for Map
        if (value instanceof Map) {
            return ((Map<?, ?>) value).isEmpty();
        }
        
        // Check for Optional
        if (value instanceof Optional) {
            return !((Optional<?>) value).isPresent();
        }
        
        // Check for Array (Handles both primitive and object arrays)
        if (value.getClass().isArray()) {
            return java.lang.reflect.Array.getLength(value) == 0;
        }

        // Check for Number (if 0.0 is considered "empty")
        if (value instanceof Number) {
            return ((Number) value).doubleValue() == 0.0;
        }

        // Check for Boolean (if 'false' is considered "empty")
        if (value instanceof Boolean) {
            return !((Boolean) value);
        }

        // For all other object types, return false
        return false;
    }
    
    /**
     * @apiNote Checks if the input string is a valid numeric value (integer or decimal, positive or negative).
     * @author VICKY NISHAD
     * @param fieldValue The input string to check.
     * @return true if the input is a valid numeric value, false otherwise.
     */
    public static boolean isValidNumericInput(String fieldValue) {
        if (isNullOrEmpty(fieldValue)) {
            return false;
        }

        // Pattern for integers and decimals (positive/negative)
        String numericPattern = "^-?\\d+(\\.\\d+)?$"; 

        // Use the pattern to match the trimmed input string
        return fieldValue.trim().matches(numericPattern);
    }
    
    /**
     * @apiNote Checks if the length of the given field value is within the specified min and max range.
     * @param fieldValue The input string to check.
     * @param min The minimum allowed length (inclusive).
     * @param max The maximum allowed length (inclusive).
     * @return true if the length of fieldValue is between min and max (inclusive),
     * false otherwise.
     */
    public static boolean isValidLength(String fieldValue, int min, int max) {
        // Validation check: Ensure min <= max
        if (min > max) {
            return false; 
        }
        
        if (isNullOrEmpty(fieldValue)) {
            // An empty string (length 0) is valid only if min is 0.
            return min == 0; 
        }

        int length = fieldValue.length();
        // Check if the length is within the specified range
        return length >= min && length <= max;
    }
    
    /**
     * @apiNote Checks if the input string contains only alphanumeric characters (a-z, A-Z, 0-9).
     * @param value The input string to check.
     * @return true if the input is not null/empty and is purely alphanumeric, false otherwise.
     */
    public static boolean isAlphanumeric(String value) {
        if (isNullOrEmpty(value)) {
            return false;
        }
        return value.matches("^[a-zA-Z0-9]*$");
    }
    
    /**
     * @apiNote Checks if the input string is a valid alphanumeric value, optionally allowing spaces.
     * @param fieldValue The input string to check.
     * @param spaceCheck If true, spaces are allowed; otherwise, they are not.
     * @return true if the input is valid alphanumeric (with optional spaces), false otherwise.
     */
    public static boolean isValidAlphaNumeric(String fieldValue, boolean spaceCheck) {
        if (isNullOrEmpty(fieldValue)) {
            return false; 
        }

        // Define the pattern based on spaceCheck
        String pattern = spaceCheck ? "^[a-zA-Z0-9 ]*$" : "^[a-zA-Z0-9]*$";

        // Match the field value against the pattern
        return fieldValue.matches(pattern);
    }

    /**
     * @apiNote Checks if the input string is alphanumeric with options to allow spaces and specific *allowed* special characters.
     * @param value The input string to check.
     * @param spaceCheck If true, spaces are allowed in the string.
     * @param allowedCharacters A string of characters that are *allowed* in addition to alphanumeric and spaces.
     * @return true if the string meets the criteria, false otherwise.
     */
    public static boolean isValidAlphaNumeric(String value, boolean spaceCheck, String allowedCharacters) {
        if (isNullOrEmpty(value)) {
            return false;
        }

        // Build the regex pattern dynamically
        StringBuilder patternBuilder = new StringBuilder("^[a-zA-Z0-9");

        // 1. Allow spaces if spaceCheck is true
        if (spaceCheck) {
            patternBuilder.append(" ");
        }

        // 2. Allow specified characters (must be quoted for regex to treat them literally)
        if (allowedCharacters != null && !allowedCharacters.isEmpty()) {
            // Pattern.quote escapes all special regex characters in the string
            patternBuilder.append(Pattern.quote(allowedCharacters)); 
        }

        // 3. Close the character class and require one or more matches until end of string
        patternBuilder.append("]*$"); 

        Pattern pattern = Pattern.compile(patternBuilder.toString());
        return pattern.matcher(value).matches();
    }

    /**
     * check email pattern 
     * @param email The email string to validate.
     * @return true if the email matches the pattern defined in Constants, false otherwise.
     */
    public static boolean isValidEmail(String email) {
        if (isNullOrEmpty(email)) return false;
        return Pattern.matches(Constants.RegexPattern.EMAIL_PATTERN, email);
    }

    /**
     * check mobile pattern
     * @param mobile The mobile number string to validate.
     * @return true if the mobile number matches the pattern defined in Constants, false otherwise.
     */
    public static boolean isValidMobile(String mobile) {
        if (isNullOrEmpty(mobile)) return false;
        return Pattern.matches(Constants.RegexPattern.MOBILE_PATTERN, mobile);
    }
    

    /**
     * Orchestrates multiple field validations and throws a RuntimeException upon the first failure.
     * * @param fieldName The name of the field (used in error messages).
     * @param fieldValue The value of the field to validate (can be String, Number, etc.).
     * @param nullCheck If true, checks for null or empty.
     * @param numericCheck If true, checks if the value is a valid number.
     * @param alphaNumericCheck If true, checks if the value is alphanumeric.
     * @param lengthCheck If true, checks length against min/max.
     * @param allowedCharacters Characters allowed in addition to alphanumeric (used with alphaNumericCheck).
     * @param min Minimum allowed length (used with lengthCheck).
     * @param max Maximum allowed length (used with lengthCheck).
     * @param spaceCheck If true, allows spaces (used with alphaNumericCheck).
     * @return null if all validations pass (String return type is just for method signature).
     */
    public static String validateRequestedField(String fieldName, Object fieldValue, boolean nullCheck,
            boolean numericCheck, boolean alphaNumericCheck, boolean lengthCheck, String allowedCharacters, int min,
            int max, boolean spaceCheck) {

        // 1. Convert Object to String
        String valueAsString = (fieldValue != null) ? String.valueOf(fieldValue) : null;
        
        // --- 2. Null/Empty Check ---
        if (nullCheck && isNullOrEmpty(valueAsString)) {
            throw new RuntimeException(fieldName + Constants.Error.CANNOT_BE_NULL_OR_EMPTY);
        }
        
        // If the value is empty/null but nullCheck was false, we stop here.
        if (isNullOrEmpty(valueAsString)) {
            return null; 
        }

        // --- 3. AlphaNumeric Check ---
        if (alphaNumericCheck) {
            if (!isValidAlphaNumeric(valueAsString, spaceCheck, allowedCharacters)) { 
                
                String allowed = "alphanumeric";
                if (spaceCheck) allowed += ", spaces";
                if (allowedCharacters != null && !allowedCharacters.isEmpty()) allowed += ", " + allowedCharacters;
                
                String errorMessage = fieldName + 
                                      Constants.Error.SHOULD_BE_ALFA_NUMERIC_OR_CHARACTERS+ 
                                      allowed + 
                                      Constants.Error.ARE_NOT_ALLOWED;
                
                throw new RuntimeException(errorMessage);
            }
        }


        // --- 4. Numeric Check ---
        if (numericCheck && !isValidNumericInput(valueAsString)) {
            throw new RuntimeException(fieldName + Constants.Error.SHOULD_BE_NUMERIC_VALUE);
        }

        // --- 5. Length Check ---
        if (lengthCheck) {
            if (!isValidLength(valueAsString, min, max)) {
                if (min == max) {
                    throw new RuntimeException(fieldName + Constants.Error.SHOULD_BE_EXACTLY + min + Constants.Error.CHARACTERS_LONG);
                } else {
                    throw new RuntimeException(fieldName + Constants.Error.SHOULD_BE_BETWEEN + min + Constants.Error.TO + max + Constants.Error.CHARACTERS_LONG);
                }
            }
        }
        
        return null;        
    }
    
    /**
     * Simple overload to check only for Null or Empty status.
     */
    public static String validateRequestedField(String fieldName, String fieldValue) {
        // Calls the full method with dummy values for disabled checks
        return validateRequestedField(fieldName, fieldValue, 
            true, 
            false, false, false, 
            null, 0, 0, false);
    }
    
    /**
     * Simple overload to check Length range and Numeric status.
     */
    public static String validateRequestedField(String fieldName, Object fieldValue, boolean numericCheck, int min, int max) {
        // Calls the full method, setting lengthCheck=true and other checks to false
        return validateRequestedField(fieldName, fieldValue, 
            true, // Do not enforce nullCheck by default
            numericCheck, 
            false, 
            true, // Enable length check
            null, min, max, false);
    }

    /**
     * Simple overload to check only for Numeric status.
     */
    public static String validateRequestedField(String fieldName, Object fieldValue, boolean numericCheck) {
        // Calls the full method with dummy values for disabled checks
        return validateRequestedField(fieldName, fieldValue, 
            false, // Do not enforce nullCheck by default
            numericCheck, 
            false, false, 
            null, 0, 0, false);
    }


    /**
     * Simple overload to check only for Length range.
     */
    public static String validateRequestedField(String fieldName, Object fieldValue, int min, int max) {
        // Calls the full method, setting lengthCheck=true and other checks to false
        return validateRequestedField(fieldName, fieldValue, 
            true, // Do not enforce nullCheck by default
            false,
            false, 
            true, // Enable length check
            null,
            min,
            max, 
            false);
    }
    
    

}
