/**
 * 
 */
package com.health.domain.model;

import java.util.List;

import com.health.model.UserRegistration;



/**
 * 
 */
public class DoctorModel {

	private Long doctorId;

	private UserRegistration user;

	private String specialization;

	private String qualification;

	private String registrationNumber;

	private List<Long> specializations;

	private List<Long> qualifications;

	private Integer experienceYears;

	private String createdBy;
	private String updatedBy;

	public Long getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Long doctorId) {
		this.doctorId = doctorId;
	}

	public UserRegistration getUser() {
		return user;
	}

	public void setUser(UserRegistration user) {
		this.user = user;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public List<Long> getSpecializations() {
		return specializations;
	}

	public void setSpecializations(List<Long> specializations) {
		this.specializations = specializations;
	}

	public List<Long> getQualifications() {
		return qualifications;
	}

	public void setQualifications(List<Long> qualifications) {
		this.qualifications = qualifications;
	}

	public Integer getExperienceYears() {
		return experienceYears;
	}

	public void setExperienceYears(Integer experienceYears) {
		this.experienceYears = experienceYears;
	}

}
