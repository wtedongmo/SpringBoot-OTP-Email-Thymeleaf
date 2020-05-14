package com.twb.service;

import com.twb.utility.EmailTemplateUpdated;
import com.twb.utility.TranslateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.internet.MimeMessage;
import java.util.Map;


@Service
public class EmailServiceUpdated {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	TranslateUtils translateUtils;
	
	@Autowired
	private JavaMailSender javaMailSender;

//	@Value("${app.twb.email.default.to}")
//	private String toEmail;
//
//	@Value("${app.twb.email.default.cc}")
//	private String ccEmail;


//	public void sendEmailTemplateWithParams(String subjectCode, String messageCode, String templateName,
//											Map<String, String> replacements) throws Exception{
//		sendEmailTemplateWithParams(toEmail, ccEmail, subjectCode, messageCode, templateName, replacements) ;
//	}

	public void sendEmailTemplateWithParams(String to, String cc, String subjectCode, String messageCode, String templateName,
											Map<String, String> replacements) throws Exception{

		EmailTemplateUpdated template = new EmailTemplateUpdated(translateUtils, templateName, messageCode); // ./mailTemplate/mailCreateUser.html
		String subject = translateUtils.translate(subjectCode);
		String message = template.getTemplate(replacements);
		sendMailTemplate(to, cc, subject, message);
	}


//	public void sendSimpleEmailWithParams(String subjectCode, String messageCode,
//										  Map<String, String> replacements){
//		sendSimpleEmailWithParams(toEmail, ccEmail, subjectCode, messageCode, replacements);
//	}

	public void sendSimpleEmailWithParams(String to, String cc, String subjectCode, String messageCode,
											Map<String, String> replacements){

		EmailTemplateUpdated template = new EmailTemplateUpdated(translateUtils, null, messageCode);
		String subject = translateUtils.translate(subjectCode);
		String message = template.getTemplate(replacements);

		sendSimpleMail(to, cc, subject, message);
	}

//	public void sendSimpleEmailForUnSuccessTransaction(String userName, String orderNumber, String errorMessage){
//
//	}

	public void sendSimpleMail(String to, String cc, String subject, String message) {

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setTo(to.split(","));
		if(!StringUtils.isEmpty(cc)){
			simpleMailMessage.setTo(cc.split(","));
		}
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);

		logger.info(subject);
		logger.info(to);
		logger.info(message);

		//Uncomment to send mail
		javaMailSender.send(simpleMailMessage);
	}

	public void sendMailTemplate(String to, String cc, String subject, String message) throws Exception {

		MimeMessage mailMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mailMessage); //, true
		helper.setTo(to.split(","));
		if(!StringUtils.isEmpty(cc)) {
			helper.setCc(cc.split(","));
		}
		helper.setSubject(subject);
		helper.setText(message, true);

//		FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
//		helper.addAttachment("Invoice", file);
		logger.trace(" To: "+to+" \t cc: "+cc+" \t Subject: "+subject+" \t Message: "+message);

		//Uncomment to send mail
		javaMailSender.send(mailMessage);
	}
}
