package com.health.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
public class Appointment extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserRegistration user;

	@ManyToOne
	@JoinColumn(name = "patient_id", nullable = false)
	private Patient patient;

	@ManyToOne
	@JoinColumn(name = "doctor_id", nullable = false)
	private Doctor doctor;

	@ManyToOne
	@JoinColumn(name = "doctor_slot_id", nullable = false)
	private DoctorSlot doctorSlot;

	@Column(name = "clinic_id", nullable = false)
	private Long clinicId;

	@Column(name = "appointment_date", nullable = false)
	private LocalDate appointmentDate;

	@Column(name = "appointment_time", nullable = false)
	private LocalTime appointmentTime;

	@Column(name = "token_number")
	private Integer tokenNumber;

	/* ---------- STATUS ---------- */

	@Column(name = "slot_status", length = 20)
	private String slotStatus; // BOOKED / CANCELLED

	@Column(name = "appointment_status", length = 20)
	private String appointmentStatus; // ACTIVE / COMPLETED / CANCELLED

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserRegistration getUser() {
		return user;
	}

	public void setUser(UserRegistration user) {
		this.user = user;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public DoctorSlot getDoctorSlot() {
		return doctorSlot;
	}

	public void setDoctorSlot(DoctorSlot doctorSlot) {
		this.doctorSlot = doctorSlot;
	}

	public Long getClinicId() {
		return clinicId;
	}

	public void setClinicId(Long clinicId) {
		this.clinicId = clinicId;
	}

	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public LocalTime getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(LocalTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public Integer getTokenNumber() {
		return tokenNumber;
	}

	public void setTokenNumber(Integer tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

	public String getSlotStatus() {
		return slotStatus;
	}

	public void setSlotStatus(String slotStatus) {
		this.slotStatus = slotStatus;
	}

	public String getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(String appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}

}
