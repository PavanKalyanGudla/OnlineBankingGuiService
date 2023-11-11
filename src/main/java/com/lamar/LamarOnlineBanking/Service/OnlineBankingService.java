package com.lamar.LamarOnlineBanking.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.lamar.LamarOnlineBanking.Dao.DataAccess;

@Service
public class OnlineBankingService {
	
	@Autowired
    private JavaMailSender mailSender;
	 
	public void sendSimpleEmail(String toEmail,
            String subject,
            String body
		) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("saiteja40484@gmail.com");
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject(subject);
		mailSender.send(message);
		System.out.println("Mail Send...");
		
		
		}
}
