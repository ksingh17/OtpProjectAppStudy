package com.otp.project2.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Email_Otp {
	@Id
	private String email;
	private String otp;
	private long otpGenerationTime;
	private long expiryTime;
	private long session;
	private int attempts;
	
	
	public Email_Otp() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Email_Otp(String email, String otp, long otpGenerationTime, long expiryTime, long session, int attempts) {
		super();
		
		this.attempts=attempts;
		this.expiryTime=expiryTime;
		this.session=session;
		this.email = email;
		this.otp = otp;
		this.otpGenerationTime = otpGenerationTime;
	}
	
	
	
	public int getAttempts() {
		return attempts;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	public long getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(long expiryTime) {
		this.expiryTime = expiryTime;
	}
	public long getSession() {
		return session;
	}
	public void setSession(long session) {
		this.session = session;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public Long getOtpGenerationTime() {
		return otpGenerationTime;
	}
	public void setOtpGenerationTine(Long otpGenerationTime) {
		this.otpGenerationTime = otpGenerationTime;
	}
	@Override
	public String toString() {
		return "Email_Otp [email=" + email + ", otp=" + otp + ", otpGenerationTime=" + otpGenerationTime
				+ ", expiryTime=" + expiryTime + ", session=" + session + ", attempts=" + attempts + "]";
	}
	
	
	
	
	
	
	
	

}
