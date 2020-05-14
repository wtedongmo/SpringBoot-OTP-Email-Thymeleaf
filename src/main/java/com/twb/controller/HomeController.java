package com.twb.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.twb.model.Role;
import com.twb.model.User;
import com.twb.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.twb.repo.BookRepository;
import com.twb.service.OtpService;


@Controller
public class HomeController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
    @Value("${spring.application.name}")
    String appName;

    @Autowired
    BookRepository repo;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
	public OtpService otpService;

    @Autowired
	public OtpController otpController;

    @GetMapping("/")
    public String homePage(Model model) {
    	
    	String message = " Welcome to my Page";
    	
        model.addAttribute("appName", appName);
        model.addAttribute("message", message);
       
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        logger.info("username: " + auth.getName()); 
        
        return "infos";
//        return "signin";
    }

    @RequestMapping(value ="/checkinfos", method = RequestMethod.GET)
    public String checkOtp(HttpServletRequest request, Model model, @RequestParam("nui") String nui, @RequestParam("email") String email) { //@ResponseBody

        double val = Math.random();
        String error=null;
        if(val<0.5){
            // valeur Non OK
            model.addAttribute("infosMessage", "Bad informations");
            return "infos";
        }else{
            //valeur OK
            model.addAttribute("infosMessage", "Good informations");

            //Create user and sign in up, generate otp page
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            User user = new User(nui, passwordEncoder.encode(nui), email, Role.ROLE_ADMIN);
            user = userRepository.save(user);

            try {
                request.login(user.getUsername(), nui);
                return otpController.generateOtp();
            } catch (ServletException e) {
                error = e.getMessage();
                logger.error("Error while login ", e);
            }
        }
        model.addAttribute("infosMessage", error);
        return "infos";
    }
    
    @GetMapping("/dashboard")
    public String dashboard(Model model){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
        logger.info("username: " + auth.getName()); 

    	return "dashboard";
    }
    
    @GetMapping("/login")
    public String login() {
        return "signin";
    }
    
    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/aboutus")
    public String about() {
        return "aboutus";
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }
    
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public @ResponseBody String logout(HttpServletRequest request, HttpServletResponse response){
    	
       Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
       if (auth != null){    
    	   String username = auth.getName();
    	   
    	   //Remove the recently used OTP from server. 
    	   otpService.clearOTP(username);
           
    	   new SecurityContextLogoutHandler().logout(request, response, auth);
       }
       
	   return "redirect:/infos?logout";
    }
    
}
