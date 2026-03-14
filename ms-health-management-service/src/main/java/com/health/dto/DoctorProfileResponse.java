package com.health.dto;

import java.util.List;

public class DoctorProfileResponse {

    private Long doctorId;
    private String fullName;
    private String registrationNumber;
    private Integer experienceYears;
    private List<String> specializations;
    private List<String> qualifications;
    private List<ClinicResponse> clinics;

    // inner DTO for clinic
    public static class ClinicResponse {
        private Long clinicId;
        private String clinicName;
        private String address;
        private String contactNumber;

        // Getters and Setters
        public Long getClinicId() { return clinicId; }
        public void setClinicId(Long clinicId) { this.clinicId = clinicId; }
        public String getClinicName() { return clinicName; }
        public void setClinicName(String clinicName) { this.clinicName = clinicName; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getContactNumber() { return contactNumber; }
        public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
    }

    // Getters and Setters
    public Long getDoctorId() { return doctorId; }
    public void setDoctorId(Long doctorId) { this.doctorId = doctorId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public Integer getExperienceYears() { return experienceYears; }
    public void setExperienceYears(Integer experienceYears) { this.experienceYears = experienceYears; }

    public List<String> getSpecializations() { return specializations; }
    public void setSpecializations(List<String> specializations) { this.specializations = specializations; }

    public List<String> getQualifications() { return qualifications; }
    public void setQualifications(List<String> qualifications) { this.qualifications = qualifications; }

    public List<ClinicResponse> getClinics() { return clinics; }
    public void setClinics(List<ClinicResponse> clinics) { this.clinics = clinics; }
}
