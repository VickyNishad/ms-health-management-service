package com.health.constants;

/**
 * Contains application-wide constants that are used across the project.
 */
public class Constants {

	public static final class RegexPattern {
		// Email Regex Pattern (basic, suitable for most cases)
		public static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

		// Mobile Regex Pattern (Indian 10-digit format, starting with 6-9)
		public static final String MOBILE_PATTERN = "^[6-9]\\d{9}$";
	}

	public static final class WhiteSpaceCharacter {
		// Define constants for different types of whitespace characters
		public static final String TAB = "\t"; // Tab
		public static final String SPACE = " "; // Space
		public static final String NEWLINE = "\n"; // Newline
		public static final String FORM_FEED = "\f"; // Form feed
		public static final String WHITE_SPACE = "\\s"; // White Space Character
		public static final String VERTICAL_TAB = "\u000B"; // Vertical tab
		public static final String EMPTY_STRING = ""; // Empty string constant
		public static final String CARRIAGE_RETURN = "\r"; // Carriage return

	}
	
	
	 public static final class Error {
	    	
	    	public static final String TO = " to ";
	    	public static final String SPACE_AND = "Space and ";  	
	    	public static final String CHARACTERS_LONG = " characters long.";
	    	public static final String ARE_NOT_ALLOWED = " are not allowed";
	    	public static final String SHOULD_BE_EXACTLY = " should be exactly ";
	    	public static final String SHOULD_BE_BETWEEN = " should be between ";
	    	public static final String CANNOT_BE_NULL_OR_EMPTY = " cannot be null or empty";
	    	public static final String SHOULD_BE_NUMERIC_VALUE = " should be a valid numeric value.";
//	    	public static final String CANNOT_CONTAIN_SPECIAL_CHARACTERS = " cannot contain special characters.";
	    	public static final String SHOULD_BE_ALFA_NUMERIC_OR_CHARACTERS = " should be alphanumeric. Characters other than ";


	    }
}
