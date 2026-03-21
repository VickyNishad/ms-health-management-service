package com.health.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "patients", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "relation" }) })
public class Patient extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserRegistration user;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "gender", nullable = true)
	private String gender;

	@Column(name = "age", nullable = true)
	private Integer age;

	@Column(nullable = true)
	private String relation;

	@Column(name = "mobile_number", nullable = true)
	private String mobileNumber;

	@Column(name = "email_id", nullable = true)
	private String emailId;

	@Column(name = "description", nullable = true)
	private String description;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
