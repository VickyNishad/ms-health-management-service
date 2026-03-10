package com.health.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "qualification_master")
public class QualificationMaster extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "qualification_name",nullable = false, unique = true)
	private String qualificationName;

	@Column
	private String description;

	public String getQualificationName() {
		return qualificationName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setQualificationName(String qualificationName) {
		this.qualificationName = qualificationName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
