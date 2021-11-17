package com.otp.project2.services;

import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otp.project2.controller.MyController;
import com.otp.project2.dao.OtpDao;
import com.otp.project2.entities.Email_Otp;

@Service
public class OtpServicesImpl implements OtpServices{
	private static final Logger LOGGER = LoggerFactory.getLogger(OtpServicesImpl.class);

	@Autowired
	private OtpDao otpDao;
	
	public boolean isValidEmail(String email)
	{
	// LOGGER.info("Inside Is valid EMail");
	String emailRegex = "\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
	                 
	Pattern pat = Pattern.compile(emailRegex);
	if (email == null)
	return false;
	return pat.matcher(email).matches();
	}
	
	@Override
	public String getMail(String email) {
		
		
		LOGGER.info("Inside getMail");
		// TODO Auto-generated method stub
		
		
		String generateOTP=generateOtp();
		Email_Otp searchedmail=otpDao.findById(email).orElse(null);
		
		if(searchedmail==null)
		{
			
			if(isValidEmail(email)==false)
			{
				return "Entered email is not valid";
			}
			
			else {
				
			Email_Otp newEmail=new Email_Otp();
			newEmail.setEmail(email);
			newEmail.setOtp(generateOTP);
			newEmail.setAttempts(4);
			newEmail.setOtpGenerationTine(System.currentTimeMillis()+1000);
			newEmail.setExpiryTime(System.currentTimeMillis()+60000);
			newEmail.setSession(System.currentTimeMillis()+180000);
			otpDao.save(newEmail);
			}	return "Otp sent successfully!";			
		}
		
		else if(searchedmail.getAttempts()==0 && searchedmail.getSession()>System.currentTimeMillis())
		{
			return "You exceeded the limit. Try after "+((searchedmail.getSession()-System.currentTimeMillis())/1000)+"seconds";
			
		}
		else {
			
		// Sender's email ID needs to be mentioned
        String from = "singh.komal1702@gmail.com";

        
     // Recipient's email ID needs to be mentioned.
        String to = email;

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        //props.put("mail.smtp.starttls.enable", "true"); 

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("singh.komal1702@gmail.com", "Koms@1702_singh");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("OTP for validation");

            // Now set the actual message
            message.setText("This is OTP for Validation-" + generateOTP);

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
            
            int x = ((searchedmail.getAttempts()-1)<0)?4:(searchedmail.getAttempts()-1);
            
			searchedmail.setAttempts(x);
			searchedmail.setOtp(generateOTP);
			searchedmail.setExpiryTime(System.currentTimeMillis()+60000);
			
			
			if(searchedmail.getSession()<System.currentTimeMillis())
				searchedmail.setSession(System.currentTimeMillis()+180000);
			
			otpDao.save(searchedmail);
            
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
		
		
		
		return "Email sent Successfully!!!!!!!";
		}
	}

	@Override
	public String validateOtp(Email_Otp otp) {
		LOGGER.info("Inside validateotp");
		// TODO Auto-generated method stub
		
		String newMail=otp.getEmail();
		String newOtp=otp.getOtp();
		
		Email_Otp searchedEmail=otpDao.findById(newMail).orElse(null);
		long time=searchedEmail.getOtpGenerationTime();
		
		long timeDiff=(System.currentTimeMillis()-time)/1000;
		
		System.out.println("time difference is-"+timeDiff);
		if(timeDiff>60)
		{
			return "OTP expired!!!";
		}
		else if(searchedEmail.getEmail().equals(newMail))
		{
			return "OTP SUccessfully validated!!!!!!";
		}
		else {
			return "Invalid OTP!!!!!!!!";
		}
		
		
	}
	
	public String sendSMS(String mobile)
	{
		String generateOTP=generateOtp();

		Email_Otp searchedMobile=otpDao.findById(mobile).orElse(null);
		
		if(searchedMobile==null)
		{
		
		
		Email_Otp emailobj=new Email_Otp();
		emailobj.setEmail(mobile);
		String otp=generateOtp();
		emailobj.setOtp(otp);
		emailobj.setOtpGenerationTine(System.currentTimeMillis()+1000);
		emailobj.setExpiryTime(System.currentTimeMillis()+60000);
		emailobj.setSession(180000+System.currentTimeMillis());
		otpDao.save(emailobj);
		return "Entry saved"
;		}
		
		else if(searchedMobile.getAttempts()==0 && searchedMobile.getSession()>System.currentTimeMillis())
		{
			return "No. of attempts limit exceeded!. Try After- "+((searchedMobile.getSession()-System.currentTimeMillis())/1000)+"Seconds";
			
		}
		
		else
		{
			
			
			int x = ((searchedMobile.getAttempts()-1)<0)?4:(searchedMobile.getAttempts()-1);
			
			searchedMobile.setAttempts(x);
			searchedMobile.setOtp(generateOTP);
			searchedMobile.setExpiryTime(System.currentTimeMillis());
			
			if(searchedMobile.getSession()<System.currentTimeMillis())
				searchedMobile.setSession(System.currentTimeMillis()+18000);
			
			otpDao.save(searchedMobile);

			return "OTP successfully sent on the mobile number";
		}
		
	}
	
	
	
	//generate otp
	public String generateOtp()
	{
		LOGGER.info("Inside generateOTP using email");
		Random r=new Random();
		int number=r.nextInt(999999);
		return String.format("%06d", number);
	}

}
