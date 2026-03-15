package com.health.dto.response;

public class ClinicDetailsDto {

    private Long id;

    private String clinicName;

    private String address;

    private String contactNumber;

    private int pinCode;

    private Double fee;

    private String locality;

    private String city;

    private String state;

    private String country;

    public String getContactNumber() {
        return contactNumber;
    }

    public String getLocality() {
        return locality;
    }

    public String getClinicName() {
        return clinicName;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public Double getFee() {
        return fee;
    }

    public Long getId() {
        return id;
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

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public int getPinCode() {
        return pinCode;
    }
}
