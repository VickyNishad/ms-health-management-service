/**
 *
 */
package com.health.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import com.health.entity.UserOtp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.dto.response.MessageResponse;
import com.health.models.ApiResponse;
import com.health.repository.OTPMasterRepository;
import com.health.service.OTPService;
import com.health.utility.ApiExecutionUtils;
import com.health.utility.HealthUtils;

/**
 *
 */
@Service
public class OTPServiceImpl implements OTPService {

    @Autowired
    private OTPMasterRepository otpMasterRepository;

    @Override
    public ApiResponse<MessageResponse> sendOTP(String mobileNumber) {
        // TODO Auto-generated method stub

        return ApiExecutionUtils.ApiExecutor.processRequest(null, null, () -> {
            UserOtp userOtp = new UserOtp();

            Optional<UserOtp> optionalOtp = otpMasterRepository
                    .findTopByMobileNumberOrderByCreatedAtDesc(mobileNumber);

            if (optionalOtp.isPresent()) {

                UserOtp lastOtp = optionalOtp.get();

                if (lastOtp.getIsVerified() == false) {

                    long seconds = Duration.between(lastOtp.getCreatedAt(), LocalDateTime.now()).getSeconds();

                    if (seconds < 30) {
                        throw new RuntimeException(
                                "Please wait " + (30 - seconds) + " seconds before requesting new OTP");
                    }
                }
            }

            String otp = HealthUtils.generateOtp();

            userOtp.setMobileNumber(mobileNumber);
            userOtp.setExpiryTime(LocalDateTime.now().plusMinutes(5));
            userOtp.setIsVerified(false);
            userOtp.setOtpCode(otp);
            userOtp.setOtpType("SMS");
            userOtp.setCreatedAt(null);
            userOtp.setCreatedBy(mobileNumber);
            userOtp.setIsActive(true);

            otpMasterRepository.save(userOtp);
            return new MessageResponse("OTP sent successfully.");
        }, ApiResponse::success);
    }

    @Override
    public ApiResponse<MessageResponse> verifyOTP(String mobileNumber, String otp) {
        // TODO Auto-generated method stub

        return ApiExecutionUtils.ApiExecutor.processRequest(null, null, () -> {
            Optional<UserOtp> optionalOtp = otpMasterRepository
                    .findTopByMobileNumberOrderByCreatedAtDesc(mobileNumber);

            if (optionalOtp.isEmpty()) {
                throw new RuntimeException("OTP not found");
            }

            UserOtp otpMaster = optionalOtp.get();

            if (otpMaster.getIsVerified()) {
                throw new RuntimeException("OTP already used");
            }

            if (!otpMaster.getOtpCode().equals(otp)) {
                throw new RuntimeException("Invalid OTP");
            }

            if (LocalDateTime.now().isAfter(otpMaster.getExpiryTime())) {
                throw new RuntimeException("OTP expired");
            }

            otpMaster.setIsVerified(true);
            otpMaster.setOtpCode(otp);
            otpMaster.setUpdatedAt(LocalDateTime.now());
            otpMaster.setUpdatedBy(mobileNumber);

            otpMasterRepository.save(otpMaster);
            return new MessageResponse("OTP verify successfully.");
        }, ApiResponse::success);

    }

    @Override
    public boolean existsByMobileNumber(String mobileNumber) {
        // TODO Auto-generated method stub
        return otpMasterRepository.existsByMobileNumberAndIsVerifiedTrue(mobileNumber);
    }

    @Override
    public boolean existsByEmailId(String emailId) {
        // TODO Auto-generated method stub
        return otpMasterRepository.existsByEmailIdAndIsVerifiedTrue(emailId);
    }

}
