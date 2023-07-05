package com.vikas.sociallogin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.vikas.sociallogin.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain fiSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf()
		.disable().authorizeHttpRequests()
		.antMatchers("/registration", "/login", "/css/**")
				.permitAll()
				.anyRequest().authenticated().and()
				.formLogin().loginPage("/login")
				.successHandler(authenticationSuccessHandler)
				.and().csrf().disable()
				.logout().logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.and().oauth2Login().loginPage("/login")
				.successHandler(authenticationSuccessHandler);
		return httpSecurity.build();
	}

}
