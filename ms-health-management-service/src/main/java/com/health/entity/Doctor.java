package com.health.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "doctors")
public class Doctor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserRegistration user;
    
    @Column(name = "name")
    private String name;

    @Column(name = "neek_name")
    private String neekName;

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

    @Column(name = "total_experience")
    private Integer totalExperience;

    
}
