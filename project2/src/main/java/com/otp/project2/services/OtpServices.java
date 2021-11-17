package com.otp.project2.services;

import com.otp.project2.entities.Email_Otp;

public interface OtpServices {
	
	public String getMail(String email);
	
	public String validateOtp(Email_Otp otp);
	
	public String sendSMS(String info);

}
