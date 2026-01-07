package com.health.dto;

import java.util.List;

public class ClinicAvailabilityResponse {
    private Long clinicId;
    private String clinicName;

    private List<AvailabilityItem> availabilityList;


    public static class AvailabilityItem {
        private Long availabilityId;
        private String dayOfWeek;
        private String startTime;
        private String endTime;
        private Integer slotDuration;
		public Long getAvailabilityId() {
			return availabilityId;
		}
		public void setAvailabilityId(Long availabilityId) {
			this.availabilityId = availabilityId;
		}
		public String getDayOfWeek() {
			return dayOfWeek;
		}
		public void setDayOfWeek(String dayOfWeek) {
			this.dayOfWeek = dayOfWeek;
		}
		public String getStartTime() {
			return startTime;
		}
		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
		public Integer getSlotDuration() {
			return slotDuration;
		}
		public void setSlotDuration(Integer slotDuration) {
			this.slotDuration = slotDuration;
		}
        
        
    }


	public Long getClinicId() {
		return clinicId;
	}


	public void setClinicId(Long clinicId) {
		this.clinicId = clinicId;
	}


	public String getClinicName() {
		return clinicName;
	}


	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}


	public List<AvailabilityItem> getAvailabilityList() {
		return availabilityList;
	}


	public void setAvailabilityList(List<AvailabilityItem> availabilityList) {
		this.availabilityList = availabilityList;
	}
    
    
}
