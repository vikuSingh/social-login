package com.vikas.sociallogin.controller;

import com.sun.corba.se.impl.io.TypeMismatchException;
import com.vikas.sociallogin.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.vikas.sociallogin.dto.UserLoginDto;
import com.vikas.sociallogin.service.UserService;

import javax.management.modelmbean.ModelMBean;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UserService userService;

	@GetMapping
	public String login() {
		return "login";
	}

	@PostMapping
	public void loginUser(@ModelAttribute UserLoginDto userLoginDto) {
		userService.loadUserByUsername(userLoginDto.getUsername());
	}

	@GetMapping("/getOtp")
	public String getOtp(@ModelAttribute UserLoginDto userLoginDto, Model model) {
       model.addAttribute("otp", userLoginDto.getOtp());
	   return "otpscreen";
	}

	@PostMapping("/sendOtp")
	public String saveOtpRecords(@ModelAttribute UserLoginDto userLoginDto, Authentication authentication, Model model) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		userLoginDto.setUsername(userDetails.getUsername());
	    User users = userService.saveOtpDetails(userLoginDto);
		if(users != null) {
			model.addAttribute("userDetails", users.getFullName());
			return "dashboard";
		}else{
			return "redirect:getOtp?error";
		}
	}
}
