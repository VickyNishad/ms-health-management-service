package com.health.dto.response;

import java.util.List;

public class DoctorPersonalDetailsDto {

    private Long doctorId;
    private String name;
    private String neekName;
    private int age;
    private String gender;
    private String mobileNumber;
    private String emailId;
    private String registrationNumber;
    private int totalExperience;
    private List<MasterSummary> qualifications;
    private List<MasterSummary> specializations;

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeekName() {
        return neekName;
    }

    public void setNeekName(String neekName) {
        this.neekName = neekName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getTotalExperience() {
        return totalExperience;
    }

    public void setTotalExperience(int totalExperience) {
        this.totalExperience = totalExperience;
    }

    public List<MasterSummary> getQualifications() {
        return qualifications;
    }

    public void setQualifications(List<MasterSummary> qualifications) {
        this.qualifications = qualifications;
    }

    public List<MasterSummary> getSpecializations() {
        return specializations;
    }

    public void setSpecializations(List<MasterSummary> specializations) {
        this.specializations = specializations;
    }
}
