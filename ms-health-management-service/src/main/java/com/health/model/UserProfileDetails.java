/**
 * 
 */
package com.health.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * 
 */

@Entity
@Table(name = "user_profile_details")
public class UserProfileDetails {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profileId;
    
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserRegistration user;
	
	@Column(name = "name", nullable = true)
	private String name;
	
	@Column(name = "age",nullable = true)
	private String age;

	@Column(name = "gender",nullable = true)
	private String gender;
	
	@Column(name = "date_of_birth",nullable = true)
	private LocalDate dateOfBirth;
	
	@Column(name = "email_id",nullable = true)
	private String emailId;
	
	@Column(name = "contact_number",nullable = true)
	private String contactNumber;
	
	@Column(name = "profile_picture")
	private String profilePicture;
	
	@Column(name = "is_email_verified")
	private Boolean isEmailVerified = false;

	@Column(name = "is_mobile_verified")
	private Boolean isMobileVerified = false;

	public Long getProfileId() {
		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

	public UserRegistration getUser() {
		return user;
	}

	public void setUser(UserRegistration user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Boolean getIsEmailVerified() {
		return isEmailVerified;
	}

	public void setIsEmailVerified(Boolean isEmailVerified) {
		this.isEmailVerified = isEmailVerified;
	}

	public Boolean getIsMobileVerified() {
		return isMobileVerified;
	}

	public void setIsMobileVerified(Boolean isMobileVerified) {
		this.isMobileVerified = isMobileVerified;
	}

	@Override
	public String toString() {
		return "UserProfileDetails [profileId=" + profileId + ", user=" + user + ", name=" + name + ", age=" + age
				+ ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", emailId=" + emailId + ", contactNumber="
				+ contactNumber + ", profilePicture=" + profilePicture + ", isEmailVerified=" + isEmailVerified
				+ ", isMobileVerified=" + isMobileVerified + "]";
	}
	
	
}
