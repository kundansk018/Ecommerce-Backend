package com.restapi.service;



import com.restapi.entity.EmailDetails;


public interface EmailService {


	String sendSimpleMail(EmailDetails details);

	
	String sendMailWithAttachment(EmailDetails details);
	
	void sendMail(String to,String subject , String text);

}
