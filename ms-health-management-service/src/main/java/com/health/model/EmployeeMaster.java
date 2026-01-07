/**
 * 
 */
package com.health.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * 
 */

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

@Entity
@Table(name = "employee_master")
public class EmployeeMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "emp_id")
	private Long empId;

	@Column(name = "emp_code", unique = true, nullable = false)
	private String empCode;

	// 🔐 Login user
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private UserRegistration user;

	// 🔑 Role (ADMIN / DOCTOR / NURSE / STAFF)
	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private RoleMaster role;

	// 🔗 Reporting hierarchy (Doctor / Admin)
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "reporting_emp_id")
	private EmployeeMaster reportingTo;

	// 🔗 Staff under Doctor/Admin
	@JsonManagedReference
	@OneToMany(mappedBy = "reportingTo", cascade = CascadeType.ALL)
	private List<EmployeeMaster> staff;

	@Column(name = "department")
	private String department;

	@Column(name = "is_active")
	private Boolean isActive = true;

	/* ===== Getters & Setters ===== */

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public UserRegistration getUser() {
		return user;
	}

	public void setUser(UserRegistration user) {
		this.user = user;
	}

	public RoleMaster getRole() {
		return role;
	}

	public void setRole(RoleMaster role) {
		this.role = role;
	}

	public EmployeeMaster getReportingTo() {
		return reportingTo;
	}

	public void setReportingTo(EmployeeMaster reportingTo) {
		this.reportingTo = reportingTo;
	}

	public List<EmployeeMaster> getStaff() {
		return staff;
	}

	public void setStaff(List<EmployeeMaster> staff) {
		this.staff = staff;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
