package com.twb.service;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class EmailServiceTest {

    Logger logger= LoggerFactory.getLogger(this.getClass());


    @Autowired
    private EmailServiceUpdated emailService;

//    @Test
//    void contextLoads() {
//    }


    @Test
    void sendSimpleTextEmailTest(){

        String to ="mail@mail.com";
        String subjectCode="mail.message.subject";
        String messageCode="mail.message.text";
        Map<String, String> replacements = new HashMap<>();
        replacements.put("user", "Wilfried");
        replacements.put("otpnum", "231548");
        replacements.put("sendTime", LocalDateTime.now().toString());

        emailService.sendSimpleEmailWithParams(to, null, subjectCode, messageCode, replacements);
    }

    @Test
    void sendHtmlTextEmailTest(){

        String to ="mail@mail.com";
        String subjectCode="mail.message.transaction.success.subject";
        String messageCode="mail.message.transaction.success.text";
        String templatePath="./email-html-templates/sendOtp.html";
        Map<String, String> replacements = new HashMap<>();
        replacements.put("user", "Wilfried");
        replacements.put("otpnum", "231548");
        replacements.put("sendTime", LocalDateTime.now().toString());

        try {
            emailService.sendEmailTemplateWithParams(to, null, subjectCode, messageCode, templatePath, replacements);
        }catch(Exception ex){
            logger.error(ex.getMessage(), ex);
        }

    }
}

