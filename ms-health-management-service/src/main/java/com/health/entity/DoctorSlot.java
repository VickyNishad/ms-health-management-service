package com.health.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;

@Entity
@Table(name = "doctor_slots")
public class DoctorSlot extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long slotId;

	@Column(name = "doctor_id")
	private Long doctorId;

	@Column(name = "clinic_id")
	private Long clinicId;

	@Column(name = "slot_date")
	private LocalDate slotDate;

	@Column(name = "slot_time")
	private LocalTime slotTime;

	@Column(name = "token_number")
	private Integer token;

	@Column(name = "status")
	private String status; // AVAILABLE, BREAK, LUNCH, LEAVE, BOOKED

	@Column(name = "is_booked")
	private Boolean isBooked = false;

	public Long getSlotId() {
		return slotId;
	}

	public void setSlotId(Long slotId) {
		this.slotId = slotId;
	}

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public Long getClinicId() {
		return clinicId;
	}

	public void setClinicId(Long clinicId) {
		this.clinicId = clinicId;
	}

	public LocalDate getSlotDate() {
		return slotDate;
	}

	public void setSlotDate(LocalDate slotDate) {
		this.slotDate = slotDate;
	}

	public LocalTime getSlotTime() {
		return slotTime;
	}

	public void setSlotTime(LocalTime slotTime) {
		this.slotTime = slotTime;
	}

	public Integer getToken() {
		return token;
	}

	public void setToken(Integer token) {
		this.token = token;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getIsBooked() {
		return isBooked;
	}

	public void setIsBooked(Boolean isBooked) {
		this.isBooked = isBooked;
	}

	// getters + setters
	
	

}