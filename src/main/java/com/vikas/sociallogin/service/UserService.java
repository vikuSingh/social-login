package com.vikas.sociallogin.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.vikas.sociallogin.dto.UserRegisterDto;
import com.vikas.sociallogin.model.User;

public interface UserService extends UserDetailsService {

	public User registerUser(UserRegisterDto userRegisterDto);

}
