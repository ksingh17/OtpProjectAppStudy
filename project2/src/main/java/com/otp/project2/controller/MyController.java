package com.otp.project2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.otp.project2.entities.Email_Otp;
import com.otp.project2.services.OtpServices;
import com.otp.project2.services.OtpServicesImpl;

@RestController
public class MyController {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyController.class);

	@Autowired
	
	private OtpServices obj;
	
	@Autowired
	private OtpServicesImpl implement;
	
	//send email
	
	@RequestMapping(path="/sendotp", method=RequestMethod.POST)
	public String getEmail(@RequestParam String info , String channel)
	{
		LOGGER.info("Inside getEmail");
		if(channel.equals("email"))
				{
			return implement.getMail(info);
				}
		else {
			return implement.sendSMS(info);
		}
		//return obj.getMail(email);
	}
	
	//validate OTP
	@RequestMapping(path="/validate", method=RequestMethod.POST)
	public String validate(@RequestBody Email_Otp otp)
	{
		LOGGER.info("Inside validateOTP");
		return obj.validateOtp(otp);
	}
	
	
	

}
