package com.health.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "lead_employee_mapping")
public class LeadEmployeeMapping extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lead_id")
    private Lead lead;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeDetails employee;

    @Column(name = "assigned_at")
    private LocalDateTime assignedAt;

    @Column(name = "assigned_by")
    private Long assignedBy;

}