package com.health.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNeekName() {
        return neekName;
    }

    public String getName() {
        return name;
    }

    public UserRegistration getUser() {
        return user;
    }

    public Integer getTotalExperience() {
        return totalExperience;
    }

    public List<QualificationMaster> getQualifications() {
        return qualifications;
    }

    public List<SpecializationMaster> getSpecializations() {
        return specializations;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setSpecializations(List<SpecializationMaster> specializations) {
        this.specializations = specializations;
    }

    public void setQualifications(List<QualificationMaster> qualifications) {
        this.qualifications = qualifications;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setNeekName(String neekName) {
        this.neekName = neekName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalExperience(Integer totalExperience) {
        this.totalExperience = totalExperience;
    }

    public void setUser(UserRegistration user) {
        this.user = user;
    }

}
