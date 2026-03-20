package com.health.dto.response;

public class DoctorSearchSummary {

    private String doctorName;
    private Long doctorId;
    private String profilePicture;
    private String clinicName;
    private Long clinicId;
    private String specializationNames;
    private String qualifications;
    private Integer totalExperience;
    private String clinicAddress;
    private Boolean isAvailable = true;

    public DoctorSearchSummary(String doctorName, Long doctorId, String profilePicture, String clinicName, Long clinicId, String specializationNames, String qualifications, Integer totalExperience,String clinicAddress, Boolean isAvailable) {
        this.doctorName = doctorName;
        this.doctorId = doctorId;
        this.profilePicture = profilePicture;
        this.clinicName = clinicName;
        this.clinicId = clinicId;
        this.specializationNames = specializationNames;
        this.qualifications = qualifications;
        this.totalExperience = totalExperience;
        this.clinicAddress = clinicAddress;
        this.isAvailable = isAvailable;
    }


    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getClinicName() {
        return clinicName;
    }

    public Integer getTotalExperience() {
        return totalExperience;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public Long getClinicId() {
        return clinicId;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public String getQualifications() {
        return qualifications;
    }

    public String getSpecializationNames() {
        return specializationNames;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public void setClinicId(Long clinicId) {
        this.clinicId = clinicId;
    }

    public void setSpecializationNames(String specializationNames) {
        this.specializationNames = specializationNames;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public void setTotalExperience(Integer totalExperience) {
        this.totalExperience = totalExperience;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }
}
