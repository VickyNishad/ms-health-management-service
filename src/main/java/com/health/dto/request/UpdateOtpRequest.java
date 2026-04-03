package com.health.dto.request;

public class UpdateOtpRequest {
    private String mobileNumber;
    private String otp;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getOtp() {
        return otp;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
