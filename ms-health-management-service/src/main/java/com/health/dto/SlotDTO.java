package com.health.dto;
import java.time.LocalTime;

public class SlotDTO {
	private Long slotId;
    private LocalTime slotTime;
    private int token;
    private String status;
    private Boolean isBooked;

    // Constructor
    public SlotDTO(Long slotId ,LocalTime slotTime, int token, String status, Boolean isBooked) {
    	this.slotId = slotId;
        this.slotTime = slotTime;
        this.token = token;
        this.status = status;
        this.isBooked = isBooked;
    }

    // Getters & Setters
    public void setSlotId(Long slotId) {
		this.slotId = slotId;
	}
    public Long getSlotId() {
		return slotId;
	}
    public Boolean getIsBooked() {
		return isBooked;
	}
    public LocalTime getSlotTime() { return slotTime; }
    public void setSlotTime(LocalTime slotTime) { this.slotTime = slotTime; }
    public int getToken() { return token; }
    public void setToken(int token) { this.token = token; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public void setBooked(Boolean booked) { isBooked = booked; }
}
