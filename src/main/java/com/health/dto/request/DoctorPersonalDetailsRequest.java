package com.health.dto.request;

import com.health.entity.DoctorLeave;

import java.util.List;

public class DoctorPersonalDetailsRequest {
    private String name;
    private int age;
    private String gender;
    private String mobileNumber;
    private String emailId;
    private String registrationNumber;
    private int totalExperience;
    private List<Long> qualifications;
    private String neekName;
    private List<Long> specializations;

    public int getAge() {
        return age;
    }

    public String getRegistrationNumber() {

        return registrationNumber;
    }

    public int getTotalExperience() {
        return totalExperience;
    }

    public List<Long> getQualifications() {
        return qualifications;
    }

    public List<Long> getSpecializations() {
        return specializations;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getGender() {
        return gender;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getName() {
        return name;
    }

    public String getNeekName() {
        return neekName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNeekName(String neekName) {
        this.neekName = neekName;
    }

    public void setQualifications(List<Long> qualifications) {
        this.qualifications = qualifications;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setSpecializations(List<Long> specializations) {
        this.specializations = specializations;
    }

    public void setTotalExperience(int totalExperience) {
        this.totalExperience = totalExperience;
    }

}
