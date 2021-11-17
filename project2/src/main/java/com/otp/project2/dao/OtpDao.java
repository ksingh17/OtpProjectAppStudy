package com.otp.project2.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otp.project2.entities.Email_Otp;
public interface OtpDao extends JpaRepository<Email_Otp,String>{

}


