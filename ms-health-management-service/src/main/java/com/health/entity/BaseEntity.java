package com.health.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {

	@Column(name = "created_by")
	private String created_by;

	@Column(name = "updated_by")
	private String updated_by;

	@Column(name = "is_active", nullable = false)
	private Boolean is_active = true;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime created_at;

	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updated_at;

	@PrePersist
	protected void onCreate() {
		this.created_at = LocalDateTime.now();
		this.updated_at = LocalDateTime.now();
		if (this.is_active == null) {
			this.is_active = true;
		}
	}

	@PreUpdate
	protected void onUpdate() {
		this.updated_at = LocalDateTime.now();
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public Boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(Boolean is_active) {
		this.is_active = is_active;
	}

	public LocalDateTime getCreated_at() {
		return created_at;
	}

	public void setCreated_at(LocalDateTime created_at) {
		this.created_at = created_at;
	}

	public LocalDateTime getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(LocalDateTime updated_at) {
		this.updated_at = updated_at;
	}

}
