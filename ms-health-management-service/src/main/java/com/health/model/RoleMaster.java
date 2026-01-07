package com.health.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles_master")
public class RoleMaster extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id")
	private Long roleId;

	@Column(name = "role_name", nullable = false, unique = true)
	private String roleName;

	@ManyToOne
	@JoinColumn(name = "parent_role_id")
	private RoleMaster parentRole;

	// ---------- Getters & Setters ----------
	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public RoleMaster getParentRole() {
		return parentRole;
	}

	public void setParentRole(RoleMaster parentRole) {
		this.parentRole = parentRole;
	}

	@Override
	public String toString() {
		return "RoleMaster [roleId=" + roleId + ", roleName=" + roleName + ", parentRole=" + parentRole + "]";
	}
	
	
}
