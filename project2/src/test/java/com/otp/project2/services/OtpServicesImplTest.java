package com.otp.project2.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.otp.project2.dao.OtpDao;

@SpringBootTest
class OtpServicesImplTest {
	
	@Autowired
	private OtpServicesImpl service;
	
	@MockBean
	private OtpDao dao;
//	@Test
//	void testGetMail() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testValidateOtp() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testSendSMS() {
//		fail("Not yet implemented");
//	}

	@Test
	void testGenerateOtp() {
		//fail("Not yet implemented");
		int expected=6;
		int actual=service.generateOtp().length();
		assertEquals(actual,expected);
		
	}
	
	@Test
	void validEmail()
	{
		String expected="Entered email is not valid";
		String actual=service.getMail("k@singh@gmail.com");
		assertEquals(expected,actual);
	}
	
	@Test
	void test() {
		String expected="Otp sent successfully!";
		String actual=service.getMail("komal.18bcs1189@abes.ac.in");
		assertEquals(expected,actual);
	}
	
	@Test
	void test2()
	{
		String expected="Entered email is not valid";
		String actual=service.getMail(null);
		assertEquals(expected,actual);
	}
	
	
	
}
