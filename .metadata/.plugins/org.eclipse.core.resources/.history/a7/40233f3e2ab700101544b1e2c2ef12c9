/**
 * 
 */
package com.common.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import com.common.constants.Constants;
import com.common.exceptions.InvalidInputException;


/**
 * 
 */
public class Validatiors {

	public static <T> boolean isNullOrEmpty(T value) {
		if (value == null) {
			return true;
		}

		// Check for String
		if (value instanceof String) {
			return ((String) value).trim().isEmpty();
		}

		// Check for Collection (List, Set, etc.)
		if (value instanceof Collection) {
			return ((Collection<?>) value).isEmpty();
		}

		// Check for Array
		if (value.getClass().isArray()) {
			return java.lang.reflect.Array.getLength(value) == 0;
		}

		// Check for Optional
		if (value instanceof Optional) {
			return !((Optional<?>) value).isPresent();
		}

		// Check for Number (Integer, Long, Double, etc.)
		if (value instanceof Number) {
			return ((Number) value).doubleValue() == 0.0;
		}

		// Check for Map
		if (value instanceof Map) {
			return ((Map<?, ?>) value).isEmpty();
		}

		// Check for Array
		if (value.getClass().isArray()) {
			return ((Object[]) value).length == 0;
		}

		// Check for Boolean
		if (value instanceof Boolean) {
			return !((Boolean) value);
		}

		// For other object types, return false (null check already handled)
		return false;
	}
	
	/**
	 * @apiNote if the input string is a valid numeric value (integer or decimal).
	 * @author Vicky Nishad
	 * @param fieldValue The input string to check.
	 * @return true if the input is a valid numeric value, false otherwise.
	 */
	public static boolean isValidNumericInput(String fieldValue) {
		// Step 1: Check if the input is null or empty
		if (isNullOrEmpty(fieldValue)) {
			return false;
		}

		// Step 2: Define the numeric pattern for integers and decimals
		// (positive/negative)
		String numericPattern = "^-?\\d+(\\.\\d+)?$"; // Allows negative numbers and decimals

		// Step 3: Use the pattern to match the trimmed input string
		return fieldValue.trim().matches(numericPattern);
	}
	
	/**
	 * @apiNote if the length of the given field value is within the specified min and max range.
	 * @param fieldValue The input string to check.
	 * @param min        The minimum allowed length.
	 * @param max        The maximum allowed length.
	 * @return true if the length of fieldValue is between min and max (inclusive),
	 *         false otherwise.
	 */
	public static boolean isValidLength(String fieldValue, int min, int max) {
		if (isNullOrEmpty(fieldValue)) {
			// If fieldValue is null or empty, it should fail validation if min > 0
			return min == 0; // Returns true only if the minimum length is zero
		}

		int length = fieldValue.length();
		// Check if the length is within the specified range
		return length >= min && length <= max;
	}
	
	// Validate if a value is alphanumeric
    public static boolean isAlphanumeric(String value) {
        if (isNullOrEmpty(value)) {
            return false;
        }
        // Regex for alphanumeric only
        return value.matches("^[a-zA-Z0-9]*$");
    }
	
	/**
	 * @apiNote if the input string is a valid alphanumeric value, optionally allowing spaces.
	 * @param fieldValue The input string to check.
	 * @param spaceCheck If true, spaces are allowed; otherwise, they are not.
	 * @return true if the input is valid alphanumeric (with optional spaces), false otherwise.
	 */
	public static boolean isValidAlphaNumeric(String fieldValue, boolean spaceCheck) {
		if (isNullOrEmpty(fieldValue)) {
			return false; // Null or empty check
		}

		// Define the pattern based on spaceCheck
		String pattern = spaceCheck ? "^[a-zA-Z0-9 ]*$" : "^[a-zA-Z0-9]*$";

		// Match the field value against the pattern
		return fieldValue.trim().matches(pattern);
	}

	/**
	 * @apiNote Checks if the input string is alphanumeric with options to allow spaces and exclude specific characters.
	 * @param value            The input string to check.
	 * @param spaceCheck       If true, spaces are allowed in the string.
	 * @param excludeCharacter A string of characters to exclude from validation.
	 * @return true if the string meets the criteria, false otherwise.
	 */
	public static boolean isValidAlphaNumeric(String value, boolean spaceCheck, String excludeCharacter) {
		if (isNullOrEmpty(value)) {
			return false;
		}

		// Build the regex pattern dynamically
		StringBuilder patternBuilder = new StringBuilder("^[a-zA-Z0-9");

		// Allow spaces if spaceCheck is true
		if (spaceCheck) {
			patternBuilder.append(" ");
		}

		// Exclude specified characters
		if (excludeCharacter != null && !excludeCharacter.isEmpty()) {
			patternBuilder.append(Pattern.quote(excludeCharacter));
		}

		patternBuilder.append("]+$");

		// Compile the pattern and match it against the input string
		Pattern pattern = Pattern.compile(patternBuilder.toString());
		return pattern.matcher(value).matches();
	}

	/**
	 * check email pattern 
	 * @param email
	 * @return
	 */
	public static boolean isValidEmail(String email) {
		return Pattern.matches(Constants.RegexPattern.EMAIL_PATTERN, email);
	}

	/**
	 * check mobile pattern
	 * @param mobile
	 * @return
	 */
	public static boolean isValidMobile(String mobile) {
		return Pattern.matches(Constants.RegexPattern.MOBILE_PATTERN, mobile);
	}

	public static String validateRequestedField(String fieldName, String fieldValue, boolean nullCheck,
			boolean numericCheck, boolean alphaNumericCheck, boolean lengthCheck, String excludeCharacter, int min,
			int max, boolean spaceCheck) {

		try {
			// Null check
			if (nullCheck && isNullOrEmpty(fieldValue)) {
				throw new RuntimeException(fieldName + Constants.Error.CANNOT_BE_NULL_OR_EMPTY);
//				return fieldName + CommonConstants.ValidateErrorMessage.CANNOT_BE_NULL_OR_EMPTY;
			}
			// Check if the field is not null or empty
			boolean isFieldNotEmpty = !isNullOrEmpty(fieldValue);

			// Proceed with further checks only if the field is not empty
			if (isFieldNotEmpty) {

				// Alphanumeric checks with special character exclusion
				if (alphaNumericCheck) {
					if (excludeCharacter != null && !isValidAlphaNumeric(fieldValue, spaceCheck, excludeCharacter)) {
						String excludedChars = excludeCharacter.contains(Constants.WhiteSpaceCharacter.WHITE_SPACE)
								? Constants.Error.SPACE_AND + excludeCharacter.replace(Constants.WhiteSpaceCharacter.WHITE_SPACE, "")
								: excludeCharacter;
//						return fieldName + CommonConstants.ValidateErrorMessage.SHOULD_BE_ALFA_NUMERIC_OR_CHARACTERS + excludedChars
//								+ CommonConstants.ValidateErrorMessage.ARE_NOT_ALLOWED;
						throw new RuntimeException(fieldName + Constants.Error.SHOULD_BE_ALFA_NUMERIC_OR_CHARACTERS + excludedChars+ Constants.Error.ARE_NOT_ALLOWED);

					}
					if (excludeCharacter == null && !isValidAlphaNumeric(fieldValue, spaceCheck)) {
//						return fieldName + CommonConstants.ValidateErrorMessage.CANNOT_CONTAIN_SPECIAL_CHARACTERS;
						throw new RuntimeException(fieldName + Constants.Error.CANNOT_CONTAIN_SPECIAL_CHARACTERS);

					}
				}

				// Numeric check
				if (numericCheck && !isValidNumericInput(fieldValue)) {
//					return fieldName + CommonConstants.ValidateErrorMessage.SHOULD_BE_NUMERIC_VALUE;
					throw new RuntimeException(fieldName + Constants.Error.SHOULD_BE_NUMERIC_VALUE);

				}

				// Length check
				if (lengthCheck && !isValidLength(fieldValue.trim(), min, max)) {
					if (min == max) {
//						return fieldName + CommonConstants.ValidateErrorMessage.SHOULD_BE_EXACTLY + min + CommonConstants.ValidateErrorMessage.CHARACTERS_LONG;
						throw new RuntimeException(fieldName + Constants.Error.SHOULD_BE_EXACTLY + min + Constants.Error.CHARACTERS_LONG);

					} else {
//						return fieldName + CommonConstants.ValidateErrorMessage.SHOULD_BE_BETWEEN + min + CommonConstants.ValidateErrorMessage.TO + max + CommonConstants.ValidateErrorMessage.CHARACTERS_LONG;
						throw new RuntimeException(fieldName + Constants.Error.SHOULD_BE_BETWEEN + min + Constants.Error.TO + max + Constants.Error.CHARACTERS_LONG);

					}
				}
			}
			// If all checks pass, return null indicating no validation errors
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw new InvalidInputException(e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			throw new InvalidInputException(e.getMessage());

		}
	}
}
