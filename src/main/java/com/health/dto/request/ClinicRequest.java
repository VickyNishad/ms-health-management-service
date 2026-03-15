package com.health.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClinicRequest {

    private String clinicName;

    private String address;

    private String contactNumber;

    private Double fee;

    private int pinCode;

    private String locality;

    private String city;

    private String state;

    private String country;

    public String getClinicName() {
        return clinicName;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }

    public String getLocality() {
        return locality;
    }

    public Double getFee() {
        return fee;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public int getPinCode() {
        return pinCode;
    }
}
