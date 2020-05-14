package com.twb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.twb.service.MyUserDetailsService;

/**
 * @author twb
 * Dec 12, 2017
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable().authorizeRequests()
			.antMatchers("/","/aboutus", "/infos", "/checkinfos").permitAll()  //dashboard , Aboutus page will be permit to all user
			.antMatchers("/generateOtp", "/otppage").hasAuthority("PRE_AUTH") //Only admin user can login
			.antMatchers("/admin", "/dashboard").hasAuthority("ROLE_ADMIN") //Only admin user can login
			.antMatchers("/user", "/dashboard").hasAuthority("ROLE_USER") //Only normal user can login
			.anyRequest().authenticated() //Rest of all request need authentication			 
        .and()
        .formLogin()
			.loginPage("/infos")  //Loginform all can access ..
//			.defaultSuccessUrl("/dashboard")
			.defaultSuccessUrl("/generateOtp")
			.failureUrl("/login?error")
			.permitAll()
			.and()
        .logout()
			.permitAll()
			.and()
        .exceptionHandling().accessDeniedHandler(accessDeniedHandler);
			
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
		auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder);;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }
	
}
