package com.health.entity;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "employee_details")
public class EmployeeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserRegistration user;

    @ManyToOne
    @JoinColumn(name = "reporting_id")
    private EmployeeDetails reportingManager;

    @Column(name = "designation")
    private String designation;

    @Column(name = "department")
    private String department;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column(name = "salary")
    private Double salary;

}