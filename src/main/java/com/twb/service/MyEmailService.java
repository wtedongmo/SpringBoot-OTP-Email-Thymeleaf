package com.twb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author twb
 * Dec 18, 2017
 */
@Service
public class MyEmailService  {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JavaMailSender javaMailSender;
	 
	public void sendOtpMessage(String to, String subject, String message) {
		 
		 SimpleMailMessage simpleMailMessage = new SimpleMailMessage(); 
		 simpleMailMessage.setTo(to); 
		 simpleMailMessage.setSubject(subject); 
		 simpleMailMessage.setText(message);
		 
		 logger.info(subject);
		 logger.info(to);
		 logger.info(message);
		 
		 //Uncomment to send mail
		 javaMailSender.send(simpleMailMessage);
	}

//	public void sendMailTemplate(String to, String cc, String subject, String message) {
//
//		MimeMessage mailMessage = javaMailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(mailMessage);
//		helper.setTo(to);
//		if(cc != null) {
//			helper.setCc(cc.split(","));
//		}
//		helper.setSubject(subject);
//		helper.setText(message, true);
//
//		logger.trace(" To: "+to+" \t cc: "+cc+" \t Subject: "+subject+" \t Message: "+message);
//
//		//Uncomment to send mail
//		javaMailSender.send(mailMessage);
//	}

}
