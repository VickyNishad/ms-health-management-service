package com.health.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "role_master")
public class RoleMaster extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true)
    private String roleName;

    @ManyToOne
    @JoinColumn(name = "parent_role_id")
    private RoleMaster parentRole;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

}
