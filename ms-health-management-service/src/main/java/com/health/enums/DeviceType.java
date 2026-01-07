package com.health.enums;

/**
 * 
 */
public enum DeviceType {
	MOBILE,
	WEB,
	TABLET,
	DESKTOP;
	
    public static DeviceType fromString(String value) {
        for (DeviceType type : DeviceType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid action type: " + value);
    }
}
