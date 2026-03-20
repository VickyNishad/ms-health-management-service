package com.health.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "doctor_search_index",
        indexes = {
                @Index(name = "idx_name", columnList = "name"),
                @Index(name = "idx_clinic_name", columnList = "clinic_name"),
                @Index(name = "idx_city", columnList = "city"),
                @Index(name = "idx_locality", columnList = "locality"),
                @Index(name = "idx_pin_code", columnList = "pin_code"),
                // 🔥 Composite index (VERY IMPORTANT)
                @Index(name = "idx_search_main", columnList = "city, is_available")
        }
)
public class DoctorSearchIndex extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "clinic_id")
    private Long clinicId;

    @Column(name = "name")
    private String name;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "clinic_name")
    private String clinicName;

    @Column(name = "specialization_names", columnDefinition = "TEXT")
    private String specializationNames;

    private String qualifications;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "city")
    private String city;

    @Column(name = "locality")
    private String locality;

    @Column(name = "clinic_address", columnDefinition = "TEXT")
    private String clinicAddress;

    @Column(name = "total_experience")
    private Integer totalExperience;

    @Column(name = "is_available", columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean isAvailable = true;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getClinicId() {
        return clinicId;
    }

    public void setClinicId(Long clinicId) {
        this.clinicId = clinicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getSpecializationNames() {
        return specializationNames;
    }

    public void setSpecializationNames(String specializationNames) {
        this.specializationNames = specializationNames;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public Integer getTotalExperience() {
        return totalExperience;
    }

    public void setTotalExperience(Integer totalExperience) {
        this.totalExperience = totalExperience;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}