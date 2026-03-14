package com.health.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "doctor_specializations",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"doctor_id", "specialization_id"})
    }
)
public class DoctorSpecialization extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialization_id", nullable = false)
    private SpecializationMaster specialization;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public SpecializationMaster getSpecialization() {
        return specialization;
    }

    public void setSpecialization(SpecializationMaster specialization) {
        this.specialization = specialization;
    }


}
