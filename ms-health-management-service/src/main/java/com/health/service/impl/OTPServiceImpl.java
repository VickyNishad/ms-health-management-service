/**
 * 
 */
package com.health.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.entity.OtpMaster;
import com.health.repository.OTPMasterRepository;
import com.health.service.OTPService;
import com.health.utility.HealthUtils;

/**
 * 
 */
@Service
public class OTPServiceImpl implements OTPService {
	
	@Autowired
	private OTPMasterRepository otpMasterRepository;

	@Override
	public String sendOTP(String mobileNumber) {
		// TODO Auto-generated method stub
		OtpMaster otpMaster = new OtpMaster();
		
		Optional<OtpMaster> optionalOtp =
				otpMasterRepository.findTopByMobileNumberOrderByCreatedAtDesc(mobileNumber);

        if(optionalOtp.isPresent()) {

            OtpMaster lastOtp = optionalOtp.get();

            if(lastOtp.getIsVerified() == false) {
            	
                long seconds = Duration.between(
                        lastOtp.getCreatedAt(),
                        LocalDateTime.now()
                ).getSeconds();

                if(seconds < 30) {
                    throw new RuntimeException(
                            "Please wait " + (30 - seconds) + " seconds before requesting new OTP"
                    );
                }
            }
        }
		
		String otp = HealthUtils.generateOtp();
		
		otpMaster.setMobileNumber(mobileNumber);
		otpMaster.setExpiryTime(LocalDateTime.now().plusMinutes(5));
		otpMaster.setIsVerified(false);
		otpMaster.setOtpCode(otp);
		otpMaster.setOtpType("SMS");
		otpMaster.setCreatedAt(null);
		otpMaster.setCreatedBy(mobileNumber);
		otpMaster.setIsActive(true);
	
		otpMasterRepository.save(otpMaster);

		return otp;
	}

	@Override
	public boolean verifyOTP(String mobileNumber, String otp) {
		// TODO Auto-generated method stub
	    Optional<OtpMaster> optionalOtp =
	    		otpMasterRepository.findTopByMobileNumberOrderByCreatedAtDesc(mobileNumber);

	    if(optionalOtp.isEmpty()) {
	        throw new RuntimeException("OTP not found");
	    }

	    OtpMaster otpMaster = optionalOtp.get();

	    if(otpMaster.getIsVerified()) {
	        throw new RuntimeException("OTP already used");
	    }

	    if(!otpMaster.getOtpCode().equals(otp)) {
	        throw new RuntimeException("Invalid OTP");
	    }

	    if(LocalDateTime.now().isAfter(otpMaster.getExpiryTime())) {
	        throw new RuntimeException("OTP expired");
	    }

	    otpMaster.setIsVerified(true);
	    otpMaster.setOtpCode(otp);
	    otpMaster.setUpdatedAt(LocalDateTime.now());
	    otpMaster.setUpdatedBy(mobileNumber);

	    otpMasterRepository.save(otpMaster);
	    return true;
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
