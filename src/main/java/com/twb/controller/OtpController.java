package com.twb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.twb.model.User;
import com.twb.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.twb.service.MyEmailService;
import com.twb.service.OtpService;
import com.twb.utility.EmailTemplate;

/**
 * @author twb
 *
 */
@Controller
public class OtpController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public OtpService otpService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public MyEmailService myEmailService;

	@GetMapping("/generateOtp")
	public String generateOtp(){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		String username = auth.getName();
		
		int otp = otpService.generateOTP(username);
		 
		logger.info("OTP : "+otp);
		
		//Generate The Template to send OTP 
		EmailTemplate template = new EmailTemplate("sendOtp.html");
		
		Map<String,String> replacements = new HashMap<String,String>();
		replacements.put("user", username);
		replacements.put("otpnum", String.valueOf(otp));
		 
		String message = template.getTemplate(replacements);
        User user = userRepository.findByUsername(username);
		myEmailService.sendOtpMessage(user.getEmail(), "OTP -SpringBoot", message+"  "+otp);
		
		return "otppage";
	}
	
	@RequestMapping(value ="/validateOtp", method = RequestMethod.GET)
	public String validateOtp(Model model, @RequestParam("otpnum") int otpnum){ //@ResponseBody
		
		final String SUCCESS = "Entered Otp is valid";
		
		final String FAIL = "Entered Otp is NOT valid. Please Retry with New One!";
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		String username = auth.getName();
		
		logger.info(" Otp Number : "+otpnum);
		
		//Validate the Otp 
		if(otpnum >= 0){
			int serverOtp = otpService.getOtp(username);
			
			if(serverOtp > 0){
				if(otpnum == serverOtp){
					otpService.clearOTP(username);
					model.addAttribute("otpMessage", SUCCESS);

					User user = userRepository.findByUsername(username);
					List<GrantedAuthority> updatedAuthorities = new ArrayList<>(); //auth.getAuthorities()
					updatedAuthorities.add(new SimpleGrantedAuthority(user.getRole().name())); //add your role here [e.g., new SimpleGrantedAuthority("ROLE_NEW_ROLE")]

					Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);

					SecurityContextHolder.getContext().setAuthentication(newAuth);

					return "dashboard";
				}
			}
		}
		model.addAttribute("otpMessage", FAIL);
		return "otppage";
	}
}
