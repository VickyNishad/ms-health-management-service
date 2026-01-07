package com.health.model;

import jakarta.persistence.*;

@Entity
@Table(
    name = "patients",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "relationship"})
    }
)
public class Patient extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "patient_id")
	private Long patientId;

//	@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserRegistration user;
	
	@Column(name = "patient_name", nullable = false)
	private String patientName;

	@Column(name = "gender", nullable = true)
	private String gender;

	@Column(name = "age", nullable = true)
	private Integer age;
	
	@Column(nullable = false)
	private String relationship; // Father, Mother, Son, etc.

	@Column(name = "contact_number", nullable = true)
	private String contactNumber;

	// ---------- Getters & Setters ----------

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public UserRegistration getUser() {
		return user;
	}

	public void setUser(UserRegistration user) {
		this.user = user;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
}
