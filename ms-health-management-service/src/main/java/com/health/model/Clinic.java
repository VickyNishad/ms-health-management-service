package com.health.model;

import jakarta.persistence.*;

@Entity
@Table(name = "clinics")
public class Clinic extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "clinic_id")
	private Long clinicId;

	@Column(name = "clinic_name", nullable = false)
	private String clinicName;

	@Column(name = "address")
	private String address;

	@Column(name = "contact_number")
	private String contactNumber;

	// ---------- Getters & Setters ----------

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
}
