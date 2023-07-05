package com.vikas.sociallogin.service;

import com.vikas.sociallogin.dto.UserLoginDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.vikas.sociallogin.dto.UserRegisterDto;
import com.vikas.sociallogin.model.User;

public interface UserService extends UserDetailsService {

	public User registerUser(UserRegisterDto userRegisterDto);
	public String generateOtp(User user);

	public User saveOtpDetails(UserLoginDto userLoginDto);

}
