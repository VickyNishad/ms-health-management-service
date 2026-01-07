/**
 * 
 */
package com.health.enums;

/**
 * 
 */
public enum DeviceOSType {
	ANDROID,
	IOS,
	TABLET;
	
    public static DeviceOSType fromString(String value) {
        for (DeviceOSType type : DeviceOSType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid action type: " + value);
    }
}
