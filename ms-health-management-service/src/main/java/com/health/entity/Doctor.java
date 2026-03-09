package com.health.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Long doctorId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserRegistration user;
    
    @Column(name = "full_name")
    private String fullName;

    @Column(name = "neek_name")
    private String neekName;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "registration_number", unique = true)
    private String registrationNumber;

    @ManyToMany
    @JoinTable(
        name = "doctor_specializations",
        joinColumns = @JoinColumn(name = "doctor_id"),
        inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private List<SpecializationMaster> specializations;

    @ManyToMany
    @JoinTable(
        name = "doctor_qualifications",
        joinColumns = @JoinColumn(name = "doctor_id"),
        inverseJoinColumns = @JoinColumn(name = "qualification_id")
    )
    private List<QualificationMaster> qualifications;

    @Column(name = "experience_years")
    private Integer experienceYears;

    // ---------- Getters & Setters ----------

    public Long getDoctorId() {
        return doctorId;
    }

    public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getNeekName() {
		return neekName;
	}

	public void setNeekName(String neekName) {
		this.neekName = neekName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
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

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public List<SpecializationMaster> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<SpecializationMaster> specializations) {
        this.specializations = specializations;
    }

    public List<QualificationMaster> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<QualificationMaster> qualifications) {
        this.qualifications = qualifications;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }
}
